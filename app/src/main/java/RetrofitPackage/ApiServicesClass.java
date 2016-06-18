package RetrofitPackage;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by jagteshwar on 19-01-2016.
 */
public interface ApiServicesClass {
    @GET("/admin/ser/movie.php")
    Call<List<CineWhoopDetail>> moviesInformation();

    @GET("/admin/ser/cinemalist.php")
    Call<List<CinemaDetail>> cinemalistInfo();

    @GET("/admin/ser/category.php")
    Call<CategoryDetails> categorylistInfo();

    @GET("/admin/ser/offers.php")
    Call<List<OfferDetail>> offerGetDetails();

    @GET("/admin/ser/coming_movies.php")
    Call<List<SoonDetail>> comingSoonDetails();

    @FormUrlEncoded
    @POST("/admin/ser/test/index.php")
    Call<TransactionStatus> transactionDetails(@Header("Authorization") String header, @Field("session_time") String sessionTime,@Field("AccessCode")String jsonResp);

    @FormUrlEncoded
    @POST("/admin/ser/coupon.php")
    Call<List<Coupon>> couponcall(@Header("Authorization") String header, @Field("session_time") String sessionTime, @Field("coupon_value")String couponcode);

    @FormUrlEncoded
    @POST("/admin/ser/checkout_old.php")
    Call<TicketDetails> checkout(@Header("Authorization") String header, @Field("session_time") String sessionTime,@Field("checkout")String jsonStr);

    @FormUrlEncoded
    @POST("/admin/ser/order_detail.php")
    Call<OrderDetails> orderinfo(@Header("Authorization") String header, @Field("session_time") String sessionTime,@Field("email_id")String email);

    @FormUrlEncoded
    @POST("/admin/ser/forgotpass.php")
    Call<ForgetPassStatus> passRequest(@Field("email_id")String email);

    @FormUrlEncoded
    @POST("/admin/ser/confirmpass.php")
    Call<ForgetPassStatus> newPassReq(@Field("email_id")String email,@Field("code")String code,@Field("password")String password);

    @FormUrlEncoded
    @POST("/admin/ser/signup.php")
    Call<List<RegistrationResp>> registrationInfo(@Field("signup")String jsonResp);

    @FormUrlEncoded
    @POST("/admin/ser/login.php")
    Call<LoginResponseCineWhoop> login(@Field("email_address")String email , @Field("password")String password);

    @FormUrlEncoded
    @POST("/admin/ser/cinema_name.php")
    Call<MovieTimmingDetails> getcinemaName(@Field("movie_id")String movieId);


}
