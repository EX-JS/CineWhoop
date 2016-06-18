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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import RecyclerAdapter.AdapterCinema;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CinemaDetail;
import RetrofitPackage.RetrofitUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 12/30/2015.
 */
public class SelectCinema extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView list_of_cinema;
    RecyclerView.LayoutManager layoutManager;
    AdapterCinema adapter;
    Toolbar toolbar;
    TextView  toolbarTitle, comingSoonText;
    ArrayList<TextView> tv;
    Utilclass typechange;
    Spinner selectByState;
    ArrayList<String> category_array;
    Call<List<CinemaDetail>> cinemadetails;
    MaterialProgressBar progressBar;
    ConnectionDetectorUtil connectionDetectorUtil;
    private  LinearLayout linearLayoutSelectCinema;
     Activity selectCinemaActivity;
    List<CinemaDetail> detailList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.selectcinema);
        selectCinemaActivity = this;
        selectByState = (Spinner) findViewById(R.id.selectbyState);
        category_array = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayoutSelectCinema = (LinearLayout)findViewById(R.id.linearLayoutSelectCinema);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        comingSoonText = (TextView)findViewById(R.id.comingSoonText);
        toolbarTitle.setText("Filter By Cinema");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        tv.add(comingSoonText);
        typechange.applycustomfont(tv);
        progressBar = (MaterialProgressBar) findViewById(R.id.indeterminate_progress_library);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_cinema = (RecyclerView) findViewById(R.id.list_of_cinema);
        list_of_cinema.setLayoutManager(layoutManager);
        list_of_cinema.setVisibility(View.GONE);


        category_array.add("QLD");
        category_array.add("ACT Coming Soon ");
        category_array.add("NSW Coming Soon ");
        category_array.add("VIC Coming Soon ");
        category_array.add("WA Coming Soon ");
        selectByState.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_text, category_array));
        selectByState.setOnItemSelectedListener(this);


        callretrofitdata();

    }

   public List<CinemaDetail> callretrofitdata() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass hookahApiService = retrofitUtil.getRetrofit();
        cinemadetails = hookahApiService.cinemalistInfo();
        cinemadetails.enqueue(new Callback<List<CinemaDetail>>() {
            @Override
            public void onResponse(Response<List<CinemaDetail>> response) {
                detailList = response.body();
                adapter = new AdapterCinema(SelectCinema.this,response.body(),selectCinemaActivity);
                list_of_cinema.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(list_of_cinema , "Some Error Occurred Please try again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        return detailList;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String arr[] = parent.getItemAtPosition(position).toString().split(" ");
        comingSoonText.setText("Coming Soon in  " + arr[0]);
        switch (position){
            case 0:
                list_of_cinema.setVisibility(View.VISIBLE);
                comingSoonText.setVisibility(View.GONE);
                break;
            case 1:

                list_of_cinema.setVisibility(View.GONE);
                comingSoonText.setVisibility(View.VISIBLE);
                break;
            case 2:

                comingSoonText.setVisibility(View.VISIBLE);
                list_of_cinema.setVisibility(View.GONE);
                break;
            case 3:
                comingSoonText.setVisibility(View.VISIBLE);
                list_of_cinema.setVisibility(View.GONE);
                break;
            case 4:
                comingSoonText.setVisibility(View.VISIBLE);
                list_of_cinema.setVisibility(View.GONE);
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_screen_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
