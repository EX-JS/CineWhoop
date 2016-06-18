package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import RetrofitPackage.SoonDetail;
import exousiatech.cinewhoop.R;

/**
 * Created by jagteshwar on 10-02-2016.
 */
//AdapterComingSoon
public class AdapterComingSoon extends RecyclerView.Adapter<AdapterComingSoon.SoonViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    List<SoonDetail> SoonDetails;
    String imdb_url;
    public AdapterComingSoon(Context context, List<SoonDetail> SoonDetails){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        this.SoonDetails = SoonDetails;
    }
    @Override
    public SoonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view_offer, parent , false);
        SoonViewHolder holder=new SoonViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SoonViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.getoffer);
        typefaceChange.changetypeface(holder.offerName);

        holder.offerName.setText(SoonDetails.get(position).getName());
        holder.getoffer.setText(SoonDetails.get(position).getCategory());
        Picasso.with(context)
                .load(ConfigClass.BASE_URL + "admin/upload/" + SoonDetails.get(position).getImage().get(0))
                .placeholder(R.drawable.preloader)
                .into(holder.offerBackground);

    }

    @Override
    public int getItemCount() {
        return SoonDetails.size();
    }

    public class SoonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView getoffer ,offerName;
        ImageView offerBackground;
        RelativeLayout layoutClick;
        public SoonViewHolder(View itemView) {
            super(itemView);
            getoffer = (TextView)itemView.findViewById(R.id.getoffer);
            offerName = (TextView)itemView.findViewById(R.id.offerName);
            offerBackground = (ImageView)itemView.findViewById(R.id.offerImage);
            layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
            layoutClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                imdb_url = SoonDetails.get(getAdapterPosition()).getImdbUrl();
                if (!imdb_url.startsWith("http://") && !imdb_url.startsWith("https://")) {
                    imdb_url = "http://" + imdb_url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdb_url));
                    context.startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdb_url));
                    context.startActivity(browserIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
