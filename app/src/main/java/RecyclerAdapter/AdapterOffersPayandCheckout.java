package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import Database.DatabaseHelperCinewhoop;
import exousiatech.cinewhoop.PayAndCheckoutClass;
import exousiatech.cinewhoop.R;

/**
 * Created by jagteshwar on 11-02-2016.
 */
//AdapterOffersPayandCheckout
public class AdapterOffersPayandCheckout extends RecyclerView.Adapter<AdapterOffersPayandCheckout.CinemaViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    Intent it;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Typeface typeface2;
    ArrayList<String> nameofOffer = new ArrayList<>();
    ArrayList<String> priceofOffer = new ArrayList<>();
    PayAndCheckoutClass payAndCheckoutClass;


    public AdapterOffersPayandCheckout(Context context){
        this.context = context;
        layoutInflater =LayoutInflater.from(context);
        payAndCheckoutClass = (PayAndCheckoutClass) context;
        typefaceChange = new Utilclass(this.context);

        acessDataBase = new DatabaseHelperCinewhoop(context);
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/cross.ttf");
        datafromDatabase =  acessDataBase.selectDataOffer();
    }


    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.checkout_custom_view, parent , false);
        CinemaViewHolder holder=new CinemaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.nameoffer);
        typefaceChange.changetypeface(holder.priceofeachoffer);
        holder.crossIcon.setTypeface(typeface2);
        String arr[] = datafromDatabase.get(position).split("~");

        holder.nameoffer.setText(arr[2]);
        holder.priceofeachoffer.setText(String.format("$ %.2f" , Double.parseDouble(arr[3])));

    }

    @Override
    public int getItemCount() {
        return datafromDatabase.size();
    }

    public  class CinemaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameoffer , priceofeachoffer ,crossIcon;

        public CinemaViewHolder(View itemView) {
            super(itemView);
            nameoffer = (TextView)itemView.findViewById(R.id.nameoffer);
            priceofeachoffer = (TextView)itemView.findViewById(R.id.priceofeachoffer);
            crossIcon = (TextView)itemView.findViewById(R.id.crossIcon);
            crossIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.crossIcon:
                     delefromDataBase(datafromDatabase.get(getAdapterPosition()));
                    datafromDatabase.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            payAndCheckoutClass.Inflateofferlayout();
                            payAndCheckoutClass.InflateMovielayout();
                        }
                    }, 1000);

                    break;
            }

        }
    }

    private void delefromDataBase(String s) {
        String arr[] = s.split("~");
        acessDataBase.deleteTableOfferwhereid(arr[0]);
    }
}

