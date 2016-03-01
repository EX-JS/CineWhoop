package exousiatech.cinewhoop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.Utilclass;
import Fragments.NavigationDrawer;

/**
 * Created by john on 12/24/2015.
 */
public class CustomerSupportActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mdrawerlayout;
    LinearLayout mainContent;
    int frag;
    Toolbar toolbar;
    NavigationDrawer navDrawer;
    TextView customerSupHeader, emailText , toolbatTitle;
    Button  emailUs, enquiry ;
    Utilclass typefaceChange;
    ArrayList<TextView> tv;
    Dialog pDialog;
    Window window;
    EditText name , phoneno, bodyText;
    String sendemailtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typefaceChange = new Utilclass(getApplicationContext());
        setContentView(R.layout.customer_support);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tv = new ArrayList<>();
        toolbatTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbatTitle.setText("Customer Support");
        tv.add(toolbatTitle);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mainContent = (LinearLayout) findViewById(R.id.mainview);
        frag = R.id.left_drawer;
        navDrawer = ((NavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.left_drawer));
        navDrawer.actionbarToogler(this, mainContent, mdrawerlayout, toolbar, frag);

        customerSupHeader = (TextView)findViewById(R.id.customerSupHeader);
        tv.add(customerSupHeader);
        emailUs = (Button)findViewById(R.id.emailUs);
        enquiry = (Button)findViewById(R.id.enquiry);
        emailText = (TextView)findViewById(R.id.emailText);
        tv.add(emailText);
        typefaceChange.applycustomfont(tv);
        emailUs.setOnClickListener(this);
        enquiry.setOnClickListener(this);
        pDialog = new Dialog(CustomerSupportActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.ask_question_dialog);
        window = pDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams  layoutParams= new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        pDialog.findViewById(R.id.sendEmail).setOnClickListener(this);
        name =(EditText)pDialog.findViewById(R.id.name);
        phoneno = (EditText)pDialog.findViewById(R.id.phoneNo);
        bodyText = (EditText)pDialog.findViewById(R.id.messageText);
        pDialog.setCancelable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enquiry:
                pDialog.show();
                break;
            case R.id.emailUs:
                sendEmail(this , "" , "Contact Cinewhoop");
                break;
            case R.id.sendEmail:
                if (TextUtils.isEmpty(name.getText())){
                    name.setError("Please Enter Your Name");
                }else if (TextUtils.isEmpty(bodyText.getText())){
                    bodyText.setError("Please Enter Your query");
                }else if (phoneNumbervali(phoneno.getText().toString())){
                    phoneno.setError("Please Enter a Valid Phone Number");

                }
                else {
                    sendemailtext = bodyText.getText().toString() + "\n\n" + name.getText().toString() + "\n"
                            + phoneno.getText().toString();
                    sendEmail(this, sendemailtext , "Enquiry");
                }
                break;
        }
    }

    private boolean phoneNumbervali(String s) {
        if (s.equalsIgnoreCase("")){
            return false;
        }else if (s.length()==10){
            return false;
        }else {
            return true;
        }
    }

    public void sendEmail(Context ctx, String sendemailtext ,String subject ) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto" , "enquiry@cinewhoop.com",null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);// Replace your title with "TITLE"
        emailIntent.putExtra(Intent.EXTRA_TEXT, sendemailtext);
        ctx.startActivity(Intent.createChooser(emailIntent, "Send E-Mail..."));// it will provide you supported all app to send email.
    }
}
