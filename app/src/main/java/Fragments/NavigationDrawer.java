package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.CropSquareTransformation;
import Database.DatabaseHelperCinewhoop;
import exousiatech.cinewhoop.AboutUsActivity;
import exousiatech.cinewhoop.CustomerSupportActivity;
import exousiatech.cinewhoop.HomeActivity;
import exousiatech.cinewhoop.LoginActivity;
import exousiatech.cinewhoop.MyProfileActivity;

import exousiatech.cinewhoop.InviteAFriend;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.TermsandCondition;

/**
 * Created by Exousia - Aamir on 23/12/2015.
 */
public class NavigationDrawer extends Fragment implements View.OnClickListener {

    ListView drawerlistItem;
    LinearLayout loginLayout;
    String mtitle[];
    TypedArray mimage;
    Typeface typeFace;
    DrawerLayout mdrawerLayout;
    Toolbar toolbar;
    LinearLayout mainContent;
    ActionBarDrawerToggle mdrawerToggle;
    View left_drawer_frag;
    AppCompatActivity activity;
    Intent intent;
    TextView username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView profilePicNav;
    DatabaseHelperCinewhoop acessDatabase;

    public NavigationDrawer(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        acessDatabase = new DatabaseHelperCinewhoop(getActivity());
        View v = inflater.inflate(R.layout.drawerlayout , container , false);
        typeFace=Typeface.createFromAsset(getActivity().getAssets(), "fonts/cinewhoop.ttf");
        drawerlistItem  = (ListView)v.findViewById(R.id.nav_left_drawer_list);
        View footerView =  ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_nav_drawer, null, false);
        drawerlistItem.addFooterView(footerView);
        mtitle = getResources().getStringArray(R.array.mdrawerListArray);
        mimage  =getResources().obtainTypedArray(R.array.mdrawerListArrayimg);
        username = (TextView)v.findViewById(R.id.user_name);
        loginLayout = (LinearLayout)v.findViewById(R.id.loginLayout);
        loginLayout.setOnClickListener(this);
        profilePicNav = (ImageView)v.findViewById(R.id.profile_pic_nav);
        username.setTypeface(typeFace);
        drawerlistItem.setAdapter(new NavAdapter());
        drawerlistItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                mdrawerLayout.closeDrawer(left_drawer_frag);
                diplayScreen(position);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNavUi();
        updateProfileImage();

    }

    private void updateNavUi() {
        if (sharedPreferences.getBoolean("userProfileExist", false)){
            username.setText(sharedPreferences.getString("name", ""));
            loginLayout.setVisibility(View.GONE);
        }else {
            username.setText("Guest");
            loginLayout.setVisibility(View.VISIBLE);
        }
    }
    private void updateProfileImage() {
        if (sharedPreferences.getBoolean("ImageUriExist", false)) {
            Uri uri = Uri.parse(sharedPreferences.getString("ImageUri", " "));
            Picasso.with(getActivity())
                    .load(uri)
                    .transform(new CropSquareTransformation())
                    .into(profilePicNav);
        }
    }
    private void diplayScreen(int position) {

        switch(position)
        {
            case 0:
                if(!(activity instanceof HomeActivity))
                {
                    intent=new Intent(activity,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }

                break;
            case 1:
                if(!(activity instanceof MyProfileActivity))
                {
                    if (sharedPreferences.getBoolean("loginDone",false)){
                        intent=new Intent(activity,MyProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }else {
                        intent=new Intent(activity,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }

                }
                else  if(activity instanceof MyProfileActivity&&!(activity instanceof HomeActivity))
                {
                    return;
                }
                break;
//
            case 2:
                if(!(activity instanceof CustomerSupportActivity))
                {
                    intent=new Intent(activity,CustomerSupportActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                else  if(activity instanceof CustomerSupportActivity&&!(activity instanceof HomeActivity))
                {
                    return;
                }

                break;
            case 3:
                if(!(activity instanceof InviteAFriend))
                {
                    intent=new Intent(activity,InviteAFriend.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                else  if(activity instanceof InviteAFriend&&!(activity instanceof HomeActivity))
                {
                  return;
                }
                break;
            case 4:
                if(!(activity instanceof AboutUsActivity))
                {
                    intent=new Intent(activity,AboutUsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                else  if(activity instanceof AboutUsActivity&&!(activity instanceof HomeActivity))
                {
                    return;
                }
                break;
            case 5:
                if(!(activity instanceof TermsandCondition))
                {
                    intent=new Intent(activity,TermsandCondition.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                else  if(activity instanceof TermsandCondition&&!(activity instanceof HomeActivity))
                {
                    return;
                }
                break;
            case 6:
                if (sharedPreferences.getBoolean("userProfileExist", false)){
                    Toast.makeText(getContext(), "You have been Logged out Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "You have been already Logged out", Toast.LENGTH_SHORT).show();

                }

                editor.clear();
                editor.commit();
                if (FacebookSdk.isInitialized()){
                    LoginManager.getInstance().logOut();
                }
                acessDatabase.deleteTableOffer();
                acessDatabase.deleteTable();
                updateNavUi();
                updateProfileImage();
                if(!(activity instanceof HomeActivity))
                {
                    intent=new Intent(activity,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
                 break;
        }
        if(!(activity instanceof HomeActivity))
        {
            activity.finish();
        }
    }

    public void actionbarToogler(final AppCompatActivity activity,LinearLayout mainview,DrawerLayout mdrawer,Toolbar toolbar,int left_drawer_id)
    {
        this.left_drawer_frag=activity.findViewById(left_drawer_id);
        this.mdrawerLayout=mdrawer;
        this.mainContent=mainview;
        this.toolbar=toolbar;
        this.activity=activity;
        mdrawerToggle=new ActionBarDrawerToggle(activity,mdrawer,toolbar, R.string.open_drawer,R.string.close_drawer){


            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub

                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(drawerView);
                activity.invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // TODO Auto-generated method stub
                super.onDrawerSlide(drawerView, slideOffset);
                mainContent.setTranslationX(drawerView.getWidth()*slideOffset);
            }
        };

        mdrawer.setDrawerListener(mdrawerToggle);
        mdrawer.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mdrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginLayout:
                mdrawerLayout.closeDrawers();
                Intent it = new Intent(getContext() , LoginActivity.class );
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                break;
        }
    }


    private class NavAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mtitle.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View leftListViewStyle=getActivity().getLayoutInflater().inflate(R.layout.left_drawer_list_style,null);
            ImageView image=(ImageView) leftListViewStyle.findViewById(R.id.image_view_left_drawer_list);
            TextView textView=(TextView) leftListViewStyle.findViewById(R.id.text_view_left_drawer_list);
            textView.setTypeface(typeFace);
            image.setImageResource(mimage.getResourceId(position,-1));
            textView.setText(mtitle[position]);
            return leftListViewStyle;
        }
    }
}
