package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import CinewhoopUtil.ConfigClass;
import Database.DatabaseHelperCinewhoop;

/**
 * Created by john on 12/30/2015.
 */
public class SplashScreen extends AppCompatActivity {
    SharedPreferences cinewhoopPref ,sharedPreferences;
    SharedPreferences.Editor editor ,editorformain;
    boolean INTRO_DONE = false;
    DatabaseHelperCinewhoop accesdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        accesdatabase = new DatabaseHelperCinewhoop(this);
        setContentView(R.layout.splash_screen);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF ,Context.MODE_PRIVATE);
        editorformain = sharedPreferences.edit();
        cinewhoopPref = getSharedPreferences("CineWhoop" , Context.MODE_PRIVATE);
        editor = cinewhoopPref.edit();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (sharedPreferences.getBoolean("MovieExist",false)||accesdatabase.offerExistornot()){
                    editor.putBoolean("MovieExist",false);
                    editor.putBoolean("offerExist",false);
                    editor.apply();
                }
                INTRO_DONE = getStatusIntro();
                if (!INTRO_DONE) {
                    Intent intent = new Intent(SplashScreen.this, IntroCinewhoop.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);
    }

    private boolean getStatusIntro() {
        if (cinewhoopPref.getInt("IntroDone" , -1) ==1)
        {
            return true;
        }
        return false;
    }
}
