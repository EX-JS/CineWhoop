package CinewhoopUtil;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RetrofitPackage.CineWhoopDetail;

/**
 * Created by jagteshwar on 30-01-2016.
 */
public class filterClasstoFilter {

    SimpleDateFormat mDateformat;
    Date min ,max , test;

    public ArrayList<CineWhoopDetail> filter(List<CineWhoopDetail> listFilter, String query) {
        query = query.toLowerCase();
        final ArrayList<CineWhoopDetail> filteredContactList = new ArrayList<>();

        for (CineWhoopDetail model : listFilter) {


            if (model != null) {
//                    Log.e("Person",model.getName());
                final String text = model.getCinema_id().toLowerCase();
                if (text.contains(query)) {
                    filteredContactList.add(model);
                }

            } else {
                Log.e("Person", "null");
            }

        }
        if (query.equals("")) {
            filteredContactList.add(0, null);
        }
        return filteredContactList;
    }
    public ArrayList<CineWhoopDetail> filterbydatemethod(List<CineWhoopDetail> listFilter, String query) {
        query = query.toLowerCase();

        final ArrayList<CineWhoopDetail> filteredContactList = new ArrayList<>();

        for (CineWhoopDetail model : listFilter) {


            if (model != null) {

//                    Log.e("Person",model.getName());
                final String datestrmin = model.getReleaseDate().toLowerCase();
                final String datestrmax = model.getMovie_expiry_date().toLowerCase();
                test = convertStrToDate(query);
                min = convertStrToDate(datestrmin);
                max = convertStrToDate(datestrmax);
                Log.e("dates" ,test.toString() +" " +min.toString() +" " +max.toString() +" " +test.compareTo(min)+" " +test.compareTo(max));
//
                if (min.compareTo(test) * test.compareTo(max) >= 0){
                    filteredContactList.add(model);
                }


            } else {
                Log.e("Person", "null");
            }

        }
        if (query.equals("")) {
            filteredContactList.add(0, null);
        }
        return filteredContactList;
    }

    private Date convertStrToDate(String query) {
        mDateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = mDateformat.parse(query);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return date;
    }

    public ArrayList<CineWhoopDetail> filterbyGenremethod(List<CineWhoopDetail> listFilter, String query) {
        query = query.toLowerCase();
        final ArrayList<CineWhoopDetail> filteredContactList = new ArrayList<>();

        for (CineWhoopDetail model : listFilter) {


            if (model != null) {
//                    Log.e("Person",model.getName());
                final String text = model.getCategory().toLowerCase();
                if (text.contains(query)) {
                    filteredContactList.add(model);
                }

            } else {
                Log.e("Person", "null");
            }

        }
        if (query.equals("")) {
            filteredContactList.add(0, null);
        }
        return filteredContactList;
    }
}
