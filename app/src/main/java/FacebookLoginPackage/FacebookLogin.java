package FacebookLoginPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import CinewhoopUtil.AlertClass;
import CinewhoopUtil.ConfigClass;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.RegistrationResp;
import RetrofitPackage.RetrofitUtil;
import exousiatech.cinewhoop.LoginActivity;
import exousiatech.cinewhoop.Registerclass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Exousia - Aamir on 18/11/2015.
 */
public class FacebookLogin {


    CallbackManager callbackManager;
    LoginActivity loginActivity;
    String facebookNameFetch, facebookIDFetch;
    Bitmap bitmap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Retrofit retrofit;
    Call<List<RegistrationResp>> reg;
    Registerclass registerclass;
    String firstNameFb, lastNameFb;
    StringBuilder stringBuilder;
    String json , emailfbtosave , tokentosave;
    AlertClass alert;
    String  message = "We are not Able to fetch your Email from Facebook, So kindly register manually. Sorry for the inconvenience caused";


    public FacebookLogin(CallbackManager callbackManager, LoginActivity loginActivity) {
        this.callbackManager = callbackManager;
        this.loginActivity = loginActivity;

        sharedPreferences = loginActivity.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        registerclass  = new Registerclass();
        alert = new AlertClass();
    }


    public void fblogin() {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    final JSONObject object,
                                    GraphResponse response) {
                                // Application code

                                try {

                                    Log.e("response", object.getString("id"));
                                    Log.e("response", object.getString("name"));


                                    facebookNameFetch = object.getString("name");
                                    facebookIDFetch = object.getString("id");
                                    if (object.has("email")){
                                        emailfbtosave = object.getString("email");
                                    }else {
                                        alert.showAlertDialog(loginActivity, message, false);
                                    }

                                    String nameSplitArr[] = facebookNameFetch.split(" ");


                                        if (nameSplitArr.length > 0) {
                                            firstNameFb = nameSplitArr[0];
                                            stringBuilder = new StringBuilder();
                                            for (int i = 1; i < nameSplitArr.length; i++) {

                                                stringBuilder.append(nameSplitArr[i]);
                                            }
                                            lastNameFb = stringBuilder.toString();
                                            json = registerclass.makejsontostring(firstNameFb, lastNameFb, object.getString("email"), facebookIDFetch, " ");
                                            if (object.has("email")){
                                                callRetrofit();
                                                 }else {

                                            }
                                        } else {
                                            firstNameFb = " ";
                                            lastNameFb = " ";
                                            json = registerclass.makejsontostring(firstNameFb, lastNameFb, object.getString("email"), facebookIDFetch, " ");
                                            if (object.has("email")){
                                                callRetrofit();
                                            }else {

                                            }
                                        }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id , name , email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

    }


    private void callRetrofit() {

        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService =  retrofitUtil.getRetrofit();
        Log.e("jsontosend", json);
        reg = cineApiService.registrationInfo(json);

        reg.enqueue(new Callback<List<RegistrationResp>>() {

            @Override
            public void onResponse(Response<List<RegistrationResp>> response) {

                Log.e("Respon", response.body().toString());
                Log.e("Respon", response.body().get(0).getToken());
                Log.e("Respon", response.body().get(0).getReply());

                if (TextUtils.equals(response.body().get(0).getReply(), "Email Already Exists")) {
                    Log.e("unSucess", "");
                    loginActivity.callretrofitLoginmethod(emailfbtosave, facebookIDFetch);
                    saveuserFBDetailsforRegUnsucessful();
                } else if (TextUtils.equals(response.body().get(0).getReply(), "Registration Successful")) {
                    Log.e("Sucess", "");
                    try {
                        byte[] data = Base64.decode(response.body().get(0).getToken(), Base64.DEFAULT);
                        tokentosave = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    saveuserFBDetails(emailfbtosave, tokentosave);
                    loginActivity.backgo();
                }

            }

            @Override
            public void onFailure(Throwable t) {
            loginActivity.showSnackBar();

            }
        });
    }

    private void saveuserFBDetailsforRegUnsucessful() {
        String uriImage = "https://graph.facebook.com/" + facebookIDFetch + "/picture?width=720&height=720";
        editor.putString("ImageUri", uriImage);
        editor.putString("name", facebookNameFetch);
        editor.putString("contact_phone", " ");
        editor.putString("email", emailfbtosave);
        editor.putBoolean("ImageUriExist", true);
        editor.putBoolean("userProfileExist", true);
        editor.putBoolean("loginDone", true);
        editor.commit();
    }

    private void saveuserFBDetails( String emailtosend, String token ) {
        String uriImage = "https://graph.facebook.com/" + facebookIDFetch + "/picture?width=720&height=720";
        editor.putString("ImageUri", uriImage);
        editor.putString("name", facebookNameFetch);
        editor.putString("contact_phone", " ");
        editor.putString("token", token);
        editor.putString("email", emailfbtosave);
        editor.putBoolean("ImageUriExist", true);
        editor.putBoolean("userProfileExist", true);
        editor.putBoolean("loginDone", true);
        editor.commit();
    }
}
