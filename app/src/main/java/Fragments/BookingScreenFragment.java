package Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import CinewhoopUtil.ConfigClass;
import exousiatech.cinewhoop.R;

/**
 * Created by jagteshwar on 27-01-2016.
 */
public class BookingScreenFragment extends Fragment {
    ImageView movieback;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_booking_screen , container,  false);
        movieback = (ImageView)v.findViewById(R.id.ImageBackBooking);
        Bundle imagesBundle = getArguments();
        if (imagesBundle!=null){
            String str = imagesBundle.getString("imageAtIndex");
            Picasso.with(getActivity())
                    .load(ConfigClass.BASE_URL + "admin/upload/" + str)
                    .placeholder(R.drawable.preloader)
                    .into(movieback);
        }
        return v;
    }
}
