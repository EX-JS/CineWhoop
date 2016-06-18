package CinewhoopUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.eway.payment.sdk.android.RapidAPI;
import com.eway.payment.sdk.android.RapidConfigurationException;
import com.eway.payment.sdk.android.beans.NVPair;
import com.eway.payment.sdk.android.entities.EncryptItemsResponse;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CinemaDetail;
import RetrofitPackage.Coupon;
import RetrofitPackage.OrderDetails;
import RetrofitPackage.RetrofitUtil;
import RetrofitPackage.TransactionStatus;
import exousiatech.cinewhoop.HomeActivity;
import exousiatech.cinewhoop.PayAndCheckoutClass;
import exousiatech.cinewhoop.PaymentEway;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 24-02-2016.
 */
public class RestroCalls  {
    TransactionStatus result;
    boolean transactionboolean = false;
    String creditpoints;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PayAndCheckoutClass payAndCheckoutClass;
    PaymentEway paymentEway;
    String statusTicket= "";
    CinemaDetail cinemaValObj;
    double priceTicketAdult = 0;
    double priceTicketChild = 0;
    double total = 0;
    DatabaseHelperCinewhoop helperCinewhoop;
    public RestroCalls(PaymentEway paymentEway) {
        this.paymentEway = paymentEway;
        helperCinewhoop = new DatabaseHelperCinewhoop(paymentEway);
        sharedPreferences = paymentEway.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public RestroCalls(PayAndCheckoutClass payAndCheckoutClass) {
        this.payAndCheckoutClass = payAndCheckoutClass;
        helperCinewhoop = new DatabaseHelperCinewhoop(payAndCheckoutClass);
        sharedPreferences = payAndCheckoutClass.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void callretrofitapi(final String acesscode , Context context)
    {

        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<TransactionStatus> paymentstatus =  cineApiService.transactionDetails(ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,""),sharedPreferences.getString(ConfigClass.SessionTime,""),acesscode);

        paymentstatus.enqueue(new Callback<TransactionStatus>() {
            @Override
            public void onResponse( final Response<TransactionStatus> response) {
                result = response.body();
                    for (int i  = 0;i<result.getTransactions().size();i++){
                        if (result.getTransactions().get(i).getTransactionStatus()){
                            editor.putString("transactionId" ,result.getTransactions().get(i).getTransactionID()+"");
                            editor.putBoolean("transactionidExist", true);
                            editor.apply();
                            transactionboolean = result.getTransactions().get(i).getTransactionStatus();
                            paymentEway.valueforpaymentstatus(transactionboolean);


                        }
                        else {
                            transactionboolean = false;
                            paymentEway.valueforpaymentstatus(transactionboolean);

                        }

                }

            }

            @Override
            public void onFailure(Throwable t) {
                result = null;
                transactionboolean = false;
                paymentEway.valueforpaymentstatus(transactionboolean);

            }
        });

    }
    public void callretrofitforOrderdetails(final String emailid ){
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<OrderDetails> orderstatus =  cineApiService.orderinfo(ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,""),sharedPreferences.getString(ConfigClass.SessionTime,""), emailid);

        orderstatus.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse( final Response<OrderDetails> response) {
                if (response.body().getStatus().equals("Access authorized")) {
                    if (response.body().getTotalCredit().getCredit().equalsIgnoreCase("")) {
                        creditpoints = "";
                        paymentEway.valueOfCreditPoints(creditpoints);
                    } else {
                        creditpoints = response.body().getTotalCredit().getCredit();
                        paymentEway.valueOfCreditPoints(creditpoints);

                    }
                }else {
                    Toast.makeText(paymentEway, "Please login again , You have been logged in with some other device", Toast.LENGTH_LONG).show();
                    editor.clear();
                    editor.commit();
                    if (FacebookSdk.isInitialized()){
                        LoginManager.getInstance().logOut();
                    }
                    helperCinewhoop.deleteTableOffer();
                    helperCinewhoop.deleteTable();
                    Intent intent=new Intent(paymentEway,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    paymentEway.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                creditpoints = "";
                paymentEway.valueOfCreditPoints(creditpoints);

            }
        });

    }
    public String errorHandler(ArrayList<String> response) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String errorMsg : response) {
            result.append("Message ").append(i).append(" = ").append(errorMsg).append("\n");
            i++;
        }
        return result.toString();
    }
    public String noNullObjects(String number) {
        if (number.isEmpty() || number == null) {
            number = "";
        }
        return number;

    }
    public EncryptItemsResponse encryptCard(String cardNumber, String cvnNumber) throws IOException, RapidConfigurationException {
        ArrayList<NVPair> values = new ArrayList<>();
        values.add(new NVPair("Card", cardNumber));
        values.add(new NVPair("CVN", cvnNumber));
        return RapidAPI.encryptValues(values);
    }

    public void couponcallretro(final String acesscode , Context context){
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<List<Coupon>> couponStatus =  cineApiService.couponcall(ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,""),sharedPreferences.getString(ConfigClass.SessionTime,""),acesscode);
        couponStatus.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Response<List<Coupon>> response) {
                if (response.body().get(0).getStatus().equalsIgnoreCase("Access authorized")){
                    Log.e("couponOff", " "+response.body().get(0).getCouponOff());
                    payAndCheckoutClass.valueforcoupontoset(response.body().get(0).getCouponOff());
                }else {
                    Toast.makeText(payAndCheckoutClass, "Please login again , You have been logged in with some other device", Toast.LENGTH_LONG).show();
                    editor.clear();
                    editor.commit();
                    if (FacebookSdk.isInitialized()){
                        LoginManager.getInstance().logOut();
                    }
                    helperCinewhoop.deleteTableOffer();
                    helperCinewhoop.deleteTable();
                    Intent intent=new Intent(payAndCheckoutClass,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    payAndCheckoutClass.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("failure", t.getMessage()+" ");
                payAndCheckoutClass.valueforcoupontoset("");

            }
        });

    }

    public void callretrofitdataforrates(final int cpoints, final int totalpointsfromSelection, final double total, final int countnoofChildscredit, final int countnoofAdultscredit) {
        sharedPreferences = paymentEway.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.total = total;

        priceTicketAdult = Double.parseDouble(sharedPreferences.getString("ratesforAdultTicket","0"));
        priceTicketChild = Double.parseDouble(sharedPreferences.getString("ratesforChildTicket","0"));
        double totalamounttobesubtracted = (countnoofChildscredit*priceTicketChild)+(countnoofAdultscredit*priceTicketAdult);

        double totalaftersubtraction =  this.total-totalamounttobesubtracted;
        if (totalaftersubtraction<0){
            totalaftersubtraction=0;
        }
        paymentEway.amounttobededucted(totalaftersubtraction);
            }
    }

