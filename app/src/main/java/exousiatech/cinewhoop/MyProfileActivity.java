package exousiatech.cinewhoop;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.CropSquareTransformation;
import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;
import RecyclerAdapter.AdapterGenre;
import RecyclerAdapter.Adapterorders;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.OrderDetails;
import RetrofitPackage.RetrofitUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 12/24/2015.
 */
public class MyProfileActivity extends AppCompatActivity  {
    DrawerLayout mdrawerlayout;
    LinearLayout mainContent;
    int frag;
    Toolbar toolbar;
    NavigationDrawer navDrawer;
    TextView  toolbarTitle , credit_points , noOrder;
    EditText nameText , phoneText;
    Utilclass typefaceChange;
    ArrayList<TextView> tv;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView list_of_orders;
    RecyclerView.LayoutManager layoutManager;
    Adapterorders adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        credit_points = (TextView)findViewById(R.id.credit_points);
        noOrder = (TextView)findViewById(R.id.no_order);
        typefaceChange = new Utilclass(getApplicationContext());
        mdrawerlayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);
        tv = new ArrayList<>();
        tv.add(toolbarTitle);
        nameText= (EditText)findViewById(R.id.name_edit_text);
        tv.add(nameText);
        tv.add(credit_points);
        tv.add(noOrder);

        phoneText= (EditText)findViewById(R.id.phone_edit_text);
        tv.add(phoneText);
        typefaceChange.applycustomfont(tv);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_orders = (RecyclerView) findViewById(R.id.list_of_orders);

        list_of_orders.setLayoutManager(layoutManager);


        updateProfileUi();
    }



    private void updateProfileUi() {
        Log.e("shared", "shard1");
        if (sharedPreferences.getBoolean("userProfileExist", false)){
            Log.e("shared" , "shard12");
            nameText.setText(sharedPreferences.getString("name","" ));
//            emailText.setText(sharedPreferences.getString("email", ""));
            phoneText.setText(sharedPreferences.getString("contact_phone","" ));
            callretrofitforOrderdetails(sharedPreferences.getString("email", ""));

        }else {
            noOrder.setVisibility(View.VISIBLE);
        }

    }

    public void callretrofitforOrderdetails(final String emailid ){


        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Call<OrderDetails> orderstatus =  cineApiService.orderinfo(emailid);

        orderstatus.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(final Response<OrderDetails> response) {
                if (response.body().getTotalCredit().get(0).getCredit().equalsIgnoreCase("")) {
                credit_points.setText("0 pts");
                }else {
                    credit_points.setText(response.body().getTotalCredit().get(0).getCredit()+ " Pts");
                }

                if (response.body().getOrderDatail().size()<0){
                noOrder.setVisibility(View.VISIBLE);
                }else {
                    adapter = new Adapterorders(MyProfileActivity.this, response.body().getOrderDatail());
                    list_of_orders.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callretrofitforOrderdetails(sharedPreferences.getString("email", ""));

            }
        });

    }
}
