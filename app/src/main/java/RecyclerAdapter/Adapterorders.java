package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.OrderDatail;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.WebviewForTicket;

/**
 * Created by jagteshwar on 27-02-2016.
 */
//public class Adapterorders {
//}
public class Adapterorders extends RecyclerView.Adapter<Adapterorders.orderViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    List<OrderDatail> detailsorder;
    Typeface typeface2;
    SimpleDateFormat mDateformat;
    public Adapterorders(Context context, List<OrderDatail> response){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        detailsorder = response;
        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/myorders.ttf");

    }

    @Override
    public orderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.myaccount_custom_view, parent , false);
        orderViewHolder holderorder=new orderViewHolder(view);
        return holderorder;
    }

    @Override
    public void onBindViewHolder(orderViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.nameoffer);
        typefaceChange.changetypeface(holder.priceofeachoffer);
        holder.crossIcon.setText(R.string.order);
        holder.crossIcon.setTypeface(typeface2);
        if (TextUtils.isEmpty(detailsorder.get(position).getOrderName())){
            holder.nameoffer.setText("CineWhoop Order");
        }else {
            holder.nameoffer.setText(detailsorder.get(position).getOrderName().trim());
        }
        holder.priceofeachoffer.setText(detailsorder.get(position).getOrderType());
        Log.e("details" ,detailsorder.get(position).getOrderName() + " "+ detailsorder.get(position).getOrderName());

    }

    @Override
    public int getItemCount() {
        return detailsorder.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameoffer , priceofeachoffer ,crossIcon ;
        LinearLayout layoutClick;
        public orderViewHolder(View itemView) {
            super(itemView);
            layoutClick = (LinearLayout)itemView.findViewById(R.id.layoutClick);
            nameoffer = (TextView)itemView.findViewById(R.id.nameoffer);
            priceofeachoffer = (TextView)itemView.findViewById(R.id.priceofeachoffer);
            crossIcon = (TextView)itemView.findViewById(R.id.crossIcon);
            layoutClick.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (detailsorder.get(getAdapterPosition()).getOrderType().equalsIgnoreCase("offer")){
                Intent browserIntent = new Intent(context , WebviewForTicket.class);
                browserIntent.putExtra("url" , ConfigClass.BASE_URL + "admin/upload/" + detailsorder.get(getAdapterPosition()).getOrderValueImg());
               browserIntent.putExtra("type" , "My Coupon");
               browserIntent.putExtra("OrderType" , detailsorder.get(getAdapterPosition()).getOrder_format());
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(browserIntent);

            }else {
                Log.e("url" , detailsorder.get(getAdapterPosition()).getTicketValueLink());
                Intent browserIntent = new Intent(context , WebviewForTicket.class);
                browserIntent.putExtra("url" , detailsorder.get(getAdapterPosition()).getTicketValueLink());
                browserIntent.putExtra("type" , "My Ticket");
                browserIntent.putExtra("OrderType" , detailsorder.get(getAdapterPosition()).getOrder_format());
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(browserIntent);
            }
        }

    }
}