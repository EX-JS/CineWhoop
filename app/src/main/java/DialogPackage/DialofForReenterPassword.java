package DialogPackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.EmailAddressValidator;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.ForgetPassStatus;
import RetrofitPackage.RegistrationResp;
import RetrofitPackage.RetrofitUtil;
import exousiatech.cinewhoop.LoginActivity;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.Registerclass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 21-04-2016.
 */
public class DialofForReenterPassword implements View.OnClickListener {
    Dialog pDialog;
    Context context;
    Window window;
    Registerclass registerclass;
    String email, password , code;
    Call<ForgetPassStatus> reg;

    LoginActivity loginActivity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Dialog pDialogprogress;
    EmailAddressValidator validator;
    Activity activity;
    EditText enterCodePopUp , renterPasspopUp,passPopUp;

    public DialofForReenterPassword(Context context){
        this.context = context;
        registerclass = new Registerclass();
        loginActivity = new LoginActivity();
        validator = new EmailAddressValidator();
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pDialog = new Dialog(context);


        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.re_enter_pass_dialog);
        window = pDialog.getWindow();
        pDialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        pDialog.findViewById(R.id.renterContinue).setOnClickListener(this);
        enterCodePopUp= (EditText) pDialog.findViewById(R.id.enterCodePopUp);
        renterPasspopUp= (EditText) pDialog.findViewById(R.id.renterPasspopUp);
        passPopUp= (EditText) pDialog.findViewById(R.id.passPopUp);
        pDialog.setCancelable(true);


        pDialogprogress = new Dialog(context);
        pDialogprogress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialogprogress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialogprogress.setContentView(R.layout.progress_view);
        pDialogprogress.setCancelable(false);
    }

    public void showDialog() {
        pDialog.show();
    }
    public void hideDialog() {
       if (pDialog!=null){
           pDialog.dismiss();
       }
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(passPopUp.getText())){
            passPopUp.setError("Please fill a this field");
        }else if (TextUtils.isEmpty(renterPasspopUp.getText())){
            renterPasspopUp.setError("Please fill this field");
        }else if (TextUtils.isEmpty(enterCodePopUp.getText())){
            enterCodePopUp.setError("Please fill this field");
        }else if (!passPopUp.getText().toString().equals(renterPasspopUp.getText().toString())){
            renterPasspopUp.setError("Password do not match");
        }else if (passPopUp.getText().length()<8){
            passPopUp.setError("Please fill a password with more than 8 character(s)");
        }else if (enterCodePopUp.getText().length()<4){
            enterCodePopUp.setError("Please fill a valid code");
        }else {
            email = sharedPreferences.getString(ConfigClass.Email_Forget_pass ,"");
            password = passPopUp.getText().toString();
            code  =enterCodePopUp.getText().toString();
            callRetrofitResetPass();
        }
    }

    private void callRetrofitResetPass() {
        pDialog.show();
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<ForgetPassStatus> service =cineApiService.newPassReq(email , code , password);
        service.enqueue(new Callback<ForgetPassStatus>() {
            @Override
            public void onResponse(Response<ForgetPassStatus> response) {
                pDialog.dismiss();
                String status = response.body().getStatus();
                if (status.equalsIgnoreCase("updated")){
                    hideDialog();
                    Toast.makeText(context, "Password updated successfully ,  you can now login with your new password", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, status, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "Some error occurred  ,Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
