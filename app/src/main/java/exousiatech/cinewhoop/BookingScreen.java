package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;
import Fragments.ViewPagerBooking;
import RetrofitPackage.CineWhoopDetail;

/**
 * Created by john on 12/30/2015.
 */
public class BookingScreen extends AppCompatActivity implements View.OnClickListener {
    TextView movieName , movieType, movieTime , releasedate, releasedateText, duration , durationText, category, categoryText, director
            , directorText, actor, actorText , ratingBottom , ratingBottomText, theplot, theplotText  , toolbarTitle , ratingTop;
    Button buyTicket , watchTrailor;
    Utilclass tyfaceChange;
    ArrayList<TextView> tv;
    Toolbar toolbar;
    CineWhoopDetail eachmovie;
    ViewPager movieBackgrounfBooking;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectionDetectorUtil connectionDetectorUtil;
    android.support.v4.app.FragmentManager fm;
    CirclePageIndicator circlePageIndicator;
    String youtubelink , imdb_url;
    LinearLayout rating_layout_bookscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_detail");
//        eachmovie.getMovie_time()
        tyfaceChange = new Utilclass(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText(eachmovie.getTitle());
        toolbarTitle.setSelected(true);
        tv = new ArrayList<>();
        tv.add(toolbarTitle);
        movieBackgrounfBooking = (ViewPager)findViewById(R.id.ViewPager_movieBack);
       movieName  = (TextView)findViewById(R.id.movieName);
        tv.add(movieName);
       movieType  = (TextView)findViewById(R.id.movieType);
        tv.add(movieType);
        movieTime = (TextView)findViewById(R.id.movieTime);
        tv.add(movieTime);
        releasedate = (TextView)findViewById(R.id.releasedate);
        tv.add(releasedate);
        releasedateText = (TextView)findViewById(R.id.releasedateText);
        tv.add(releasedateText);
        duration= (TextView)findViewById(R.id.duration);
        tv.add(duration);
        durationText = (TextView)findViewById(R.id.durationText);
        tv.add(durationText);
        category = (TextView)findViewById(R.id.category);
        tv.add(category);
        categoryText = (TextView)findViewById(R.id.categoryText);
        tv.add(categoryText);
        director = (TextView)findViewById(R.id.director);
        tv.add(director);
        directorText = (TextView)findViewById(R.id.directorText);
        tv.add(directorText);
        actor = (TextView)findViewById(R.id.actor);
        tv.add(actor);
        actorText= (TextView)findViewById(R.id.actorText);
        tv.add(actorText);
        ratingBottom = (TextView)findViewById(R.id.ratingbottom);
        tv.add(ratingBottom);
        ratingBottomText= (TextView)findViewById(R.id.ratingbottomText);
        tv.add(ratingBottomText);
        theplot = (TextView)findViewById(R.id.theplot);
        tv.add(theplot);
        theplotText = (TextView)findViewById(R.id.theplotText);
        tv.add(theplotText);
        buyTicket = (Button)findViewById(R.id.buyticket);
        tv.add(buyTicket);
        watchTrailor = (Button)findViewById(R.id.watchTrailor);
        tv.add(watchTrailor);
        ratingTop = (TextView)findViewById(R.id.ratingtop);
        tv.add(ratingTop);
        tyfaceChange.applycustomfont(tv);
        rating_layout_bookscreen = (LinearLayout)findViewById(R.id.rating_layout_bookscreen);

        rating_layout_bookscreen.setOnClickListener(this);
        buyTicket.setOnClickListener(this);
        watchTrailor.setOnClickListener(this);

        movieName.setText(eachmovie.getTitle());
        movieType.setText(eachmovie.getCategory());
        movieTime.setText(convertmintohours(eachmovie.getMovieLenght()));
        releasedateText.setText(eachmovie.getReleaseDate());
        durationText.setText(convertmintohours(eachmovie.getMovieLenght()));
        categoryText.setText(eachmovie.getCategory());
        directorText.setText(eachmovie.getDirector());
        actorText.setText(eachmovie.getActor());
        ratingBottomText.setText(eachmovie.getRating());
        theplotText.setText(eachmovie.getDescription());
        ratingTop.setText(eachmovie.getRating());
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        fm = getSupportFragmentManager();
        movieBackgrounfBooking.setAdapter(new ViewPagerBooking(fm , eachmovie.getFeatured_image()));
        circlePageIndicator.setViewPager(movieBackgrounfBooking);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MoviesActivity.class);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buyticket:
                if (sharedPreferences.getBoolean("loginDone", false)){
                    if (sharedPreferences.getBoolean("CinemaNameExist",false)){
                        Intent it = new Intent(this, ShowTimmingsClass.class);
                        it.putExtra("movie_detail", eachmovie);
                        startActivity(it);
                    }else {
                        Intent it = new Intent(this, CinemaWithSelectedMovie.class);
                        it.putExtra("movie_detail", eachmovie);
                        startActivity(it);
                    }
                }else {
                    Intent it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }
                break;
            case R.id.watchTrailor:
                 try {

                     String arr[] = eachmovie.getYoutube_url().split("=");

                     if (arr.length>0) {
                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + arr[1]));
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         startActivity(intent);
                     }

                }catch(Exception e) {

                    // youtube is not installed.Will be opened in other available apps
                     youtubelink = eachmovie.getYoutube_url();
                     if (!youtubelink.startsWith("http://") && !youtubelink.startsWith("https://")) {
                         youtubelink = "http://" + youtubelink;
                         Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubelink));
                         startActivity(browserIntent);
                     }else {
                         Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubelink));
                         startActivity(browserIntent);
                     }
                }
                break;
            case R.id.rating_layout_bookscreen:
                imdb_url = eachmovie.getImdb_url();
                if (!imdb_url.startsWith("http://") && !imdb_url.startsWith("https://")) {
                    imdb_url = "http://" + imdb_url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdb_url));
                    startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdb_url));
                    startActivity(browserIntent);
                }
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
    public String  convertmintohours(String min){
        String startTime = "00:00";
        int minutes = Integer.parseInt(min);
        int h = minutes / 60 + Integer.valueOf(startTime.substring(0,1));
        int m = minutes % 60 + Integer.valueOf(startTime.substring(3,4));
        String newtime = h+" hr "+m +" min";
        return newtime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_screen_menu,menu);
        return true;
    }
}
