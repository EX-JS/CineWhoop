package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;

import Fragments.AppIntroScreenFour;
import Fragments.AppIntroScreenOne;
import Fragments.AppIntroScreenThree;
import Fragments.AppIntroScreenTwo;

/**
 * Created by jagteshwar on 05-01-2016.
 */

public class IntroCinewhoop extends AppIntro2 {
    SharedPreferences cinewhoopPref;
    SharedPreferences.Editor editor;

    @Override
    public void init(Bundle savedInstanceState) {
        cinewhoopPref = getSharedPreferences("CineWhoop" , Context.MODE_PRIVATE);
        editor = cinewhoopPref.edit();
        addSlide(new AppIntroScreenOne());
        addSlide(new AppIntroScreenTwo());
        addSlide(new AppIntroScreenThree());
        addSlide(new AppIntroScreenFour());

        setFadeAnimation();
    }

    @Override
    public void onDonePressed() {
        Intent it = new Intent(this , HomeActivity.class);
        editor.putInt("IntroDone", 1);
        editor.commit();
        startActivity(it);
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}
