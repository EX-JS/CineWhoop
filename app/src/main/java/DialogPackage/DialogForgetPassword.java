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
public class DialogForgetPassword implements View.OnClickListener {
    Dialog pDialog;
    Context context;
    Window window;
    Registerclass registerclass;
    String jsonresp, tokentosave;
    Call<List<RegistrationResp>> reg;
    EditText emailpopUp, phonepopUp;
    LoginActivity loginActivity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Dialog pDialogprogress;
    EmailAddressValidator validator;
    Activity activity;
    DialofForReenterPassword loginpopUp;

    public DialogForgetPassword(Context context) {
        this.context = context;
        registerclass = new Registerclass();
        loginActivity = new LoginActivity();
        validator = new EmailAddressValidator();
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pDialog = new Dialog(context);


        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.forget_pass_dialog);
        window = pDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        pDialog.findViewById(R.id.forgepasswordContinue).setOnClickListener(this);
        emailpopUp = (EditText) pDialog.findViewById(R.id.emailpopUp);
        pDialog.setCancelable(true);


        pDialogprogress = new Dialog(context);
        pDialogprogress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialogprogress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialogprogress.setContentView(R.layout.progress_view);
        pDialogprogress.setCancelable(false);
        this.activity = activity;
        loginpopUp = new DialofForReenterPassword(context);
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
        if (TextUtils.isEmpty(emailpopUp.getText())){
            emailpopUp.setError("Please enter your email");
        }else {
            emailpopUp.setError(null);
            callRetrofitForPassword(emailpopUp.getText().toString());
        }


    }

    private void callRetrofitForPassword(final String email) {
        pDialog.show();
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<ForgetPassStatus> service =cineApiService.passRequest(email);
        service.enqueue(new Callback<ForgetPassStatus>() {
            @Override
            public void onResponse(Response<ForgetPassStatus> response) {
                pDialog.dismiss();
                String status = response.body().getStatus();

                if (status.equalsIgnoreCase("verification code sent to your registered email ")){
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    editor.putString(ConfigClass.Email_Forget_pass, email);
                    editor.commit();
                    hideDialog();
                    loginpopUp.showDialog();
                }else {
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                pDialog.dismiss();
                Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
