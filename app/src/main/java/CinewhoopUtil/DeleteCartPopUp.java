package CinewhoopUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.Window;

import Database.DatabaseHelperCinewhoop;
import exousiatech.cinewhoop.R;

/**
 * Created by jagteshwar on 11-02-2016.
 */
public class DeleteCartPopUp implements View.OnClickListener {
    Dialog pDialog;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelperCinewhoop accessdatabase;
    Activity activity;
    public DeleteCartPopUp(Context context ){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF ,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessdatabase = new DatabaseHelperCinewhoop(context);
        pDialog = new Dialog(context);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.alert_view_pref_clear);
        pDialog.findViewById(R.id.deleteoffer).setOnClickListener(this);
        pDialog.findViewById(R.id.notNow).setOnClickListener(this);
        pDialog.setCancelable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteoffer:
                editor.putBoolean("MovieExist",false);
                editor.putBoolean("offerExist", false);
                editor.commit();
                accessdatabase.deleteTableOffer();
                pDialog.dismiss();

                break;
            case R.id.notNow:
                pDialog.dismiss();
                break;
        }
    }
    public  void showDialog(){
        pDialog.show();
    }
    public void hideDialog(){
        pDialog.dismiss();
    }
}
