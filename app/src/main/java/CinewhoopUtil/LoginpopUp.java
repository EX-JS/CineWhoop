package CinewhoopUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;



import java.io.UnsupportedEncodingException;
import java.util.List;

import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.RegistrationResp;
import RetrofitPackage.RetrofitUtil;
import exousiatech.cinewhoop.LoginActivity;
import exousiatech.cinewhoop.MoviesActivity;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.Registerclass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 08-02-2016.
 */
public class LoginpopUp implements View.OnClickListener {
    Dialog pDialog;
    Context context;
    Window window;
    Registerclass registerclass;
    String jsonresp , tokentosave;
    Call<List<RegistrationResp>> reg;
    EditText emailpopUp, phonepopUp;
    LoginActivity loginActivity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Dialog pDialogprogress;
    EmailAddressValidator validator;
    boolean status = false;

    public LoginpopUp (Context context){
        this.context = context;
        registerclass = new Registerclass();
        loginActivity  = new LoginActivity();
        validator = new EmailAddressValidator();
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pDialog = new Dialog(context);

        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.popup_for_guest_login);
        window = pDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams  layoutParams= new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        pDialog.findViewById(R.id.registerAsGuest).setOnClickListener(this);
        emailpopUp = (EditText)pDialog.findViewById(R.id.emailpopUp);
        phonepopUp = (EditText)pDialog.findViewById(R.id.phonepopUp);
        pDialog.setCancelable(true);


        pDialogprogress = new Dialog(context);
        pDialogprogress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialogprogress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialogprogress.setContentView(R.layout.progress_view);
        pDialogprogress.setCancelable(false);

        emailpopUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


//                else {
//                    status=false;
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                      }
        });
    }
    public void showDialog(){

        pDialog.show();
    }
    public void hideDialog(){
        pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(emailpopUp.getText())){
            emailpopUp.setError("Please fill this field.");
            emailpopUp.requestFocus();
        }else if (emailpopUp.getText().toString().indexOf(".")==0){
            emailpopUp.setError("The ID contains invalid character(s). Please choose another ID.");
//                    emailpopUp.setError("The ID contains invalid character(s). Please choose another ID.");
        }else if (!isEmailValid(emailpopUp.getText().toString())){
            emailpopUp.setError("The ID contains invalid character(s). Please choose another ID.");
        }else if (loginActivity.countdotsAfterAt(emailpopUp.getText().toString())){
            emailpopUp.setError("The ID contains invalid character(s). Please choose another ID.");
        }else if (TextUtils.isEmpty(phonepopUp.getText())){
            phonepopUp.setError("Please fill this field");
            phonepopUp.requestFocus();
        }else if (phonepopUp.getText().toString().length() < 10){
            phonepopUp.setError("Phone number should contain minimum of ten characters.");
            phonepopUp.requestFocus();
        }
        else {
            jsonresp = registerclass.makejsontostring("Guest", " ", emailpopUp.getText().toString(), phonepopUp.getText().toString()
                    , phonepopUp.getText().toString());
            callRetrofit();
            pDialogprogress.show();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    private void callRetrofit() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        reg = cineApiService.registrationInfo(jsonresp);

        reg.enqueue(new Callback<List<RegistrationResp>>() {

            @Override
            public void onResponse(Response<List<RegistrationResp>> response) {

                Log.e("Respon", response.body().toString());
                Log.e("Respon", response.body().get(0).getToken());
                Log.e("Respon", response.body().get(0).getReply());
                pDialogprogress.dismiss();
                pDialog.dismiss();

                 if (TextUtils.equals(response.body().get(0).getReply(), "Registration Successful")) {
                    Toast.makeText(context, "Sign In successful as a guest", Toast.LENGTH_SHORT).show();

                    try {
                        byte[] data = Base64.decode(response.body().get(0).getToken(), Base64.DEFAULT);
                        tokentosave = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    saveuserDetails("Guest", "", emailpopUp.getText().toString(), tokentosave
                            , phonepopUp.getText().toString());
                    Intent it = new Intent(context , MoviesActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                }else {
                     if (TextUtils.equals(response.body().get(0).getReply(), "Registration Unsuccessful")) {
                         Toast.makeText(context, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
//                    Email Already Exists
                     }else if (TextUtils.equals(response.body().get(0).getReply(), "Email Already Exists")){
                         Toast.makeText(context, "Email Already Exists", Toast.LENGTH_SHORT).show();
                     }
                     else{
                         Toast.makeText(context, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
                     }
                 }

            }

            @Override
            public void onFailure(Throwable t) {
                pDialogprogress.dismiss();
                pDialog.dismiss();
                Toast.makeText(context, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();

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
}
