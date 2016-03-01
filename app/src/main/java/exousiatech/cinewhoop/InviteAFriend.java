package exousiatech.cinewhoop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;

/**
 * Created by john on 12/24/2015.
 */
public class InviteAFriend extends AppCompatActivity {
    DrawerLayout mdrawerlayout;
    LinearLayout mainContent;
    int frag;
    Toolbar toolbar;
    NavigationDrawer navDrawer;
    TextView Invite ,inviteHeader , toolbarTitle ;
    Utilclass typefaceChange;
    ArrayList<TextView> tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typefaceChange = new Utilclass(getApplicationContext());
        setContentView(R.layout.refer_a_friend);
        tv = new ArrayList<>();
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        tv.add(toolbarTitle);
        Invite = (TextView)findViewById(R.id.invitebtn);
        inviteHeader = (TextView)findViewById(R.id.inviteHeader);
        tv.add(Invite);
        tv.add(inviteHeader);
        typefaceChange.applycustomfont(tv);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        mdrawerlayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);
        Invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Invite your friends to the exiting world of CinwWhoop! in just a Click.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}
