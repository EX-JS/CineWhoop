package Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exousiatech.cinewhoop.R;


/**
 * Created by john on 12/30/2015.
 */
public class AppIntroScreenFour extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View introOne = inflater.inflate(R.layout.appintro_four, container, false);

        return introOne;
    }
}
