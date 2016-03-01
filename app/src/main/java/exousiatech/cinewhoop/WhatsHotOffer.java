package exousiatech.cinewhoop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RecyclerAdapter.AdapterWhatsHot;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.OfferDetail;
import RetrofitPackage.RetrofitUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 18-01-2016.
 */
public class WhatsHotOffer extends AppCompatActivity implements View.OnClickListener {
    RecyclerView list_of_offer;
    RecyclerView.LayoutManager layoutManager;
    AdapterWhatsHot adapter;
    Toolbar toolbar;
    TextView toolbarTitle, textNoResults ,addMovie ,itemsincart;
    LinearLayout  skip;
    ArrayList<TextView> tv;
    Utilclass typechange;
    ConnectionDetectorUtil connectionDetectorUtil;
    Call<List<OfferDetail>> cinemadetails;
    MaterialProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Activity activity;
    DatabaseHelperCinewhoop accesDatabase;
    int movieCount =0 ,offerCount = 0 , totalcount = 0;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_layout);
        activity = this;
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accesDatabase = new DatabaseHelperCinewhoop(WhatsHotOffer.this);
        progressBar = (MaterialProgressBar)findViewById(R.id.indeterminate_progress_library);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textNoResults = (TextView)findViewById(R.id.textNoResults);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        itemsincart = (TextView)findViewById(R.id.itemsincart);
        skip = (LinearLayout)findViewById(R.id.skip);
        addMovie = (TextView)findViewById(R.id.addMovie);
        toolbarTitle.setText("OFFERS");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        tv.add(textNoResults);
        tv.add(itemsincart);

        tv.add(addMovie);
        typechange.applycustomfont(tv);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_offer = (RecyclerView) findViewById(R.id.list_of_offer);
        list_of_offer.setLayoutManager(layoutManager);

        skip.setOnClickListener(this);
        addMovie.setOnClickListener(this);
        callretrofitdata();

    }

    private void updateUiForcart() {
        if (sharedPreferences.getBoolean("MovieExist", false)){
            movieCount = 1;
            settotalCount();
        }else {
            movieCount = 0;
            settotalCount();
        }
        if(accesDatabase.offerExistornot()){
            datafromDatabase = accesDatabase.selectDataOffer();
           offerCount= datafromDatabase.size();
            settotalCount();
        }else {
            offerCount = 0;
            settotalCount();
        }
    }

    private void settotalCount() {
totalcount = movieCount+offerCount;
        itemsincart.setText(totalcount+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUiForcart();
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

    public void callretrofitdata() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        cinemadetails = cineApiService.offerGetDetails();
        cinemadetails.enqueue(new Callback<List<OfferDetail>>() {
            @Override
            public void onResponse(Response<List<OfferDetail>> response) {
                progressBar.setVisibility(View.GONE);
                adapter = new AdapterWhatsHot(WhatsHotOffer.this, response.body() , activity);
                list_of_offer.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                textNoResults.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        editor.putBoolean("skipExist", false);
//        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip:
                if (sharedPreferences.getBoolean("loginDone",false)){
                    Intent it = new Intent(this, PayAndCheckoutClass.class);
                    startActivity(it);
                }else {
                    Intent it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }


                break;
            case R.id.addMovie:
                Intent itmovie = new Intent(this , MoviesActivity.class);
                startActivity(itmovie);
                editor.putBoolean("offerExist" , true);
                editor.commit();
                finish();
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
