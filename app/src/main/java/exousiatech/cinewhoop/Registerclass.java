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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.RegistrationResp;
import RetrofitPackage.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jagteshwar on 20-01-2016.
 */
public class Registerclass  extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView toolbarTitle , userNameIcon, emailRegIcon , passRegicon , phoneIcon;
    ArrayList<TextView> tv;
    Utilclass typechange;
    Typeface customIcon;
    EditText firstNameReg , lastNameReg , emailReg , passwordReg , phoneNumberReg;
    Button signUpBtn;
    Retrofit retrofit;
    Call<List<RegistrationResp>> reg;
    String jsonresp , tokentosave;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectionDetectorUtil connectionDetectorUtil;
    Dialog pDialog;
    LinearLayout linearLayoutRegisterration;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loginActivity = new LoginActivity() ;
        customIcon = Typeface.createFromAsset(getAssets(), "fonts/registepage.ttf");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Registration");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);

        linearLayoutRegisterration = (LinearLayout)findViewById(R.id.linearLayoutRegisterration);
        userNameIcon = (TextView)findViewById(R.id.user_name_reg_icon);
        emailRegIcon= (TextView)findViewById(R.id.email_reg_icon);
        passRegicon= (TextView)findViewById(R.id.pass_reg_icon);
        phoneIcon= (TextView)findViewById(R.id.phone_icon);
        userNameIcon.setTypeface(customIcon);
        emailRegIcon.setTypeface(customIcon);
        passRegicon.setTypeface(customIcon);
        phoneIcon.setTypeface(customIcon);

        firstNameReg = (EditText)findViewById(R.id.firstNameReg);
        lastNameReg= (EditText)findViewById(R.id.lastNameReg);
        emailReg= (EditText)findViewById(R.id.emailReg);
        passwordReg= (EditText)findViewById(R.id.passwordReg);
        phoneNumberReg= (EditText)findViewById(R.id.phoneNumberReg);
        tv.add(firstNameReg);
        tv.add(lastNameReg);
        tv.add(emailReg);
        tv.add(passwordReg);
        tv.add(phoneNumberReg);
        typechange.applycustomfont(tv);

        signUpBtn = (Button)findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(this);
        pDialog = new Dialog(Registerclass.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);

    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();}



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signUp:

                if (TextUtils.isEmpty(firstNameReg.getText().toString() )||TextUtils.isEmpty(lastNameReg.getText().toString() )
                        ||TextUtils.isEmpty(emailReg.getText().toString() )||TextUtils.isEmpty(passwordReg.getText().toString() )
                        ||TextUtils.isEmpty(phoneNumberReg.getText().toString() ) ){
                    for (int i = 1;i<tv.size(); i++){
                        if (TextUtils.isEmpty(tv.get(i).getText()
                        )){
                            tv.get(i).setError("This Field is Required");
                            tv.get(i).requestFocus();
                        }
                    }

                }else if(!isValidEmail(emailReg.getText().toString())){
                    emailReg.setError("Please enter a Valid Email");
                    emailReg.requestFocus();

                }else if (loginActivity.countdotsAfterAt(emailReg.getText().toString())){
                    emailReg.setError("Please enter a Valid Email");
                    emailReg.requestFocus();
                }else if (emailReg.getText().toString().indexOf(".")==0){
                    emailReg.setError("Please enter a Valid Email");
                }else if (passwordReg.getText().toString().length()<8){
                    passwordReg.setError("Please fill a password with more than 8 character(s)");
                }else  if (phoneNumberReg.getText().toString().length() < 10){
                    phoneNumberReg.setError("Please enter a valid Phone Number");
                    phoneNumberReg.requestFocus();
                }else  if (checkalphanumericStr(firstNameReg.getText().toString())){
                        firstNameReg.setError("Please enter a Valid Name");
                }else if (checkalphanumericStr(lastNameReg.getText().toString())){
                    lastNameReg.setError("Please enter a Valid Name");
                }else  if (!checkwithSpecialchar(firstNameReg.getText().toString())){
                    firstNameReg.setError("Please enter a Valid Name");
                }else if (!lastcheckwithSpecialchar(lastNameReg.getText().toString())) {
                    lastNameReg.setError("Please enter a valid Name");
                }else {
                    pDialog.show();
                    jsonresp =  makejsontostring(firstNameReg.getText().toString().trim() ,lastNameReg.getText().toString().trim(),emailReg.getText().toString(),passwordReg.getText().toString()
                            ,phoneNumberReg.getText().toString() );
                            callRetrofit();
                    Log.e("JsonData", jsonresp);

                }


                break;
        }
    }

    private boolean checkwithSpecialchar (CharSequence s) {
        String exp = "^[a-zA-Z ]*$";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(s);
        if (pattern.matcher(s).matches()){
            return true;
        }else {
            return false;
        }

    }

    private boolean lastcheckwithSpecialchar (CharSequence s) {
        String exp = "^[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(s);
        if (pattern.matcher(s).matches()){
            return true;
        }else {
            return false;
        }

    }
    private boolean checkalphanumericStr(String s) {
        int count = 0;
        if (s.length()>0){
            for (int i = 0;i<s.length();i++){
                if (Character.isDigit(s.charAt(i))){
                    count++;
                }

            }
            if (count>0){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    private void callRetrofit() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        reg = cineApiService.registrationInfo(jsonresp);

        reg.enqueue(new Callback<List<RegistrationResp>>() {

            @Override
            public void onResponse(Response<List<RegistrationResp>> response) {
                pDialog.dismiss();
                Log.e("Respon" , response.body().toString());
                Log.e("Respon" , response.body().get(0).getToken());
                Log.e("Respon" , response.body().get(0).getReply());

                if (TextUtils.equals(response.body().get(0).getReply(), "Registration Successful")){
                    Toast.makeText(Registerclass.this, "You have been registered Successfully", Toast.LENGTH_SHORT).show();
                    try {
                        byte[] data = Base64.decode(response.body().get(0).getToken(), Base64.DEFAULT);
                       tokentosave = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    saveuserDetails(firstNameReg.getText().toString(), lastNameReg.getText().toString(), emailReg.getText().toString(), tokentosave
                            , phoneNumberReg.getText().toString());
                    Intent it = new Intent(getApplicationContext(), MoviesActivity.class);
                    startActivity(it);

                }else {
                    if (TextUtils.equals(response.body().get(0).getReply(), "Registration Unsuccessful")){
                        Toast.makeText(Registerclass.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

                    }else if (TextUtils.equals(response.body().get(0).getReply(), "Email Already Exists")){
                        Toast.makeText(Registerclass.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Registerclass.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure (Throwable t) {
                pDialog.dismiss();

                Snackbar snackbar = Snackbar.make( linearLayoutRegisterration, "Registration Unsuccessful", Snackbar.LENGTH_LONG);
                snackbar.show();
//                Log.e("Respon" , t.getMessage());

            }
        });
    }

    public void saveuserDetails(String firtNametosend, String lastnametosend, String emailtosend, String token, String phonetosend) {

        editor.putString("name", firtNametosend+" "+lastnametosend);
        editor.putString("contact_phone", phonetosend);
        editor.putString("token", token);
        editor.putString("email", emailtosend);
        editor.putBoolean("userProfileExist", true);
        editor.putBoolean("loginDone", true);
        editor.apply();

    }

    public String makejsontostring(String firtNametosend, String lastnametosend, String emailtosend, String passwordtosend, String phonetosend) {
        JSONObject data  = new JSONObject();

        try {
//            signup.put( "signup" , data);
            data.put( "email_address" , emailtosend);
            data.put( "password" , passwordtosend);
            data.put( "first_name" , firtNametosend);
            data.put( "last_name" , lastnametosend);
            data.put( "contact_phone" , phonetosend);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return data.toString();

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
}
