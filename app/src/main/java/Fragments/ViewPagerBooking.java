package Fragments;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jagteshwar on 27-01-2016.
 */
public class ViewPagerBooking extends FragmentStatePagerAdapter {
    Fragment fragment;
    List<String> images;
    public ViewPagerBooking(FragmentManager fm, List<String> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new BookingScreenFragment();
        Bundle imagesbundle = new Bundle();
        imagesbundle.putString("imageAtIndex", images.get(position));
        fragment.setArguments(imagesbundle);

        return fragment ;
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
