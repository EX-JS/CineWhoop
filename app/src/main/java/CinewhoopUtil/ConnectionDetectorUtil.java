package CinewhoopUtil;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import exousiatech.cinewhoop.R;

/**
 * Created by jagteshwar on 26-01-2016.
 */
public class ConnectionDetectorUtil extends BroadcastReceiver implements InternetNotification {
    boolean connected=false;
    Dialog dialog;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetNetworkInfo =manager.getActiveNetworkInfo();
        if((dialog!=null))
        {

            dialog.dismiss();
        }
        if(internetNetworkInfo==null||!internetNetworkInfo.isConnectedOrConnecting())
        {

        createDialog(context);
        }
    }
    public void createDialog(Context context)
    {
        dialog=new Dialog(context);
        Window window=dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        dialog.setContentView(R.layout.internet_cinewhoop);
        window.getAttributes().windowAnimations= R.style.inoutAnimation;
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams windowParams= new WindowManager.LayoutParams();
        windowParams.copyFrom(window.getAttributes());
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.gravity= Gravity.BOTTOM;
        window.setAttributes(windowParams);
        dialog.show();

    }


    @Override
    public void dismissDialog() {
        if(dialog!=null)
        {

            dialog.dismiss();
        }
    }
}
