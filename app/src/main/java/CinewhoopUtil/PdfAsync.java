package CinewhoopUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import exousiatech.cinewhoop.PaymentEway;
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
    public PdfAsync(PaymentEway paymentEway) {
        this.paymentEway = paymentEway;
        sharedPreferences = paymentEway.getSharedPreferences(ConfigClass.Shared_PREF , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accesdatabase = new DatabaseHelperCinewhoop(paymentEway);
        alert = new AlertClass();
    }


    public void collectDataAndCallRetrofit() {
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
            data.put("offer_price" , sharedPreferences.getString("offerPrice","") );
            data.put("total_price" , sharedPreferences.getString("totalPrice",""));
            data.put("transaction_id" ,sharedPreferences.getInt("transactionId", 0));
            data.put("adult_ticket" ,  sharedPreferences.getInt("NoofChildTicket", 0));
            data.put("child_ticket" ,sharedPreferences.getInt("NoofAdultTicket", 0));
            data.put("debit" ,sharedPreferences.getInt("totalpointsfromSelection", 0));
            data.put("type" ,sharedPreferences.getBoolean("fullpaymentcredit", false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data" , data.toString());
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<TicketDetails> checkout = cineApiService.checkout(data.toString());
        checkout.enqueue(new Callback<TicketDetails>() {
            @Override
            public void onResponse(Response<TicketDetails> response) {
                if (response.body().getResponse().equalsIgnoreCase("Successfull")){
                    alert.showAlertDialogforhome(paymentEway, "Your order has been placed, You will recieve an email of your order Shortly. Thanks", false);
                }else {
                    alert.showAlertDialog(paymentEway,"Something went Wrong" ,false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
            collectDataAndCallRetrofit();
            }
        });

    }
}
