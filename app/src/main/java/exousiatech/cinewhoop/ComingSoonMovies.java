package exousiatech.cinewhoop;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import RecyclerAdapter.AdapterComingSoon;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.RetrofitUtil;
import RetrofitPackage.SoonDetail;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 10-02-2016.
 */
public class ComingSoonMovies extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle, textNoResults;
    ArrayList<TextView> tv;
    Utilclass typechange;

    RecyclerView list_of_comingSoonMovies;
    RecyclerView.LayoutManager layoutManager;
    AdapterComingSoon adapter;

    ConnectionDetectorUtil connectionDetectorUtil;
    Call<List<SoonDetail>> cominfSoonDetails;
    MaterialProgressBar progressBar;
    SimpleDateFormat mDateformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coming_soon_layout);
        progressBar = (MaterialProgressBar)findViewById(R.id.indeterminate_progress_library);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        textNoResults = (TextView)findViewById(R.id.textNoResults);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Coming Soon");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_comingSoonMovies = (RecyclerView) findViewById(R.id.list_of_comingSoonMovies);
        list_of_comingSoonMovies.setLayoutManager(layoutManager);

        callRetroForCommingSoon();
    }

    private void callRetroForCommingSoon() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        cominfSoonDetails = cineApiService.comingSoonDetails();
        cominfSoonDetails.enqueue(new Callback<List<SoonDetail>>() {
            @Override
            public void onResponse(Response<List<SoonDetail>> response) {
                progressBar.setVisibility(View.GONE);
                adapter = new AdapterComingSoon(ComingSoonMovies.this, response.body());
                list_of_comingSoonMovies.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                textNoResults.setVisibility(View.VISIBLE);
            }
        });
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
        getMenuInflater().inflate(R.menu.booking_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
}
