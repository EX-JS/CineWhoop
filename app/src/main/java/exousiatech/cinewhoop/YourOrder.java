package exousiatech.cinewhoop;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;

/**
 * Created by jagteshwar on 25-02-2016.
 */
public class YourOrder extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    ConnectionDetectorUtil connectionDetectorUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Your Orders");
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);

        typechange.applycustomfont(tv);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://businessportal.hoyts.com.au/electronicdelivery/8328d461-85ec-4cea-b782-e7ed49a4cc99"));
        startActivity(browserIntent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        connectionDetectorUtil=new ConnectionDetectorUtil();
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionDetectorUtil, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionDetectorUtil);
        connectionDetectorUtil.dismissDialog();
    }
}


//        Textwather
//cardno1.addTextChangedListener(new TextWatcher() {
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("count", count + "");
//        if (cardno1.getText().toString().length() == 4) {
//        cardno2.requestFocus();
//        }
//        }
//
//@Override
//public void afterTextChanged(Editable s) {
//
//        }
//        });
//        cardno2.addTextChangedListener(new TextWatcher() {
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("count", count + "");
//        if (cardno2.getText().toString().length() == 4) {
//        cardno3.requestFocus();
//        }
//
//        }
//
//@Override
//public void afterTextChanged(Editable s) {
//
//        }
//        });
//        cardno3.addTextChangedListener(new TextWatcher() {
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("count", count + "");
//        if (cardno3.getText().toString().length() == 4) {
//        cardno4.requestFocus();
//        }
//        }
//
//@Override
//public void afterTextChanged(Editable s) {
//
//        }
//        });
//        cardno4.addTextChangedListener(new TextWatcher() {
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("count", count + " " + before + " " + start);
//        if (cardno4.getText().toString().length() == 4) {
//        cvnNumber.requestFocus();
//        }
//        }
//
//@Override
//public void afterTextChanged(Editable s) {
//
//        }
//        });
//        cvnNumber.addTextChangedListener(new TextWatcher() {
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("count", count + " " + before + " " + start);
//        if (cvnNumber.getText().toString().length() == 3) {
//        nameOnCard.requestFocus();
//        }
//        }
//
//@Override
//public void afterTextChanged(Editable s) {
//
//        }
//        });