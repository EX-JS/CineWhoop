package CinewhoopUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CinemaDetail;
import RetrofitPackage.RetrofitUtil;
import RetrofitPackage.TicketDetails;
import exousiatech.cinewhoop.HomeActivity;
import exousiatech.cinewhoop.PaymentEway;
import exousiatech.cinewhoop.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 29-02-2016.
 */
public class PdfAsync {
    PaymentEway paymentEway;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelperCinewhoop accesdatabase;
    ArrayList<String> datafromdatbase = new ArrayList<>();
    AlertClass alert ;
    Dialog pDialog;
    public PdfAsync(PaymentEway paymentEway) {
        this.paymentEway = paymentEway;
        sharedPreferences = paymentEway.getSharedPreferences(ConfigClass.Shared_PREF , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accesdatabase = new DatabaseHelperCinewhoop(paymentEway);
        alert = new AlertClass();
        pDialog = new Dialog(paymentEway);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view_fro_checkkout);
        pDialog.setCancelable(false);
    }


    public void collectDataAndCallRetrofit() {
        pDialog.show();
        JSONArray offerdata = new JSONArray();
        if (accesdatabase.offerExistornot()){
            datafromdatbase = accesdatabase.selectDataOffer();
            for (int i =0;i<datafromdatbase.size();i++){
                String arr[] = datafromdatbase.get(i).split("~");
                offerdata.put(arr[1]);
            }
        }else {
            offerdata.put("");
        }
        JSONObject data = new JSONObject();
        try {
            data.put("email_id" ,  sharedPreferences.getString("email",""));
            data.put("movie_id" ,  sharedPreferences.getString("MovieId",""));
            data.put("movie_price" ,sharedPreferences.getString("totalPriceMovie","") );
            data.put("cinema_id" , sharedPreferences.getString("cinemaIDtoSend",""));
            data.put("offer_id" ,offerdata );
            data.put("offer_price" , sharedPreferences.getString("offerPrice", "") );
            data.put("total_price" , sharedPreferences.getString("totalPrice", ""));
            data.put("transaction_id" ,sharedPreferences.getString("transactionId", ""));
            data.put("adult_ticket" ,  sharedPreferences.getInt("NoofAdultTicket", 0));
            data.put("child_ticket" ,sharedPreferences.getInt("NoofChildTicket", 0));
            data.put("debit", sharedPreferences.getInt("totalpointsfromSelection", 0));
            if (sharedPreferences.getBoolean("fullpaymentcredit", false)){
                data.put("type" ,1);
            }else {
                data.put("type" ,0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<TicketDetails> checkout = cineApiService.checkout(ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,""),sharedPreferences.getString(ConfigClass.SessionTime,""),data.toString());
        checkout.enqueue(new Callback<TicketDetails>() {
            @Override
            public void onResponse(Response<TicketDetails> response) {
                if (response.body().getStatus().equals("Access authorized")) {
                    if (response.body().getResponse().equalsIgnoreCase("Successfull")) {
                        accesdatabase.deleteTable();
                        accesdatabase.deleteTableOffer();
                        editor.putBoolean("offerExist", false);
                        editor.putBoolean("MovieExist", false);
                        editor.putBoolean("fullpaymentcredit", false);
                        editor.putInt("totalpointsfromSelection", 0);
                        editor.commit();
                        alert.showAlertDialogforhome(paymentEway, "Your order has been placed, You will receive an email of your order Shortly. Thanks", false);
                    } else {
                        alert.showAlertDialog(paymentEway, "Something went Wrong , you can check your orders in My Account", false);
                    }
                    if (pDialog != null) {
                        pDialog.dismiss();
                    }
                }else {
                    Toast.makeText(paymentEway, "Please login again , You have been logged in with some other device", Toast.LENGTH_LONG).show();
                    editor.clear();
                    editor.commit();
                    if (FacebookSdk.isInitialized()){
                        LoginManager.getInstance().logOut();
                    }
                    accesdatabase.deleteTableOffer();
                    accesdatabase.deleteTable();
                    Intent intent=new Intent(paymentEway,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    paymentEway.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (pDialog!=null){
                    pDialog.dismiss();
                }
                accesdatabase.deleteTable();
                accesdatabase.deleteTableOffer();
                editor.putBoolean("offerExist", false);
                editor.putBoolean("MovieExist", false);
                editor.putBoolean("fullpaymentcredit", false);
                editor.putInt("totalpointsfromSelection", 0);
                editor.commit();
                alert.showAlertDialogforhome(paymentEway, "We have successfully received your payment. We are getting in touch with Cinemas to process your tickets. This may take several seconds, please be patient.", false);
            }
        });

    }
}
