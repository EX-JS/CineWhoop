package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;

/**
 * Created by jagteshwar on 19-01-2016.
 */
public class FilterByClass extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView  toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    RelativeLayout filterBygenre , filterByDate , filterByCinema;
    TextView headerFilterGenre , headerFilterDate,headerFilterCinema ,selectGenre ,selectDate,selectCinema;
    Date mDate, mDate1;
    Calendar mCalendar, mCalendar1;
    DateFormat mDateFormat;
    ConnectionDetectorUtil connectionDetectorUtil;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent dateIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_list);

        acessDataBase = new DatabaseHelperCinewhoop(getApplicationContext());
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typechange = new Utilclass(getApplicationContext());
        tv = new ArrayList<>();
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        headerFilterGenre = (TextView)findViewById(R.id.header_filter_genre);
        headerFilterDate = (TextView)findViewById(R.id.header_filter_date);
        headerFilterCinema = (TextView)findViewById(R.id.header_filter_cinema);
        selectGenre = (TextView)findViewById(R.id.select_genre);
        selectDate = (TextView)findViewById(R.id.select_date);
        selectCinema = (TextView)findViewById(R.id.select_cinema);
        filterBygenre = (RelativeLayout)findViewById(R.id.filterBygenre);
        filterByDate = (RelativeLayout)findViewById(R.id.filterByDate);
        filterByCinema = (RelativeLayout)findViewById(R.id.filterByCinema);
        toolbarTitle.setText("Filter");
        tv.add(toolbarTitle);
        tv.add(headerFilterCinema);
        tv.add(headerFilterDate);
        tv.add(headerFilterGenre);
        tv.add(selectCinema);
        tv.add(selectDate);
        tv.add(selectGenre);
        typechange.applycustomfont(tv);
        filterByCinema.setOnClickListener(this);
        filterByDate.setOnClickListener(this);
        filterBygenre.setOnClickListener(this);

        mDate = new Date();
        mDate1 = new Date();
        mCalendar = Calendar.getInstance();
        mCalendar1 = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.filterByCinema :
                Intent it = new Intent(this, SelectCinema.class);
                startActivity(it);
                finish();
                break;
            case R.id.filterByDate :
                myCalender();
                break;
            case R.id.filterBygenre :
                Intent it2 = new Intent(this, SelectGenre.class);
                startActivity(it2);
                finish();
                break;
        }

    }
    public void myCalender() {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        try {
            mDate = (Date) mDateFormat.parse(mDate.toString());
            mDate1 = (Date) mDateFormat.parse(mDate1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDate.setTime(System.currentTimeMillis() - 1000);
        mCalendar.setTime(mDate);
        mCalendar1.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 12, mCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        datePickerDialog.setMinDate(mCalendar);
        datePickerDialog.setMaxDate(mCalendar1);
        datePickerDialog.show(getFragmentManager(), "mCalender");
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(year, monthOfYear, dayOfMonth);
                Toast.makeText(FilterByClass.this, "" + mDateFormat.format(mCalendar.getTime()), Toast.LENGTH_LONG).show();

                boolean flag =  acessDataBase.insertDataintoTable("Filterbydate", mDateFormat.format(mCalendar.getTime()));
                if (flag){

                    editor.putBoolean("DateFilterExist", true);
                    editor.commit();
//                        datafromDatabase = acessDataBase.selectData();
//                        for (int i =0;i<datafromDatabase.size();i++){
//                            Log.e("value" ,datafromDatabase.get(i));
//                        }
                    dateIntent = new Intent(getApplicationContext() , MoviesActivity.class);

                    dateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dateIntent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), " This filter cannot be applied now", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil=new ConnectionDetectorUtil();
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionDetectorUtil, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionDetectorUtil);
        connectionDetectorUtil.dismissDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.homeIcon:
                Intent intent2 = new Intent(this,HomeActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
