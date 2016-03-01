package CinewhoopUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.eway.payment.sdk.android.RapidAPI;
import com.eway.payment.sdk.android.RapidConfigurationException;
import com.eway.payment.sdk.android.beans.NVPair;
import com.eway.payment.sdk.android.entities.EncryptItemsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CinemaDetail;
import RetrofitPackage.Coupon;
import RetrofitPackage.OrderDetails;
import RetrofitPackage.RetrofitUtil;
import RetrofitPackage.TransactionStatus;
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
    public RestroCalls(PaymentEway paymentEway) {
        this.paymentEway = paymentEway;
    }
    public RestroCalls(PayAndCheckoutClass payAndCheckoutClass) {
        this.payAndCheckoutClass = payAndCheckoutClass;
    }

    public void callretrofitapi(final String acesscode , Context context)
    {

        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<TransactionStatus> paymentstatus =  cineApiService.transactionDetails(acesscode);

        paymentstatus.enqueue(new Callback<TransactionStatus>() {
            @Override
            public void onResponse( final Response<TransactionStatus> response) {
                result = response.body();
                Log.e("Response" , response.body().toString());

                    for (int i  = 0;i<result.getTransactions().size();i++){
                        if (result.getTransactions().get(i).getTransactionStatus()){
                            editor.putInt("transactionId" ,result.getTransactions().get(i).getTransactionID());
                            editor.putBoolean("transactionidExist", true);
                            editor.apply();
                            transactionboolean = result.getTransactions().get(i).getTransactionStatus();
                            paymentEway.valueforpaymentstatus(transactionboolean);


                        }
                        else {
                            transactionboolean = false;
                            paymentEway.valueforpaymentstatus(transactionboolean);

                        }
                        Log.e("payment" , result.getTransactions().get(i).getTransactionStatus()+" "+result.getTransactions().get(i).getTransactionID()+" "+result.getTransactions().get(i).getTotalAmount() );

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("fail" , t.getMessage());
                result = null;
                transactionboolean = false;
                paymentEway.valueforpaymentstatus(transactionboolean);

            }
        });

    }
    public void callretrofitforOrderdetails(final String emailid ){


        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<OrderDetails> orderstatus =  cineApiService.orderinfo(emailid);

        orderstatus.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse( final Response<OrderDetails> response) {
                if (response.body().getTotalCredit().get(0).getCredit().equalsIgnoreCase("")) {
                    creditpoints = "";
                    Log.e("credit1" , creditpoints);

                    paymentEway.valueOfCreditPoints(creditpoints);
                }else {
                    creditpoints = response.body().getTotalCredit().get(0).getCredit();
                    Log.e("credit2" , creditpoints);
                    paymentEway.valueOfCreditPoints(creditpoints);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                creditpoints = "";
                Log.e("fail" , creditpoints);

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
        Call<List<Coupon>> couponStatus =  cineApiService.couponcall(acesscode);
        couponStatus.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Response<List<Coupon>> response) {
                Log.e("Response", response.body().get(0).getCouponOff());
                payAndCheckoutClass.valueforcoupontoset(response.body().get(0).getCouponOff());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("fail", t.getMessage() + " ");

                payAndCheckoutClass.valueforcoupontoset("");

            }
        });

    }

    public void callretrofitdataforrates(final int cpoints, final int totalpointsfromSelection, final double total, final int countnoofChildscredit, final int countnoofAdultscredit) {
        sharedPreferences = paymentEway.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.total = total;
        Log.e("ratesadult" ,sharedPreferences.getString("ratesforAdultTicket","0") );
        Log.e("rateschild" ,sharedPreferences.getString("ratesforChildTicket","0") );

        priceTicketAdult = Double.parseDouble(sharedPreferences.getString("ratesforAdultTicket","0"));
        priceTicketChild = Double.parseDouble(sharedPreferences.getString("ratesforChildTicket","0"));
        double totalamounttobesubtracted = (countnoofChildscredit*priceTicketChild)+(countnoofAdultscredit*priceTicketAdult);
        Log.e("amount ghat" , totalamounttobesubtracted+"");
        Log.e("amount ghat2" , this.total+"");

        double totalaftersubtraction =  this.total-totalamounttobesubtracted;
        Log.e("resp amount1" ,totalaftersubtraction+"" );
        paymentEway.amounttobededucted(totalaftersubtraction);
            }
    }

