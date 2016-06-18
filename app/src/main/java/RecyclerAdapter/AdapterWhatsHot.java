package RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import CinewhoopUtil.ConfigClass;
import RetrofitPackage.OfferDetail;
import exousiatech.cinewhoop.OfferDescription;
import exousiatech.cinewhoop.R;

import CinewhoopUtil.Utilclass;

/**
 * Created by jagteshwar on 05-01-2016.
 */
public class AdapterWhatsHot extends RecyclerView.Adapter<AdapterWhatsHot.HotViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    List<OfferDetail> offerDetails;
    Activity activity;
    public AdapterWhatsHot(Context context, List<OfferDetail> offerDetails, Activity activity){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        this.offerDetails = offerDetails;
        this.activity = activity;
    }
    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view_offer, parent , false);
        HotViewHolder holder=new HotViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.getoffer);
        typefaceChange.changetypeface(holder.offerName);

        holder.offerName.setText(offerDetails.get(position).getName().trim());
        Picasso.with(context)
                .load(ConfigClass.BASE_URL + "admin/upload/" + offerDetails.get(position).getImage().get(0))
                .placeholder(R.drawable.preloader)
                .into(holder.offerBackground);

    }

    @Override
    public int getItemCount() {
        return offerDetails.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView getoffer ,offerName;
        ImageView offerBackground;
        RelativeLayout layoutClick;
        public HotViewHolder(View itemView) {
            super(itemView);
            getoffer = (TextView)itemView.findViewById(R.id.getoffer);
            offerName = (TextView)itemView.findViewById(R.id.offerName);
            offerBackground = (ImageView)itemView.findViewById(R.id.offerImage);
            layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
            layoutClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent it = null;
            try {
                it = new Intent(context , OfferDescription.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("offer_detail", offerDetails.get(getAdapterPosition()));
                context.startActivity(it);
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
