package exousiatech.cinewhoop;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.LoginpopUp;
import FacebookLoginPackage.FacebookLogin;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.RetrofitUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jagteshwar on 06-01-2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManager;
    FacebookLogin fbLogin;
    Button facebook_layout, signinGuest , signIn;
    TextView  userIcon , passIcon  , signupBtn  ,backIcon;
    Typeface typeface;
    Intent it;
    Call<ResponseBody> loginapi;
    Typeface backbtntypeface;
    EditText  emailLog  ,passLog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectionDetectorUtil connectionDetectorUtil;
    Dialog pDialog;
    String emailtosave;
    LinearLayout linearLayoutFacebook;
    LoginpopUp  loginpopUp;
    public LoginActivity(){


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/flaticon.ttf");
        backbtntypeface = Typeface.createFromAsset(getAssets(),"fonts/backttf.ttf");
        facebook_layout = (Button) findViewById(R.id.facebookLogin);
        facebook_layout.setOnClickListener(this);
        signinGuest = (Button)findViewById(R.id.signInAsGuest);
        signinGuest.setOnClickListener(this);
        signIn = (Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        linearLayoutFacebook = (LinearLayout)findViewById(R.id.linearLayoutFacebook);
        userIcon = (TextView)findViewById(R.id.user_iconid);
         passIcon= (TextView)findViewById(R.id.pass_id);
        signupBtn= (TextView)findViewById(R.id.signupBtn);
        backIcon= (TextView)findViewById(R.id.back_icon);
        emailLog= (EditText)findViewById(R.id.emailLog);
        passLog= (EditText)findViewById(R.id.passLog);

        SpannableString content = new SpannableString("Sign up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signupBtn.setText(content);
        signupBtn.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        userIcon.setTypeface(typeface);
        passIcon.setTypeface(typeface);
        backIcon.setTypeface(backbtntypeface);

        pDialog = new Dialog(LoginActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil=new ConnectionDetectorUtil();
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionDetectorUtil, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionDetectorUtil);
        connectionDetectorUtil.dismissDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.facebookLogin:
                callbackManager = CallbackManager.Factory.create();
                fbLogin = new FacebookLogin(callbackManager, this);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                fbLogin.fblogin();
                break;
            case R.id.signInAsGuest:
                loginpopUp = new LoginpopUp(LoginActivity.this);
                loginpopUp.showDialog();

                break;
            case R.id.signIn:

                if (TextUtils.isEmpty(emailLog.getText().toString())){
                    emailLog.setError("Please fill your E-mail.");

                }else if (emailLog.getText().toString().indexOf(".")==0){
                    emailLog.setError("Please fill your E-mail.");
                }
                else if (countdotsAfterAt(emailLog.getText().toString())){
                    emailLog.setError("Please fill your E-mail.");

                }
                else if (TextUtils.isEmpty(passLog.getText().toString())){
                    passLog.setError("Please fill your password");

                }else{
                    pDialog.show();
                    callretrofitLoginmethod(emailLog.getText().toString(), passLog.getText().toString());
                }


                break;
            case R.id.back_icon:
                super.onBackPressed();
                break;
            case R.id.signupBtn:
                it  = new Intent(this, Registerclass.class);
                startActivity(it);
                break;

        }

    }

    public boolean countdotsAfterAt(String s) {
        int count = 0;
        String arr[] = s.split("@");
        if (arr.length>0){
            String atr  = arr[1];
            for (int i=0;i<atr.length();i++) {
                if (atr.charAt(i)==46){
                    count++;
                }
            }
            if (count<=2){
                return false;
            }else {
                return true;
            }
        }
        else {
            return true;
        }

    }

    public void callretrofitLoginmethod(String loginemail, String loginPass) {
                emailtosave = loginemail;
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        loginapi = cineApiService.login(loginemail,loginPass);
        loginapi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                Log.e("resp" , response.body().toString());
                pDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    parseResponse(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                    pDialog.dismiss();
                Snackbar snackbar = Snackbar.make(linearLayoutFacebook , "Some error occured. Please try again", Snackbar.LENGTH_LONG);
                snackbar.show();
                Log.e("error", t.getMessage());
            }
        });
    }

    private void parseResponse(JSONObject response) {

        try {
            Log.e("Tag" , response.toString());
           String status =  response.getString("status");
            Log.e("Tag" , status);
            if (status.equalsIgnoreCase("1") ){
                Log.e("Tag", "login success");
                Toast.makeText(LoginActivity.this, "You have been Logged In Successfully", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(signupBtn , "You have been Logged In Successfully", Snackbar.LENGTH_SHORT);
                snackbar.show();
                String firstName = response.getString("first_name");
                String lastName =response.getString("last_name");
                String contact_phone =response.getString("contact_phone");
                String key =response.getString("key");
                byte[] data = Base64.decode(response.getString("key"), Base64.DEFAULT);
                String token = new String(data, "UTF-8");
                Log.e("Tag", token);
                editor.putString("name", firstName + " " + lastName);
                editor.putString("contact_phone", contact_phone);
                editor.putString("token", token);
                editor.putString("email", emailtosave);
                editor.putBoolean("userProfileExist", true);
                editor.putBoolean("loginDone", true);
                editor.commit();
                super.onBackPressed();


            }else if (status.equalsIgnoreCase("0")){
                Log.e("Tag" , "login uinsucess");
                showSnackBar();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

    }
    public void backgo(){
        super.onBackPressed();
    }
    public void showSnackBar(){

        Snackbar snackbar = Snackbar.make(linearLayoutFacebook, "Some error occured. Please try again", Snackbar.LENGTH_LONG);
        snackbar.show();

    }
}
