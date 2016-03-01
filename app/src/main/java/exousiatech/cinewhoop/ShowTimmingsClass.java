package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import RecyclerAdapter.AdapterShowTimmings;
import RetrofitPackage.CineWhoopDetail;

/**
 * Created by jagteshwar on 28-01-2016.
 */
public class ShowTimmingsClass extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    CineWhoopDetail eachmovie;
    RecyclerView list_of_showTimmings;
    RecyclerView.LayoutManager layoutManager;
    AdapterShowTimmings adapter;
    String cinemaName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_timmings_layout);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setSelected(true);

        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);
        if (sharedPreferences.getBoolean("CinemaNameExist",false)){
            cinemaName = sharedPreferences.getString("CinemaName","");
            toolbarTitle.setText("Show Timings at " + cinemaName);
            eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_detail");
        }else {
            cinemaName = getIntent().getStringExtra("cinema_name");
            toolbarTitle.setText("Show Timings at " + cinemaName);
            eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_Time_detail");
        }



        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_showTimmings = (RecyclerView) findViewById(R.id.list_of_showTimmings);
        list_of_showTimmings.setLayoutManager(layoutManager);
        checkCinemaTimeToSend();

    }

    private void checkCinemaTimeToSend() {

        if (cinemaName.equalsIgnoreCase("Hoyts Stafford")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema1(),eachmovie ,"Hoyts Stafford" ,"1" );
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Hoyts Redcliffe")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema2(),eachmovie , "Hoyts Redcliffe","2");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Hoyts Sunnybank")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema3(),eachmovie , "Hoyts SunnyBank","3");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Yatala Drive In")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this ,eachmovie.getMovie_time().getCinema4(),eachmovie ,"Yatala Drive In","4");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Dendy portside")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema5(),eachmovie ,"Dendy portside","5");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("New Farm Cinemas")){
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema6(),eachmovie,"New Farm Cinemas","6");
            list_of_showTimmings.setAdapter(adapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;

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
        getMenuInflater().inflate(R.menu.booking_screen_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("CinemaNameExist",false);
        editor.commit();
    }
}
