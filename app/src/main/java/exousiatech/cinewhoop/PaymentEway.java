package exousiatech.cinewhoop;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.eway.payment.sdk.android.RapidAPI;
import com.eway.payment.sdk.android.RapidConfigurationException;
import com.eway.payment.sdk.android.beans.CardDetails;
import com.eway.payment.sdk.android.beans.Customer;
import com.eway.payment.sdk.android.beans.Payment;
import com.eway.payment.sdk.android.beans.Transaction;
import com.eway.payment.sdk.android.beans.TransactionType;
import com.eway.payment.sdk.android.entities.EncryptItemsResponse;
import com.eway.payment.sdk.android.entities.SubmitPayResponse;
import com.suru.myp.MonthYearPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import CinewhoopUtil.AlertClass;
import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.PdfAsync;
import CinewhoopUtil.RestroCalls;
import CinewhoopUtil.Utilclass;
import CinewhoopUtil.PdfAsync;
import RetrofitPackage.TransactionStatus;


/**
 * Created by jagteshwar on 12-02-2016.
 */
public class PaymentEway extends AppCompatActivity implements View.OnClickListener, Paymentinterface, AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    TextView toolbarTitle, headerCard, monthyear, totalAmounttopay, headerTotalamount , detailsCredit;
    Button btnPayNow , applyCreditBtn;
    ArrayList<TextView> tv;
    Utilclass typechange;
    ConnectionDetectorUtil connectionDetectorUtil;
    EditText cardno1, cardno2, cardno3, cardno4, cvnNumber, nameOnCard;
    private MonthYearPicker myp;
    String monthtoset;
    Dialog pDialog;
    private String totalamounttopaystr;
    private String cardName;
    private String cardNumber;
    private String cvnNumberstr;
    private String expMonth;
    private String expYear;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertClass alert;
    RestroCalls restroCalls;
    TransactionStatus result;
    double paymentamn;
    boolean transactionStatusboolean = false;
    PdfAsync pdfAsync;
    File file;
    String creditPoints ="";
    String category_arrayAdult[] = {};
    String category_arrayChild[] = {};
    int     countnoofAdultscredit =0 , countnoofChildscredit= 0;
    Spinner noOfAdultTicketscredit , noOfchildTicketscredit;
    int debitPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_eway);
//        RapidAPI.PublicAPIKey = "epk-BC84C5DC-D8B8-4F53-BE80-C3D76B45F53B";
//        RapidAPI.RapidEndpoint = "https://api.ewaypayments.com/";
        pdfAsync = new PdfAsync(PaymentEway.this);
        RapidAPI.PublicAPIKey = "epk-8A78A914-DAB2-4026-BB2A-179A55B9B08E";
        RapidAPI.RapidEndpoint = "https://api.ewaypayments.com/";
        restroCalls = new RestroCalls(this);
        result = new TransactionStatus();
        alert = new AlertClass();
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("PAYMENT");
        headerCard = (TextView) findViewById(R.id.headerCard);
        monthyear = (TextView) findViewById(R.id.monthyear);
        totalAmounttopay = (TextView) findViewById(R.id.totalAmounttopay);
        headerTotalamount = (TextView) findViewById(R.id.headerTotalamount);
        detailsCredit = (TextView) findViewById(R.id.detailsCredit);
        detailsCredit.setOnClickListener(this);
        typechange = new Utilclass(getApplicationContext());
        cardno1 = (EditText) findViewById(R.id.cardno1);
        cardno2 = (EditText) findViewById(R.id.cardno2);
        cardno3 = (EditText) findViewById(R.id.cardno3);
        cardno4 = (EditText) findViewById(R.id.cardno4);
        cvnNumber = (EditText) findViewById(R.id.cvnNumber);
        nameOnCard = (EditText) findViewById(R.id.nameOnCard);
        btnPayNow = (Button) findViewById(R.id.btnPayNow);
        btnPayNow.setOnClickListener(this);
        applyCreditBtn = (Button) findViewById(R.id.applyCreditBtn);
        applyCreditBtn.setOnClickListener(this);
        tv.add(toolbarTitle);
        tv.add(cardno1);
        tv.add(cardno2);
        tv.add(cardno3);
        tv.add(cardno4);
        tv.add(cvnNumber);
        tv.add(nameOnCard);
        tv.add(monthyear);
        tv.add(headerCard);
        tv.add(totalAmounttopay);
        tv.add(headerTotalamount);
        tv.add(detailsCredit);
        typechange.applycustomfont(tv);
        totalAmounttopay.setText(String.format("$ %.2f ", getIntent().getDoubleExtra("total", 0)));
        totalamounttopaystr = totalAmounttopay.getText().toString();
        pDialog = new Dialog(PaymentEway.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
        category_arrayAdult = getResources().getStringArray(R.array.noOfSeatsforCreditsAdult);
        category_arrayChild = getResources().getStringArray(R.array.noOfSeatsforCreditsChild);
        noOfAdultTicketscredit = (Spinner)findViewById(R.id.noOfAdultTicketscredit);
        noOfchildTicketscredit = (Spinner)findViewById(R.id.noOfchildTicketscredit);
        noOfAdultTicketscredit.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_text, category_arrayAdult));
        noOfAdultTicketscredit.setOnItemSelectedListener(this);

        noOfchildTicketscredit.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_text, category_arrayChild));
        noOfchildTicketscredit.setOnItemSelectedListener(this);
        monthyear.setOnClickListener(this);
        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener() {
            //
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int monthNumber = myp.getSelectedMonth() + 1;
                if (monthNumber <= 9) {
                    monthtoset = "0" + (myp.getSelectedMonth() + 1);
                    monthyear.setText(monthtoset + "/" + myp.getSelectedYear());
                } else {
                    monthyear.setText((myp.getSelectedMonth() + 1) + "/" + myp.getSelectedYear());
                }
            }
        }, null);

        //        Textwather
        cardno1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardno1.getText().toString().length() == 4) {
                    cardno2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cardno2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardno2.getText().toString().length() == 4) {
                    cardno3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cardno3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardno3.getText().toString().length() == 4) {
                    cardno4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cardno4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardno4.getText().toString().length() == 4) {
                    cvnNumber.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cvnNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cvnNumber.getText().toString().length() == 4) {
                    nameOnCard.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sharedPreferences.getBoolean("offerExist", false) || sharedPreferences.getBoolean("MovieExist", false)) {

            editor.putBoolean("offerExist", false);

            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil = new ConnectionDetectorUtil();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionDetectorUtil, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionDetectorUtil);
        connectionDetectorUtil.dismissDialog();
    }

    public void show(View view) {
        myp.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.monthyear:
                show(v);
                break;
            case R.id.detailsCredit:
                alert.showAlertDialog(PaymentEway.this , "1. On 200 Points user gets 1 Free Adult Ticket. \n\n2. On 150 Points user gets 1 Free Child Ticket. \n\n3. You cannot use credits for Offers. ", false);
                break;
            case R.id.applyCreditBtn:
                if (!sharedPreferences.getBoolean("MovieExist" , false)){
                    Snackbar snackbar = Snackbar.make(btnPayNow , "Please select a movie to apply Credit points" , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else if (countnoofAdultscredit==0 && countnoofChildscredit==0 ){
                    Snackbar snackbar = Snackbar.make(btnPayNow , "Please Select the Tickets" , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (countnoofAdultscredit>sharedPreferences.getInt("NoofAdultTicket", 0)){
                    Snackbar snackbar = Snackbar.make(btnPayNow , "Please Select a valid number of Adult Tickets" , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else if (countnoofChildscredit>sharedPreferences.getInt("NoofChildTicket", 0)){
                    Snackbar snackbar = Snackbar.make(btnPayNow , "Please Select a valid number of Child Tickets" , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else {
                    pDialog.show();
                    restroCalls.callretrofitforOrderdetails(sharedPreferences.getString("email" , ""));
                }

                break;
            case R.id.btnPayNow:
                double totalamn = 0;
                String amount[] = totalamounttopaystr.split(" ");
                if (amount.length > 0) {
                    String totalam = amount[1];
                    totalamn = Double.parseDouble(totalam);
                    paymentamn = totalamn * 100;
                }
                if (totalamn>0.0){
                    editor.putBoolean("fullpaymentcredit",false);
                    editor.commit();
                }
//                if (sharedPreferences.getBoolean("fullpaymentcredit",false)){
//                    pdfAsync.collectDataAndCallRetrofit();
//                }else
                if (TextUtils.isEmpty(cardno1.getText().toString()) || TextUtils.isEmpty(cardno2.getText().toString())
                        || TextUtils.isEmpty(cardno3.getText().toString()) || TextUtils.isEmpty(cardno4.getText().toString())
                        || TextUtils.isEmpty(cvnNumber.getText().toString()) || TextUtils.isEmpty(monthyear.getText().toString()) || TextUtils.isEmpty(nameOnCard.getText().toString())) {
                    for (int i = 1; i < tv.size(); i++) {
                        if (TextUtils.isEmpty(tv.get(i).getText()
                        )) {
                            tv.get(i).setError("This Field is Required");
                            tv.get(i).requestFocus();
                        }
                    }

                } else {
                    pDialog.show();
                    new AsyncPaymentTask().execute();

                }
                break;
        }
    }

    @Override
    public void valueforpaymentstatus(boolean status) {
        if (pDialog != null) {
            pDialog.dismiss();
        }
        if (!status) {
            Toast.makeText(PaymentEway.this, "Payment Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PaymentEway.this, "Payment Done, Please wait while we are processing ", Toast.LENGTH_SHORT).show();
            pdfAsync.collectDataAndCallRetrofit();
        }
    }

    @Override
    public void valueOfCreditPoints(String points) {

        if (points.equalsIgnoreCase("")){
            if (pDialog != null){
                pDialog.dismiss();
            }
            Snackbar snackbar = Snackbar.make(btnPayNow  ,"You have 0 Credit points",Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            int cpoints = Integer.parseInt(points);
            if ((cpoints<150)){
                if (pDialog != null){
                    pDialog.dismiss();
                }
                Snackbar snackbar = Snackbar.make(btnPayNow  ,"You do not qualify for this, Please Click Details for more Information",Snackbar.LENGTH_LONG);
                snackbar.show();
            }else {
                int child = countnoofChildscredit*150;
                int adult = countnoofAdultscredit*200;
                int totalpointsfromSelection = adult+child;
                if (cpoints>totalpointsfromSelection){
                    debitPoints = totalpointsfromSelection;
                    editor.putBoolean("debitExists" , true);
                    editor.putInt("totalpointsfromSelection",totalpointsfromSelection);
                    editor.commit();
                    restroCalls.callretrofitdataforrates(cpoints ,totalpointsfromSelection,getIntent().getDoubleExtra("total", 0),countnoofChildscredit,countnoofAdultscredit);

                }else {
                    if (pDialog != null){
                        pDialog.dismiss();
                    }
                    Snackbar snackbar = Snackbar.make(btnPayNow  ,"You do not qualify for this, Please Click Details for more Information",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }

    }

    @Override
    public void amounttobededucted(Double amount) {
        if (pDialog != null){
            pDialog.dismiss();
        }
        if (amount == 0.0){
            if (sharedPreferences.getBoolean("debitExists",false)){
                editor.putBoolean("fullpaymentcredit", true);
                editor.commit();
                totalAmounttopay.setText(String.format("$ %.2f" , amount));
            }else {
                Snackbar snackbar = Snackbar.make(btnPayNow , "Some Error occurred , Please try Again",Snackbar.LENGTH_SHORT );
                snackbar.show();
            }

        }else {
            totalAmounttopay.setText(String.format("$ %.2f" , amount));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.noOfAdultTicketscredit:
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Adult")){
                    countnoofAdultscredit = 0;
                }else {
                    countnoofAdultscredit = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }
                break;
            case R.id.noOfchildTicketscredit:
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("child")){
                    countnoofChildscredit = 0;
                }else {
                    countnoofChildscredit = Integer.parseInt( parent.getItemAtPosition(position).toString());
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class AsyncPaymentTask extends AsyncTask<String, Void, String> {
        String errorMessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fetchDataFromForm();
            totalamounttopaystr = totalAmounttopay.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            Transaction transaction = new Transaction();
            Payment payment = new Payment();
            CardDetails cardDetails = new CardDetails();
            Customer customer = new Customer();
            String amount[] = totalamounttopaystr.split(" ");
            if (amount.length > 0) {
                String totalam = amount[1];
                double totalamn = Double.parseDouble(totalam);
                paymentamn = totalamn * 100;
            }
            payment.setTotalAmount((int) paymentamn);
            cardDetails.setName(cardName);
//
            try {
                EncryptItemsResponse nCryptedData = restroCalls.encryptCard(cardNumber, cvnNumberstr);
                if (nCryptedData.getErrors() != null)
                    return restroCalls.errorHandler(RapidAPI.userMessage(Locale.getDefault().getLanguage(), nCryptedData.getErrors()).getErrorMessages());
                cardDetails.setNumber(nCryptedData.getItems().get(0).getValue());
                cardDetails.setCVN(nCryptedData.getItems().get(1).getValue());
                cardDetails.setExpiryMonth(expMonth);
                cardDetails.setExpiryYear(expYear);
                customer.setCardDetails(cardDetails);

                transaction.setTransactionType(TransactionType.Purchase);
                transaction.setPayment(payment);
                transaction.setCustomer(customer);
                SubmitPayResponse response = null;
                response = RapidAPI.submitPayment(transaction);


                if (response.getErrors() == null) {
                    return (response.getAccessCode());
                } else {
                    errorMessage = "error" + restroCalls.errorHandler(RapidAPI.userMessage(Locale.getDefault().getLanguage(), response.getErrors()).getErrorMessages());
                    Log.e("error in eway" ,errorMessage+" ");
                }

            } catch (RapidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return errorMessage;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("error")) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                Toast.makeText(PaymentEway.this, "Some Error Occurred. Please try again ", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(PaymentEway.this, "Please wait while we are confirming payment", Toast.LENGTH_SHORT).show();

                restroCalls.callretrofitapi(s, PaymentEway.this);
            }
        }
    }

    private void fetchDataFromForm() {
        cardName = restroCalls.noNullObjects(nameOnCard.getText().toString());
        cardNumber = restroCalls.noNullObjects(cardno1.getText().toString()) + restroCalls.noNullObjects(cardno2.getText().toString()) + restroCalls.noNullObjects(cardno3.getText().toString()) + restroCalls.noNullObjects(cardno4.getText().toString());
        cvnNumberstr = restroCalls.noNullObjects(cvnNumber.getText().toString());
        String arr[] = monthyear.getText().toString().split("/");
        if (!(arr.length < 0)) {
            expMonth = arr[0];
            expYear = arr[1];
        } else {
            monthyear.setError("Please select the expiry month & year");
        }
    }
}

interface Paymentinterface {
    void valueforpaymentstatus(boolean status);
    void valueOfCreditPoints(String points);
    void amounttobededucted(Double amount);
}
