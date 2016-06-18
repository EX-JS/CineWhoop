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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import CinewhoopUtil.filterClasstoFilter;
import RecyclerAdapter.AdapterShowTimmings;
import RetrofitPackage.CineWhoopDetail;
import RetrofitPackage.Datum;
import RetrofitPackage.MovieTimmingDetails;

/**
 * Created by jagteshwar on 28-01-2016.
 */
public class ShowTimmingsClass extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView toolbarTitle ,noTimmings;
    ArrayList<TextView> tv;
    Utilclass typechange;
    CineWhoopDetail eachmovie;
    Datum timmingsDetail;
    MovieTimmingDetails movieTimmingDetails;
    RecyclerView list_of_showTimmings;
    RecyclerView.LayoutManager layoutManager;
    AdapterShowTimmings adapter;
    String cinemaName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button continueTimmings;
    String cinemaId;
    filterClasstoFilter filterClasstoFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_timmings_layout);
        filterClasstoFilter = new filterClasstoFilter();
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        continueTimmings = (Button) findViewById(R.id.continueTimmings);
        continueTimmings.setOnClickListener(this);
        noTimmings = (TextView)findViewById(R.id.noTimmings);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setSelected(true);

        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_showTimmings = (RecyclerView) findViewById(R.id.list_of_showTimmings);
        list_of_showTimmings.setLayoutManager(layoutManager);
        if (sharedPreferences.getBoolean("CinemaNameExist",false)){
            cinemaName = sharedPreferences.getString("CinemaName","");
            toolbarTitle.setText("Show Timings at " + cinemaName);
            eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_detail");
            movieTimmingDetails = (MovieTimmingDetails)getIntent().getSerializableExtra(ConfigClass.MovieTimmings);
            List<Datum> list = filterClasstoFilter.filterbyCinemaforTimmings(movieTimmingDetails.getData() , cinemaName);

            if (!list.isEmpty()) {
                cinemaId = list.get(0).getCinemaId();
                adapter = new AdapterShowTimmings(ShowTimmingsClass.this, eachmovie.getMovie_time().getCinema1(), eachmovie, list.get(0).getCinemaName(), list.get(0).getCinemaId(), list.get(0).getCinemaTime());
                list_of_showTimmings.setAdapter(adapter);
            }else {
                noTimmings.setVisibility(View.VISIBLE);
            }

        }else {
            cinemaName = getIntent().getStringExtra("cinema_name");
            toolbarTitle.setText("Show Timings at " + cinemaName);
            eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_Time_detail");
            timmingsDetail = (Datum)getIntent().getSerializableExtra(ConfigClass.TimmingsCinema);
            cinemaId = timmingsDetail.getCinemaId();
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema1(),eachmovie ,timmingsDetail.getCinemaName() ,timmingsDetail.getCinemaId() ,timmingsDetail.getCinemaTime());
            list_of_showTimmings.setAdapter(adapter);
        }
//        checkCinemaTimeToSend();

    }

    private void checkCinemaTimeToSend() {

     /*   if (cinemaName.equalsIgnoreCase("Hoyts Stafford")){
            cinemaId = "1";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema1(),eachmovie ,"Hoyts Stafford" ,"1" );
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Hoyts Redcliffe")){
            cinemaId = "2";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema2(),eachmovie , "Hoyts Redcliffe","2");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Hoyts Sunnybank")){
            cinemaId = "3";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema3(),eachmovie , "Hoyts SunnyBank","3");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Yatala Drive In")){
            cinemaId = "4";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this ,eachmovie.getMovie_time().getCinema4(),eachmovie ,"Yatala Drive In","4");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("Dendy portside")){
            cinemaId = "5";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema5(),eachmovie ,"Dendy portside","5");
            list_of_showTimmings.setAdapter(adapter);
        }else if (cinemaName.equalsIgnoreCase("New Farm Cinemas")){
            cinemaId = "6";
            adapter = new AdapterShowTimmings(ShowTimmingsClass.this , eachmovie.getMovie_time().getCinema6(),eachmovie,"New Farm Cinemas","6");
            list_of_showTimmings.setAdapter(adapter);
        }
*/
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

    @Override
    public void onClick(View v) {
        Intent it = new Intent(ShowTimmingsClass.this , TicketsSelectionClass.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("cinemaIDtoSend", cinemaId);
        it.putExtra("cinemaName" , cinemaName);
        it.putExtra("MovieId" , eachmovie.getMovieId() );
        it.putExtra("movieDetails" , eachmovie.getTitle());
        startActivity(it);
    }
}
