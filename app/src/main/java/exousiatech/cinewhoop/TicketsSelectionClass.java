package exousiatech.cinewhoop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RecyclerAdapter.AdapterCinema;

import RetrofitPackage.ApiServicesClass;
import RetrofitPackage.CinemaDetail;
import RetrofitPackage.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jagteshwar on 29-01-2016.
 */
public class TicketsSelectionClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Toolbar toolbar;
    TextView toolbarTitle, headerAdultTickets , textTotalHeaderAdult ,amountTotalAdultTicket, headerChildTickets ,textTotalHeaderChild ,amountTotalChildTicket ,totalPriceHeader,textTotalPrise ,fixedRateAdultTickets  ,fixedRateChildTicket;
    Button letsGobtn;
    ArrayList<TextView> tv;
    Utilclass typechange;
    Spinner noOfAdultTickets , noOfChildTickets;
    String category_array[] , cinemaname, statusTicket , movieNametosend , movieIdtosend , cinemaidtosend;
    int countnoofAdults= 0 ,countnoofChilds = 0;
    double amountofAdultstickets , amountofchildtickets ,finalTotalAmount , priceTicketAdult , priceTicketChild;
    Call<List<CinemaDetail>> cinemadetails;
    CardView childCard, adultCard;
    CinemaDetail cinemaValObj;
    Dialog pDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
//    CineWhoopDetail movieDetails;
    DatabaseHelperCinewhoop accessdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_selection_layout);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessdatabase  = new DatabaseHelperCinewhoop(this);
        cinemaname = getIntent().getStringExtra("cinemaName");
        movieNametosend = getIntent().getStringExtra("movieDetails");
        movieIdtosend = getIntent().getStringExtra("MovieId");
        cinemaidtosend = getIntent().getStringExtra("cinemaIDtoSend");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Select Tickets");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        childCard= (CardView)findViewById(R.id.childCard);
        adultCard= (CardView)findViewById(R.id.adultCard);
        headerAdultTickets= (TextView)findViewById(R.id.headerAdultTickets);
        tv.add(headerAdultTickets);
        textTotalHeaderAdult = (TextView)findViewById(R.id.textTotalHeaderAdult);
        tv.add(textTotalHeaderAdult);
        amountTotalAdultTicket= (TextView)findViewById(R.id.amountTotalAdultTicket);
        tv.add(amountTotalAdultTicket);
        headerChildTickets = (TextView)findViewById(R.id.headerChildTickets);
        tv.add(headerChildTickets);
        textTotalHeaderChild = (TextView)findViewById(R.id.textTotalHeaderChild);
        tv.add(textTotalHeaderChild);
        amountTotalChildTicket = (TextView)findViewById(R.id.amountTotalChildTicket);
        tv.add(amountTotalChildTicket);
        totalPriceHeader= (TextView)findViewById(R.id.totalPriceHeader);
        tv.add(totalPriceHeader);
        textTotalPrise= (TextView)findViewById(R.id.textTotalPrise);
        tv.add(textTotalPrise);
        fixedRateAdultTickets= (TextView)findViewById(R.id.fixedRateAdultTickets);
        tv.add(fixedRateAdultTickets);
        fixedRateChildTicket= (TextView)findViewById(R.id.fixedRateChildTicket);
        tv.add(fixedRateChildTicket);
        typechange.applycustomfont(tv);

        category_array = getResources().getStringArray(R.array.noOfSeats);
        noOfAdultTickets = (Spinner)findViewById(R.id.noOfAdultTickets);
        noOfChildTickets = (Spinner)findViewById(R.id.noOfChildTickets);
        noOfAdultTickets.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_text, category_array));
        noOfAdultTickets.setOnItemSelectedListener(this);

        noOfChildTickets.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_text, category_array));
        noOfChildTickets.setOnItemSelectedListener(this);

        pDialog = new Dialog(TicketsSelectionClass.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
        pDialog.show();
        callretrofitdata();

        letsGobtn = (Button)findViewById(R.id.letsGoBtn);
        letsGobtn.setOnClickListener(this);
    }

    public void callretrofitdata() {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        ApiServicesClass hookahApiService = retrofitUtil.getRetrofit();
        cinemadetails = hookahApiService.cinemalistInfo();
        cinemadetails.enqueue(new Callback<List<CinemaDetail>>() {
            @Override
            public void onResponse(Response<List<CinemaDetail>> response) {
                for (int i = 0;i<response.body().size();i++){
                if (cinemaname.equalsIgnoreCase(response.body().get(i).getCinemaName())){
                    statusTicket=  response.body().get(i).getTicket_status();
                    if (statusTicket.equalsIgnoreCase("0")){
                        cinemaValObj = response.body().get(i);
                        priceTicketAdult = Double.parseDouble(cinemaValObj.getAdult_ticket_price());
                        priceTicketChild = Double.parseDouble(cinemaValObj.getChild_ticket_price());
                        setUiWithChild(response.body().get(i));
                    }else if (statusTicket.equalsIgnoreCase("1")){
                        cinemaValObj = response.body().get(i);
                        priceTicketAdult = Double.parseDouble(cinemaValObj.getAdult_ticket_price());
                        priceTicketChild = Double.parseDouble(cinemaValObj.getChild_ticket_price());
                        setUiWithoutChild(response.body().get(i));

                    }

                }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                pDialog.dismiss();
                Snackbar snackbar = Snackbar.make(letsGobtn , "NO Rates Available" ,Snackbar.LENGTH_LONG );
                snackbar.show();
                backgo();

            }
        });

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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.noOfAdultTickets:
               countnoofAdults = Integer.parseInt(parent.getItemAtPosition(position).toString());

                CalculateTotalPriceAmont();
                break;
            case R.id.noOfChildTickets:
                countnoofChilds = Integer.parseInt( parent.getItemAtPosition(position).toString());

                CalculateTotalPriceAmont();
                break;
        }
    }

    private void CalculateTotalPriceAmont() {
         if (countnoofAdults>0){

             amountofAdultstickets = countnoofAdults*priceTicketAdult;
             amountTotalAdultTicket.setText(String.format("$  %.2f" , amountofAdultstickets) );
             setTotalAmount();
         }else {
             amountofAdultstickets = 0;
             amountTotalAdultTicket.setText("$ 0.00");
             setTotalAmount();
         }
        if (countnoofChilds>0){

            amountofchildtickets = countnoofChilds*priceTicketChild;
            amountTotalChildTicket.setText(String.format("$  %.2f" , amountofchildtickets));
            setTotalAmount();
        }else {
            amountofchildtickets = 0;
            amountTotalChildTicket.setText("$ 0.00");
            setTotalAmount();
        }


    }

    private void setTotalAmount() {
        finalTotalAmount = amountofAdultstickets+amountofchildtickets;
        textTotalPrise.setText(String.format("$  %.2f" , finalTotalAmount));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setUiWithChild(CinemaDetail body) {

        childCard.setVisibility(View.VISIBLE);

        fixedRateAdultTickets.setText(String.format("$ %.2f" , priceTicketAdult));
        fixedRateChildTicket.setText(String.format("$ %.2f" , priceTicketChild));
        pDialog.dismiss();
    }

    private void setUiWithoutChild(CinemaDetail body) {


        childCard.setVisibility(View.GONE);
        headerAdultTickets.setText("Ticket's");
        fixedRateAdultTickets.setText(String.format("$ %.2f" , priceTicketAdult));

        pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (finalTotalAmount !=0) {
            editor.putString("ratesforAdultTicket", priceTicketAdult+"");
            editor.putString("ratesforChildTicket", priceTicketChild+"");
            editor.putString("MovieName", movieNametosend);
            editor.putString("MovieId", movieIdtosend);
            editor.putString("cinemaIDtoSend", cinemaidtosend);
            editor.putString("CinemaId2", cinemaname);
            editor.putString("totalPriceMovie", finalTotalAmount + "");
            editor.putInt("NoofChildTicket", countnoofChilds);
            editor.putInt("NoofAdultTicket", countnoofAdults);
            editor.putBoolean("MovieExist", true);
            editor.commit();
            if (sharedPreferences.getBoolean("offerExist", false)) {
                if (accessdatabase.offerExistornot()){
                Intent it = new Intent(this, PayAndCheckoutClass.class);
                startActivity(it);
                }else {
                    Intent it = new Intent(this, WhatsHotOffer.class);
                    startActivity(it);
                }
            } else {
                Intent it = new Intent(this, WhatsHotOffer.class);
                startActivity(it);
            }
        }else {
            Snackbar snackbar = Snackbar.make(letsGobtn, "Please Select A Ticket", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
    private void backgo(){
        super.onBackPressed();
    }
}
