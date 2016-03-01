package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.ArrayList;

import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;

import CinewhoopUtil.filterClasstoFilter;
import Database.DatabaseHelperCinewhoop;
import RecyclerAdapter.AdapterMovieList;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CineWhoopDetail;
import RetrofitPackage.RetrofitUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.*;

public class MoviesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    RecyclerView list_of_movie;
    LayoutManager layoutManager;
    AdapterMovieList adapter;
    TextView toolbarTitle ,textNoResults;
    Utilclass typefacechnage;
    ArrayList<TextView> tv;
    ImageView filterBy;
    Call<List<CineWhoopDetail>> moviesDetails;
    MaterialProgressBar progressBar;
    ConnectionDetectorUtil connectionDetectorUtil;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelperCinewhoop acessDataBase;
    List<CineWhoopDetail> details;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    filterClasstoFilter filterthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acessDataBase = new DatabaseHelperCinewhoop(getApplicationContext());
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        filterthis = new filterClasstoFilter();
        typefacechnage = new Utilclass(getApplicationContext());
        tv = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        textNoResults = (TextView) findViewById(R.id.textNoResults);
        progressBar = (MaterialProgressBar) findViewById(R.id.indeterminate_progress_library);
        toolbarTitle.setText("MOVIES");
        tv.add(toolbarTitle);
        tv.add(textNoResults);
        typefacechnage.applycustomfont(tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filterBy = (ImageView) findViewById(R.id.filterBy);
        filterBy.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_movie = (RecyclerView) findViewById(R.id.list_of_movie);
        list_of_movie.setLayoutManager(layoutManager);
        executeRetroCall();




    }

    private void executeRetroCall() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cinewhoopApiService =  retrofitUtil.getRetrofit();
        moviesDetails = cinewhoopApiService.moviesInformation();
        moviesDetails.enqueue(new Callback<List<CineWhoopDetail>>() {
            @Override
            public void onResponse(Response<List<CineWhoopDetail>> response) {
                details = response.body();
                adapter = new AdapterMovieList(MoviesActivity.this, details);
                list_of_movie.setAdapter(adapter);
                progressBar.setVisibility(GONE);
                Log.e("size", response.body().size()+"");
                if (sharedPreferences.getBoolean("CinemaFilterExist",false)){
                    Log.e("no filter" , "yes");

                    PerformcinemaFilter();
                }else if(sharedPreferences.getBoolean("DateFilterExist",false)){
                    PerformdateFilter();
                }
                else if (sharedPreferences.getBoolean("GenreFilterExist",false)){
                    PerformgenreFilter();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(GONE);
                textNoResults.setVisibility(VISIBLE);
                Snackbar snackbar = Snackbar.make(list_of_movie , "Some error occured. Please try again", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterBy:
                Intent it = new Intent(this, FilterByClass.class);
                startActivity(it);
                finish();
                break;
        }
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
        editor.putBoolean("CinemaFilterExist", false);
        editor.putBoolean("DateFilterExist", false);
        editor.putBoolean("GenreFilterExist", false);
        editor.commit();
        acessDataBase.deleteTable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("CinemaFilterExist", false);
        editor.putBoolean("DateFilterExist", false);
        editor.putBoolean("GenreFilterExist", false);
        editor.commit();
        acessDataBase.deleteTable();
    }
    private void PerformcinemaFilter() {
        datafromDatabase = acessDataBase.selectData();
        for (int i =0;i<datafromDatabase.size();i++){
            Log.e("value" ,datafromDatabase.get(i));
            String arr[] =datafromDatabase.get(i).split(" ");
            String cinemaid = arr[0];
            Log.e("value", cinemaid);

        ArrayList<CineWhoopDetail> filterlist = filterthis.filter(details , cinemaid);
        adapter.animateTo(filterlist);
            list_of_movie.scrollToPosition(0);

        }
    }

    private void PerformdateFilter() {
        datafromDatabase = acessDataBase.selectData();
        for (int i =0;i<datafromDatabase.size();i++){
            Log.e("value" ,datafromDatabase.get(i));
            String arr[] =datafromDatabase.get(i).split(" ");
            String date = arr[0];
            Log.e("value", date);

            ArrayList<CineWhoopDetail> filterlist = filterthis.filterbydatemethod(details, date);
            adapter.animateTo(filterlist);
            list_of_movie.scrollToPosition(0);

        }
    }
    private void PerformgenreFilter() {
        datafromDatabase = acessDataBase.selectData();
        for (int i = 0; i < datafromDatabase.size(); i++) {
            Log.e("value", datafromDatabase.get(i));
            String arr[] = datafromDatabase.get(i).split(" ");
            String genre = arr[0];
            Log.e("value", genre);

            ArrayList<CineWhoopDetail> filterlist = filterthis.filterbyGenremethod(details, genre);
            adapter.animateTo(filterlist);
            list_of_movie.scrollToPosition(0);
        }
    }
}
