package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.CategoryDetails;
import RetrofitPackage.OrderDatail;
import RetrofitPackage.OrderDetails;
import exousiatech.cinewhoop.MoviesActivity;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.SelectGenre;

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
    Intent it;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Typeface typeface2;
    SharedPreferences.Editor editor;
    public Adapterorders(Context context, List<OrderDatail> response){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        detailsorder = response;
        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/cross.ttf");

    }
    @Override
    public orderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.checkout_custom_view, parent , false);
        orderViewHolder holder=new orderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(orderViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.nameoffer);
        typefaceChange.changetypeface(holder.priceofeachoffer);
        holder.crossIcon.setTypeface(typeface2);
        Log.e("position and name", position+" "+ detailsorder.get(position).getOrderName()+" "+ detailsorder.get(position));
        holder.nameoffer.setText(detailsorder.get(position).getOrderName());
        holder.priceofeachoffer.setText(detailsorder.get(position).getOrderType());

    }

    @Override
    public int getItemCount() {
        return detailsorder.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder  {
        TextView nameoffer , priceofeachoffer ,crossIcon;
        RelativeLayout layoutClick;
        public orderViewHolder(View itemView) {
            super(itemView);
            nameoffer = (TextView)itemView.findViewById(R.id.nameoffer);
            priceofeachoffer = (TextView)itemView.findViewById(R.id.priceofeachoffer);
            crossIcon = (TextView)itemView.findViewById(R.id.crossIcon);

        }


    }
}