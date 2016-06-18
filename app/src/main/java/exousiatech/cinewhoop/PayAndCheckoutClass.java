package exousiatech.cinewhoop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ProgressDialogClass;
import CinewhoopUtil.RestroCalls;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RecyclerAdapter.AdapterOffersPayandCheckout;

/**
 * Created by jagteshwar on 05-02-2016.
 */
public class PayAndCheckoutClass extends AppCompatActivity implements View.OnClickListener, couponinterface {
    Toolbar toolbar;
    TextView noOrdersinPayment, showtermsAndcond, toolbarTitle, headerYourTotal, namemovieselected, priceofMovieSelecte, crossIconmovie, headerYourOffer, headerTotal, totalAmount;
    CheckBox termsandcondCheckbox;
    Button payAndCheckout, applycouponBtn;
    ArrayList<TextView> tv;
    Utilclass typechange;
    LinearLayout offerLayout, ticketLaout, termsAndCondLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Dialog pDialog;
    DatabaseHelperCinewhoop accesdatabase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    Typeface typeface2;
    RecyclerView list_of_alltheOffers;
    RecyclerView.LayoutManager layoutManager;
    AdapterOffersPayandCheckout adapter;
    EditText couponcode;
    double moviePrice = 0;
    double offerPrice = 0;
    double totalPrice = 0;
    double totalPricetosend = 0;
    ProgressDialogClass dialogClass;

    RestroCalls restroCalls;
    private static final String CONFIG_CLIENT_ID = "ARJ2hVcdEXPiRsAQL4MHmMfv5EQeVh2kYGM3krqm3U4UmRVqtv9HuH3GJCqQh4qfvzmzfIkszj9eYN3v";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_checkout);
        restroCalls = new RestroCalls(PayAndCheckoutClass.this);
        accesdatabase = new DatabaseHelperCinewhoop(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialogClass = new ProgressDialogClass(PayAndCheckoutClass.this);
        typeface2 = Typeface.createFromAsset(getAssets(), "fonts/cross.ttf");
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setSelected(true);
        toolbarTitle.setText("Make Payment");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        crossIconmovie = (TextView) findViewById(R.id.crossIconmovie);
        crossIconmovie.setTypeface(typeface2);
        crossIconmovie.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_alltheOffers = (RecyclerView) findViewById(R.id.list_of_alltheOffers);
        list_of_alltheOffers.setLayoutManager(layoutManager);

        headerYourTotal = (TextView) findViewById(R.id.headerYourTotal);
        tv.add(headerYourTotal);

        couponcode = (EditText) findViewById(R.id.couponcode);
        tv.add(couponcode);
        headerYourOffer = (TextView) findViewById(R.id.headerYourOffer);
        tv.add(headerYourOffer);
        headerTotal = (TextView) findViewById(R.id.headerTotal);
        tv.add(headerTotal);
        totalAmount = (TextView) findViewById(R.id.totalAmount);
        tv.add(totalAmount);
        showtermsAndcond = (TextView) findViewById(R.id.showtermsAndcond);
        tv.add(showtermsAndcond);
        noOrdersinPayment = (TextView) findViewById(R.id.noOrdersinPayment);
        tv.add(noOrdersinPayment);
        namemovieselected = (TextView) findViewById(R.id.namemovieselected);
        tv.add(namemovieselected);
        priceofMovieSelecte = (TextView) findViewById(R.id.priceofMovieSelecte);
        tv.add(priceofMovieSelecte);
        typechange.applycustomfont(tv);
        offerLayout = (LinearLayout) findViewById(R.id.offerAmountlayout);
        ticketLaout = (LinearLayout) findViewById(R.id.ticketLaout);
        termsandcondCheckbox = (CheckBox) findViewById(R.id.termsandcondCheckbox);
        termsAndCondLayout = (LinearLayout) findViewById(R.id.termsAndCondLayout);
        termsAndCondLayout.setOnClickListener(this);

        applycouponBtn = (Button) findViewById(R.id.applycouponBtn);
        payAndCheckout = (Button) findViewById(R.id.payAndCheckout);
        payAndCheckout.setOnClickListener(this);
        applycouponBtn.setOnClickListener(this);
        SetUiForPayandcheckout();
        pDialog = new Dialog(PayAndCheckoutClass.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.alert_view_web_view);
        pDialog.findViewById(R.id.okBtn).setOnClickListener(this);
        pDialog.setCancelable(true);

    }

    private void SetUiForPayandcheckout() {
        InflateMovielayout();
        Inflateofferlayout();
       checkForNoOrders();
    }

    private void checkForNoOrders() {
        if (!sharedPreferences.getBoolean("MovieExist", false)&&!accesdatabase.offerExistornot()){
            noOrdersinPayment.setVisibility(View.VISIBLE);
        }else {
            noOrdersinPayment.setVisibility(View.INVISIBLE);
        }
    }

    public void InflateMovielayout() {
        if (sharedPreferences.getBoolean("MovieExist", false)) {
            priceofMovieSelecte.setText(String.format("$ %.2f", Double.parseDouble(sharedPreferences.getString("totalPriceMovie", " "))));
            namemovieselected.setText(sharedPreferences.getString("MovieName", " "));
            moviePrice = Double.parseDouble(sharedPreferences.getString("totalPriceMovie", ""));
            settotalAmount();
        } else {
            ticketLaout.setVisibility(View.GONE);
            moviePrice = 0;
            settotalAmount();
        }
        checkForNoOrders();
    }

    public void Inflateofferlayout() {
        if (accesdatabase.offerExistornot()) {
            adapter = new AdapterOffersPayandCheckout(PayAndCheckoutClass.this);
            list_of_alltheOffers.setAdapter(adapter);
            offerPrice = getTotalPriceOfOfferFromDataBase();
            settotalAmount();
        } else {
            offerLayout.setVisibility(View.GONE);
            offerPrice = 0;
            settotalAmount();
        }
        checkForNoOrders();
    }

    private double getTotalPriceOfOfferFromDataBase() {
        datafromDatabase = accesdatabase.selectDataOffer();
        double offerpricetoset = 0;
        for (int i = 0; i < datafromDatabase.size(); i++) {
            String arr[] = datafromDatabase.get(i).split("~");
            offerpricetoset = Double.parseDouble(arr[3]) + offerpricetoset;
        }
        return offerpricetoset;
    }

    public void settotalAmount() {

        double totalPrice = moviePrice + offerPrice;
        this.totalPrice = totalPrice;
        totalPricetosend = totalPrice;
        totalAmount.setText(String.format("$ %.2f", totalPrice));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();

                break;

            case R.id.homeIcon:
                Intent intent2 = new Intent(this, HomeActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sharedPreferences.getBoolean("offerExist", false)) {

            editor.putBoolean("offerExist", false);

            editor.commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payAndCheckout:
                if (totalPrice == 0.0) {
                    Snackbar snackbar = Snackbar.make(payAndCheckout, "You have nothing to pay", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (termsandcondCheckbox.isChecked()) {
                    Intent it = new Intent(PayAndCheckoutClass.this, PaymentEway.class);
                    editor.putString("offerPrice", offerPrice + "");
                    editor.putString("totalPrice", totalPrice + "");
                    editor.commit();
                    it.putExtra("total", totalPricetosend);
                    startActivity(it);

                } else {
                    Snackbar snackbar = Snackbar.make(payAndCheckout, "Please Accept The Terms And Conditions", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                break;
            case R.id.termsAndCondLayout:
                pDialog.show();
                break;
            case R.id.okBtn:
                pDialog.dismiss();
                termsandcondCheckbox.setChecked(true);
                break;
            case R.id.crossIconmovie:
                editor.putBoolean("MovieExist", false);
                editor.commit();
                InflateMovielayout();
                break;
            case R.id.applycouponBtn:

                if (TextUtils.isEmpty(couponcode.getText().toString())) {
                    couponcode.setError("Please Enter some code");
                } else {
                    dialogClass.showdialog();
                    restroCalls.couponcallretro(couponcode.getText().toString(), this);
                }

                break;

        }
    }

    @Override
    public void valueforcoupontoset(String status) {

        dialogClass.hideDialog();
        if (status.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(payAndCheckout, "Coupon Code Not Valid", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            View view = this.getCurrentFocus();
            InputMethodManager imm = null;

            if (view != null) {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            Double.parseDouble(status);
            double pe = ((totalPrice) * Double.parseDouble(status)) / 100;
            double value = totalPrice - pe;
            totalAmount.setText(String.format("$ %.2f", totalPrice) + "\n" + "\n" + " - " + String.format(" $ %.2f", pe) + "\n" + "_________" + "\n" + "\n" + "" + String.format("$ %.2f", value));

            totalPricetosend = value;
            couponcode.setText(null);
            Toast.makeText(PayAndCheckoutClass.this, "Promo Code Applied ", Toast.LENGTH_SHORT).show();
        }
    }
}

interface couponinterface {
    void valueforcoupontoset(String status);
}