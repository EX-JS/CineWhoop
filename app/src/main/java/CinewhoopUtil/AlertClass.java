package CinewhoopUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import exousiatech.cinewhoop.HomeActivity;

/**
 * Created by Jagteshwar.Singh on 9/15/2015.
 */
public class AlertClass {
    public void showAlertDialog(Context context,  String message,
                                Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setMessage(message);
        if(status != null)

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }
    public void showAlertDialogforhome(final Context context,  String message,
                                Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        if(status != null)

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                    Intent it = new Intent(context , HomeActivity.class);
                    context.startActivity(it);
                }
            });


        alertDialog.show();
    }
}
