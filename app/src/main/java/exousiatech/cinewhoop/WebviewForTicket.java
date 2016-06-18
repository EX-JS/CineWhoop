package exousiatech.cinewhoop;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.ConnectionDetectorUtil;
import CinewhoopUtil.Utilclass;

/**
 * Created by jagteshwar on 01-03-2016.
 */
public class WebviewForTicket extends AppCompatActivity{
    Toolbar toolbar;
    TextView toolbarTitle;
    ArrayList<TextView> tv;
    Utilclass typechange;
    ConnectionDetectorUtil connectionDetectorUtil;
    Dialog pDialog;
    String titleToSet;
    String typeoforder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pdfwebview);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tv = new ArrayList<>();
        setSupportActionBar(toolbar);
        titleToSet = getIntent().getStringExtra("type");
        typeoforder = getIntent().getStringExtra("OrderType");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText(titleToSet);
        typechange = new Utilclass(getApplicationContext());
        tv.add(toolbarTitle);
        typechange.applycustomfont(tv);
        WebView webView=(WebView)findViewById(R.id.pdf_webview);
        webView.setInitialScale(15);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheEnabled(true);
        if (typeoforder.equalsIgnoreCase("pdf")){
            webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + getIntent().getStringExtra("url"));
        }else {
            webView.loadUrl(getIntent().getStringExtra("url"));

        }
        pDialog = new Dialog(WebviewForTicket.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
        pDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pDialog!= null){
                    pDialog.dismiss();}
            }
        },4000);

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
