package RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.CinemaDetail;
import exousiatech.cinewhoop.MoviesActivity;
import exousiatech.cinewhoop.R;

import CinewhoopUtil.Utilclass;

/**
 * Created by jagteshwar on 05-01-2016.
 */

public class AdapterCinema extends RecyclerView.Adapter<AdapterCinema.CinemaViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    List<CinemaDetail> cinemaDetails;
    Intent it;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Activity selectCinemaActivity;

    public AdapterCinema(Context context, List<CinemaDetail> cinemaDetails, Activity selectCinemaActivity){
        this.context = context;
        layoutInflater =LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        this.cinemaDetails = cinemaDetails;
        acessDataBase = new DatabaseHelperCinewhoop(context);
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.selectCinemaActivity = selectCinemaActivity;

    }
    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.select_layout_cinema, parent , false);
        CinemaViewHolder holder=new CinemaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.selectcinema);
        typefaceChange.changetypeface(holder.cinemaOrGenre);

        holder.cinemaOrGenre.setText(cinemaDetails.get(position).getCinemaName());


            Picasso.with(context)
                    .load(ConfigClass.BASE_URL+"admin/upload/"+cinemaDetails.get(position).getLogo())
                    .into(holder.cinemaIcon);
    }

    @Override
    public int getItemCount() {
        return cinemaDetails.size();
    }

    public  class CinemaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView selectcinema , cinemaOrGenre;
        ImageView cinemaIcon;
        RelativeLayout layoutClick;
    public CinemaViewHolder(View itemView) {
        super(itemView);
        selectcinema = (TextView)itemView.findViewById(R.id.selectType);
        cinemaOrGenre = (TextView)itemView.findViewById(R.id.cinemaOrGenre);
        cinemaIcon = (ImageView)itemView.findViewById(R.id.cinema_icon);
        layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
        layoutClick.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layoutClick:

                   boolean flag =  acessDataBase.insertDataintoTable("Filterbycinema",cinemaDetails.get(getAdapterPosition()).getCinemaId());
                    if (flag){
                        editor.putString("CinemaName" , cinemaDetails.get(getAdapterPosition()).getCinemaName());
                        editor.putBoolean("CinemaNameExist",true);
                        editor.putBoolean("CinemaFilterExist", true);
                        editor.putBoolean("DateFilterExist", false);
                        editor.putBoolean("GenreFilterExist", false);
                        editor.commit();
                        it = new Intent(context , MoviesActivity.class);

                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                        selectCinemaActivity.finish();
                    }else {
                        Toast.makeText(context, "This filter cannot be applied now", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    }
}
