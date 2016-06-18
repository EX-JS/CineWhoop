package exousiatech.cinewhoop;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import RecyclerAdapter.AdapterGenre;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CategoryDetails;
import RetrofitPackage.RetrofitUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by john on 12/30/2015.
 */
public class SelectGenre extends AppCompatActivity {
    RecyclerView list_of_genre;
    RecyclerView.LayoutManager layoutManager;
    AdapterGenre adapter;
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    ConnectionDetectorUtil connectionDetectorUtil;
    Call<CategoryDetails> categorydetails;
    Retrofit retrofit;
    MaterialProgressBar progressBar;
    FrameLayout frameProgess;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectgenre);
        activity = this;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Filter By Genre");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_genre = (RecyclerView) findViewById(R.id.list_of_genre);
        progressBar = (MaterialProgressBar) findViewById(R.id.indeterminate_progress_library);
        frameProgess = (FrameLayout) findViewById(R.id.frame_progess);
        list_of_genre.setLayoutManager(layoutManager);


        callretrofitcategory();
    }

    private void callretrofitcategory() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cinewhoopApiService = retrofitUtil.getRetrofit();
        categorydetails = cinewhoopApiService.categorylistInfo();
        categorydetails.enqueue(new Callback<CategoryDetails>() {
            @Override
            public void onResponse(Response<CategoryDetails> response) {
                frameProgess.setVisibility(View.GONE);
                adapter = new AdapterGenre(SelectGenre.this, response.body());
                list_of_genre.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                frameProgess.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(list_of_genre, "Some Error Occured Please try again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, FilterByClass.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil = new ConnectionDetectorUtil();
        IntentFilter intentFilter = new IntentFilter();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

