package exousiatech.cinewhoop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.OfferDetail;

/**
 * Created by jagteshwar on 05-02-2016.
 */
public class OfferDescription extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    OfferDetail eachOffer;
    TextView offerName , offerPrice, offerDescription , offerDescHeader , validUptoHeader , validUptoText ;
    Button getOffer ,websiteurl  ,direction;
    ImageView offerImage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String websiteUrlLink;
    DatabaseHelperCinewhoop acessDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_description);
        eachOffer = (OfferDetail) getIntent().getSerializableExtra("offer_detail");
        acessDataBase = new DatabaseHelperCinewhoop(OfferDescription.this);
        sharedPreferences = getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setSelected(true);
        toolbarTitle.setText(eachOffer.getName());
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        offerName = (TextView)findViewById(R.id.offerName);
        tv.add(offerName);
        offerPrice= (TextView)findViewById(R.id.offerPrice);
        tv.add(offerPrice);
        offerDescription = (TextView)findViewById(R.id.descriptionText);
        tv.add(offerDescription);
        offerDescHeader= (TextView)findViewById(R.id.descriptionHeader);
        tv.add(offerDescHeader);
        validUptoHeader= (TextView)findViewById(R.id.validuptoHeader);
        tv.add(validUptoHeader);
        validUptoText= (TextView)findViewById(R.id.validuptoText);
        tv.add(validUptoText);
        websiteurl= (Button)findViewById(R.id.websiteurl);
        tv.add(websiteurl);
        direction= (Button)findViewById(R.id.direction);
        tv.add(direction);
        typechange.applycustomfont(tv);

        offerImage = (ImageView)findViewById(R.id.offerImage);
        getOffer = (Button)findViewById(R.id.getofferbtn);
        getOffer.setOnClickListener(this);
        websiteurl.setOnClickListener(this);
        direction.setOnClickListener(this);

        setUi();
    }

    private void setUi() {
        offerName.setText(eachOffer.getName());
        offerPrice.setText("$ "+eachOffer.getPrice());
        offerDescription.setText(eachOffer.getDescription());
//        websiteurl.setText("Visit us :"+eachOffer.getWebsiteUrl());
        Picasso.with(getApplicationContext())
                .load(ConfigClass.BASE_URL+"umax/upload/"+eachOffer.getImage().get(0))
                .into(offerImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getofferbtn:
                boolean flag =  acessDataBase.insertDataintoOfferTable(eachOffer.getOffer_id(), eachOffer.getName() , eachOffer.getPrice());
                if (flag){
                    editor.putString("offerid", eachOffer.getOffer_id());
                    editor.putBoolean("offerExist", true);
                    editor.commit();
                    Toast.makeText(this, "The Offer has been Added to your Cart Successfully", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(this , WhatsHotOffer.class);
                    startActivity(it);
                    finish();
                }else {
                    Toast.makeText(this, " This offer is already selected", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.websiteurl:
                websiteUrlLink = eachOffer.getWebsiteUrl();
                if (!websiteUrlLink.startsWith("http://") && !websiteUrlLink.startsWith("https://")) {
                    websiteUrlLink = "http://" + websiteUrlLink;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrlLink));
                    startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrlLink));
                    startActivity(browserIntent);
                }
                break;
            case R.id.direction:
                Intent it = new Intent(OfferDescription.this , RestorentRoute.class);
                it.putExtra("latLong",eachOffer);
                startActivity(it);
                break;
        }

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
