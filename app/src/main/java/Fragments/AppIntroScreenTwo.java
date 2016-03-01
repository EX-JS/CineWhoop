package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exousiatech.cinewhoop.R;


public class AppIntroScreenTwo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View introTwo = inflater.inflate(R.layout.appintro_two, container, false);

        return introTwo;
    }

}
