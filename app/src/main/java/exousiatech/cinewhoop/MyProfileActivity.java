package exousiatech.cinewhoop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.CropSquareTransformation;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import Fragments.NavigationDrawer;
import RecyclerAdapter.AdapterGenre;
import RecyclerAdapter.Adapterorders;
import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.OrderDatail;
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
    Dialog pDialog;
    SimpleDateFormat mDateformat;
    DatabaseHelperCinewhoop accessDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessDatabase = new DatabaseHelperCinewhoop(MyProfileActivity.this);
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
        pDialog = new Dialog(MyProfileActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);

        updateProfileUi();
    }



    private void updateProfileUi() {
        if (sharedPreferences.getBoolean("userProfileExist", false)){
            nameText.setText(sharedPreferences.getString("name","" ));
            phoneText.setText(sharedPreferences.getString("contact_phone","" ));
            callretrofitforOrderdetails(sharedPreferences.getString("email", ""));

        }else {
            noOrder.setVisibility(View.VISIBLE);
        }

    }

    public void callretrofitforOrderdetails(final String emailid ){
        pDialog.show();

        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass cineApiService = retrofitUtil.getRetrofit();
        Log.e("auth" ,ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,"")+"  "+sharedPreferences.getString(ConfigClass.SessionTime,"") );
        Call<OrderDetails> orderstatus =  cineApiService.orderinfo(ConfigClass.UltaExousia+sharedPreferences.getString(ConfigClass.Token,""),sharedPreferences.getString(ConfigClass.SessionTime,""),emailid);

        orderstatus.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(final Response<OrderDetails> response) {

                if (pDialog != null) {
                    pDialog.dismiss();
                }
                if (response.body().getStatus().equals("Access authorized")) {
                    if (response.body().getTotalCredit().getCredit().equalsIgnoreCase("")) {
                        credit_points.setText("0 pts");
                    } else {
                        credit_points.setText(response.body().getTotalCredit().getCredit() + " Pts");
                    }

                    if (response.body().getOrderDatail().size() <= 0) {
                        noOrder.setVisibility(View.VISIBLE);
                    } else {
                        Collections.sort(response.body().getOrderDatail(), new Comparator<OrderDatail>() {
                            @Override
                            public int compare(OrderDatail lhs, OrderDatail rhs) {
                                if (lhs != null || rhs != null) {
                                    Date d1 = new Date();
                                    Date d2 = new Date();
                                    d1 = convertStrToDate(lhs.getOrder_time());
                                    d2 = convertStrToDate(rhs.getOrder_time());

                                    return d2.compareTo(d1);
                                } else {
                                    return 0;
                                }

                            }
                        });
                        adapter = new Adapterorders(MyProfileActivity.this, response.body().getOrderDatail());
                        list_of_orders.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(MyProfileActivity.this, "Please login again , You have been logged in with some other device", Toast.LENGTH_LONG).show();
                    editor.clear();
                    editor.commit();
                    if (FacebookSdk.isInitialized()){
                        LoginManager.getInstance().logOut();
                    }
                    accessDatabase.deleteTableOffer();
                    accessDatabase.deleteTable();
                    Intent intent=new Intent(MyProfileActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                noOrder.setVisibility(View.VISIBLE);

            }
        });

    }

    private Date convertStrToDate(String query) {
        mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = mDateformat.parse(query);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
