package exousiatech.cinewhoop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import RecyclerAdapter.AdapterSelectedMovieCinema;
import RetrofitPackage.CineWhoopDetail;

/**
 * Created by jagteshwar on 26-01-2016.
 */
public class CinemaWithSelectedMovie extends AppCompatActivity{
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    CineWhoopDetail eachmovie;

    RecyclerView list_of_selectedMovieCinema;
    RecyclerView.LayoutManager layoutManager;
    AdapterSelectedMovieCinema adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_selected_screen);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Select Cinema");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);
        eachmovie = (CineWhoopDetail) getIntent().getSerializableExtra("movie_detail");




        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_selectedMovieCinema = (RecyclerView) findViewById(R.id.list_of_selectedMovieCinema);
        list_of_selectedMovieCinema.setLayoutManager(layoutManager);
        adapter = new AdapterSelectedMovieCinema(CinemaWithSelectedMovie.this , eachmovie);
        list_of_selectedMovieCinema.setAdapter(adapter);
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

}
