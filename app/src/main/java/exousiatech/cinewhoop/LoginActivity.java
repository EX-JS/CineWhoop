package exousiatech.cinewhoop;


import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import DialogPackage.DialogForgetPassword;
import DialogPackage.LoginpopUp;
import FacebookLoginPackage.FacebookLogin;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.LoginResponseCineWhoop;
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
    Button facebook_layout, signinGuest, signIn;
    TextView userIcon, passIcon, signupBtn, backIcon, forgetpassLogin;
    Typeface typeface;
    Intent it;
    Call<LoginResponseCineWhoop> loginapi;
    Typeface backbtntypeface;
    EditText emailLog, passLog;
    RelativeLayout outsideRel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectionDetectorUtil connectionDetectorUtil;
    Dialog pDialog;
    String emailtosave;
    LinearLayout linearLayoutFacebook, registrationLayout;
    LoginpopUp loginpopUp;
    Activity activity;
    DialogForgetPassword forgetPassword;

    public LoginActivity() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        forgetPassword = new DialogForgetPassword(LoginActivity.this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/flaticon.ttf");
        backbtntypeface = Typeface.createFromAsset(getAssets(), "fonts/backttf.ttf");
        facebook_layout = (Button) findViewById(R.id.facebookLogin);
        facebook_layout.setOnClickListener(this);
        signinGuest = (Button) findViewById(R.id.signInAsGuest);
        signinGuest.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        linearLayoutFacebook = (LinearLayout) findViewById(R.id.linearLayoutFacebook);
        registrationLayout = (LinearLayout) findViewById(R.id.registrationLayout);
        registrationLayout.setOnClickListener(this);
        userIcon = (TextView) findViewById(R.id.user_iconid);
        passIcon = (TextView) findViewById(R.id.pass_id);
        signupBtn = (TextView) findViewById(R.id.signupBtn);
        backIcon = (TextView) findViewById(R.id.back_icon);
        forgetpassLogin = (TextView) findViewById(R.id.forgetpassLogin);
        emailLog = (EditText) findViewById(R.id.emailLog);
        passLog = (EditText) findViewById(R.id.passLog);
        outsideRel = (RelativeLayout) findViewById(R.id.outsideRel);
        outsideRel.setOnClickListener(this);
        SpannableString content = new SpannableString("Sign up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signupBtn.setText(content);
//        signupBtn.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        forgetpassLogin.setOnClickListener(this);
        userIcon.setTypeface(typeface);
        passIcon.setTypeface(typeface);
        backIcon.setTypeface(backbtntypeface);

        pDialog = new Dialog(LoginActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil = new ConnectionDetectorUtil();
        IntentFilter intentFilter = new IntentFilter();
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
        switch (v.getId()) {
            case R.id.facebookLogin:
                callbackManager = CallbackManager.Factory.create();
                fbLogin = new FacebookLogin(callbackManager, this);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                fbLogin.fblogin();
                break;
            case R.id.signInAsGuest:
                loginpopUp = new LoginpopUp(LoginActivity.this, activity);
                loginpopUp.showDialog();

                break;
            case R.id.signIn:

                if (TextUtils.isEmpty(emailLog.getText().toString())) {
                    emailLog.setError("Please fill your E-mail.");

                } else if (emailLog.getText().toString().indexOf(".") == 0) {
                    emailLog.setError("Please fill your E-mail.");
                }
//                else if (countdotsAfterAt(emailLog.getText().toString())){
//                    emailLog.setError("Please fill your E-mail.");
//
//                }
                else if (TextUtils.isEmpty(passLog.getText().toString())) {
                    passLog.setError("Please fill your password");

                } else {
                    pDialog.show();
                    callretrofitLoginmethod(emailLog.getText().toString(), passLog.getText().toString());
                }


                break;
            case R.id.back_icon:
                super.onBackPressed();
                break;
            case R.id.forgetpassLogin:

                forgetPassword.showDialog();
                break;
            case R.id.registrationLayout:
                it = new Intent(this, Registerclass.class);
                startActivity(it);
                break;
            case R.id.outsideRel:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(outsideRel.getWindowToken(), 0);
                break;

        }

    }

    public boolean countdotsAfterAt(String s) {
        int count = 0;
        String arr[] = s.split("@");
        if (arr.length > 0) {
            String atr = arr[1];
            for (int i = 0; i < atr.length(); i++) {
                if (atr.charAt(i) == 46) {
                    count++;
                }
            }
            if (count <= 2) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    public void callretrofitLoginmethod(String loginemail, String loginPass) {
        emailtosave = loginemail;
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        loginapi = cineApiService.login(loginemail, loginPass);
        loginapi.enqueue(new Callback<LoginResponseCineWhoop>() {
            @Override
            public void onResponse(Response<LoginResponseCineWhoop> response) {
                pDialog.dismiss();
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, "You have been Logged In Successfully", Toast.LENGTH_SHORT).show();
                    String firstName = response.body().getFirstName();
                    String lastName = response.body().getLastName();
                    String contact_phone = response.body().getContactPhone();
                    String key = response.body().getKey();
                    byte[] data = Base64.decode(key, Base64.DEFAULT);
                    String token = null;
                    try {
                        token = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    editor.putString("name", firstName + " " + lastName);
                    editor.putString("contact_phone", contact_phone);
                    editor.putString(ConfigClass.Token, token);
                    editor.putString(ConfigClass.SessionTime, response.body().getSessionTime());
                    editor.putString("email", emailtosave);
                    editor.putBoolean("userProfileExist", true);
                    editor.putBoolean("loginDone", true);
                    editor.commit();
                    onBackPressed();


                } else if (response.body().getStatus().equalsIgnoreCase("0")) {
                    showSnackBar();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                pDialog.dismiss();
                Snackbar snackbar = Snackbar.make(linearLayoutFacebook, "Some error occurred. Please try again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


    public void backgo() {
        super.onBackPressed();
    }

    public void showSnackBar() {
        Snackbar snackbar = Snackbar.make(linearLayoutFacebook, "Some error occurred. Please try again", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
