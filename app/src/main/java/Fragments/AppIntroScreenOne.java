package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exousiatech.cinewhoop.R;


public class AppIntroScreenOne extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View introOne = inflater.inflate(R.layout.appintro_one, container, false);

        return introOne;
    }

}
