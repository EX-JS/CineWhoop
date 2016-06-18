package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import DialogPackage.DeleteCartPopUp;
import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;

/**
 * Created by jagteshwar on 18-01-2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mdrawerlayout;
    NavigationDrawer navDrawer;
    int frag;
    LinearLayout mainContent , moviesLayout , cinemasLayout , hotLayout ,comingSoonLayout;
    Toolbar toolbar;
    TextView movieHeader , cinemasHeader,  hotHeader;
    Utilclass typefaceChnage;
    ArrayList<TextView> tv;
    Intent it;
    ConnectionDetectorUtil connectionDetectorUtil;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DeleteCartPopUp deleteCartPopUp;
//    Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        deleteCartPopUp = new DeleteCartPopUp(this );
        toolbar = (Toolbar) findViewById(R.id.tool_bar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);

        movieHeader = (TextView)findViewById(R.id.movieHeader);
        cinemasHeader= (TextView)findViewById(R.id.cinemasHeader);
        hotHeader= (TextView)findViewById(R.id.hotHeader);
        typefaceChnage = new Utilclass(getApplicationContext());
        tv = new ArrayList<>();
        tv.add(movieHeader);
        tv.add(cinemasHeader);
        tv.add(hotHeader);
        typefaceChnage.applycustomfont(tv);

        moviesLayout = (LinearLayout)findViewById(R.id.movies_layout);
        cinemasLayout= (LinearLayout)findViewById(R.id.cinemas_layout);
        hotLayout = (LinearLayout)findViewById(R.id.hot_layout);
        comingSoonLayout = (LinearLayout)findViewById(R.id.comingSoonLayout);

        moviesLayout.setOnClickListener(this);
        cinemasLayout.setOnClickListener(this);
        hotLayout.setOnClickListener(this);
        comingSoonLayout.setOnClickListener(this);
            }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.movies_layout:
                 it = new Intent(this, MoviesActivity.class);
                startActivity(it);
                break;
            case R.id.cinemas_layout:
                it = new Intent(this, SelectCinema.class);
                startActivity(it);

                break;
            case R.id.hot_layout:
                it = new Intent(this, WhatsHotOffer.class);
                startActivity(it);
                break;
            case R.id.comingSoonLayout:
                it = new Intent(this, ComingSoonMovies.class);
                startActivity(it);
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
    }

    @Override
    public void onBackPressed() {

        if (sharedPreferences.getBoolean("MovieExist",false)||sharedPreferences.getBoolean("offerExist",false)){
            deleteCartPopUp.showDialog();
        }else {
            super.onBackPressed();
        }

    }
}
