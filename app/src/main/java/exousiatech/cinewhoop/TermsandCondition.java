package exousiatech.cinewhoop;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;

/**
 * Created by jagteshwar on 18-02-2016.
 */
public class TermsandCondition extends AppCompatActivity{
    DrawerLayout mdrawerlayout;
    LinearLayout mainContent;
    int frag;
    NavigationDrawer navDrawer;
    Toolbar toolbar;
    TextView  toolbarTitle , term1 ,term2 , term3 , term2header , term3header;
    ArrayList<TextView> tv;
    Utilclass typechange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_conditions_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Terms and Conditions");
        typechange = new Utilclass(getApplicationContext());
        tv = new ArrayList<TextView>();
        tv.add(toolbarTitle);
        term1 = (TextView)findViewById(R.id.term1);
        tv.add(term1);
        term2 = (TextView)findViewById(R.id.term2);
        tv.add(term2);
        term3 = (TextView)findViewById(R.id.term3);
        tv.add(term3);
        term2header = (TextView)findViewById(R.id.term2Header);
        tv.add(term2header);
        term3header = (TextView)findViewById(R.id.term3Header);
        tv.add(term3header);

        typechange.applycustomfont(tv);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);
    }
}
