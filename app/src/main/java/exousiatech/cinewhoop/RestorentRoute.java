package exousiatech.cinewhoop;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import RetrofitPackage.OfferDetail;

/**
 * Created by jagteshwar on 10-02-2016.
 */
public class RestorentRoute extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    SupportMapFragment mapFragment;

    ArrayList<Double> allLat = new ArrayList<Double>();
    ArrayList<Double> allLng = new ArrayList<Double>();
    LatLngBounds bound;
    GoogleMap googleMap;
    Location mlocation;
    LatLng ueserCurrentLocation;
    Typeface typefacemap;
    TextView mapIcon;
    OfferDetail eachOffer;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        eachOffer = (OfferDetail) getIntent().getSerializableExtra("latLong");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setSelected(true);
        toolbarTitle.setText("Location on Map");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Gmap_route);
        mapFragment.getMapAsync(RestorentRoute.this);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(eachOffer.getLatitude(), eachOffer.getLongitude()))
                );
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(eachOffer.getLatitude(), eachOffer.getLongitude()) ).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        getMenuInflater().inflate(R.menu.booking_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
