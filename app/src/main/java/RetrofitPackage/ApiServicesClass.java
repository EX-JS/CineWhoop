package RetrofitPackage;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by jagteshwar on 19-01-2016.
 */
public interface ApiServicesClass {
    @GET("/umax/ser/movie.php")
    Call<List<CineWhoopDetail>> moviesInformation();

    @GET("/umax/ser/cinemalist.php")
    Call<List<CinemaDetail>> cinemalistInfo();

    @GET("/umax/ser/category.php")
    Call<CategoryDetails> categorylistInfo();

    @GET("/umax/ser/offers.php")
    Call<List<OfferDetail>> offerGetDetails();

    @GET("/umax/ser/coming_movies.php")
    Call<List<SoonDetail>> comingSoonDetails();

    @FormUrlEncoded
    @POST("/test/index.php")
    Call<TransactionStatus> transactionDetails(@Field("AccessCode")String jsonResp);

    @FormUrlEncoded
    @POST("/umax/ser/coupon.php")
    Call<List<Coupon>> couponcall(@Field("coupon_value")String couponcode);

    @FormUrlEncoded
    @POST("/umax/ser/checkout.php")
    Call<TicketDetails> checkout(@Field("checkout")String jsonStr);

    @FormUrlEncoded
    @POST("/umax/ser/order_detail.php")
    Call<OrderDetails> orderinfo(@Field("email_id")String email);

    @FormUrlEncoded
    @POST("/umax/ser/signup.php")
    Call<List<RegistrationResp>> registrationInfo(@Field("signup")String jsonResp);

    @FormUrlEncoded
    @POST("/umax/ser/login.php")
    Call<ResponseBody> login(@Field("email_address")String email , @Field("password")String password);
}
