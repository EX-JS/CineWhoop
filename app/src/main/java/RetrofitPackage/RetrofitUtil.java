package RetrofitPackage;

import CinewhoopUtil.ConfigClass;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by jagteshwar on 03-02-2016.
 */
public class RetrofitUtil {
    Retrofit retrofit;
    public ApiServicesClass getRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ConfigClass.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServicesClass cinewhoopApiService = retrofit.create(ApiServicesClass.class);
        return cinewhoopApiService;
    }
}
