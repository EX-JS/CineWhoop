package CinewhoopUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RetrofitPackage.CineWhoopDetail;
import RetrofitPackage.Datum;
import RetrofitPackage.MovieTimmingDetails;

/**
 * Created by jagteshwar on 30-01-2016.
 */
public class filterClasstoFilter {

    SimpleDateFormat mDateformat;
    Date min, max, test;

    public ArrayList<CineWhoopDetail> filter(List<CineWhoopDetail> listFilter, String query) {
        query = query.toLowerCase();
        final ArrayList<CineWhoopDetail> filteredContactList = new ArrayList<>();

        for (CineWhoopDetail model : listFilter) {


            if (model != null) {
                final String text = model.getCinema_id().toLowerCase();
                if (text.contains(query)) {
                    filteredContactList.add(model);
                }

            } else {
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

                final String datestrmin = model.getReleaseDate().toLowerCase();
                final String datestrmax = model.getMovie_expiry_date().toLowerCase();
                test = convertStrToDate(query);
                min = convertStrToDate(datestrmin);
                max = convertStrToDate(datestrmax);
//
                if (min.compareTo(test) * test.compareTo(max) >= 0) {
                    filteredContactList.add(model);
                }


            } else {
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
                final String text = model.getCategory().toLowerCase();
                if (text.contains(query)) {
                    filteredContactList.add(model);
                }

            } else {
            }

        }
        if (query.equals("")) {
            filteredContactList.add(0, null);
        }
        return filteredContactList;
    }

    public List<Datum> filterbyCinemaforTimmings(List<Datum> listFilter, String query) {
        query = query.toLowerCase();
        final List<Datum> filteredContactList = new ArrayList<>();

        for (Datum model : listFilter) {


            if (model != null) {
                final String text = model.getCinemaName().toLowerCase();
                if (text.equalsIgnoreCase(query)) {
                    filteredContactList.add(model);
                }

            } else {
            }

        }
        if (query.equals("")) {
            filteredContactList.add(0, null);
        }
        return filteredContactList;
    }
}
