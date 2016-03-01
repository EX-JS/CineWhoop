package exousiatech.cinewhoop;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;
import exousiatech.cinewhoop.R;

/**
 * Created by john on 12/24/2015.
 */
public class AboutUsActivity extends AppCompatActivity {
    DrawerLayout mdrawerlayout;
    LinearLayout mainContent;
    int frag;
    NavigationDrawer navDrawer;
    Toolbar toolbar;
    TextView header , toolbarTitle  ,backabout, downabout ,rightabout;
    ArrayList<TextView> tv;
    Utilclass typechange;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/arrowfont.ttf");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        backabout = (TextView)findViewById(R.id.backabout);
        downabout = (TextView)findViewById(R.id.downback);
        rightabout = (TextView)findViewById(R.id.rightback);
        toolbarTitle.setText("About Us");
        typechange = new Utilclass(getApplicationContext());
         header = (TextView)findViewById(R.id.aboutusheader);
        tv = new ArrayList<TextView>();
        tv.add(header);
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);
        backabout.setTypeface(typeface);
        downabout.setTypeface(typeface);
        rightabout.setTypeface(typeface);
    }
}
