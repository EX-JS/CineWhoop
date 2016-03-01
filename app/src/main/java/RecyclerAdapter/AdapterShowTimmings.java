package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CinewhoopUtil.Utilclass;
import RetrofitPackage.CineWhoopDetail;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.TicketsSelectionClass;

/**
 * Created by jagteshwar on 28-01-2016.
 */
public class AdapterShowTimmings extends RecyclerView.Adapter<AdapterShowTimmings.ShowTimmingsViewHolder>{
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    String stringofTimmings , cinemaName, cinemaId;
    StringBuilder stringBuilder;
    String timmingArr[];
    List<String> times =new ArrayList<>();
    ArrayList<String> timmingstoset = new ArrayList<>();
    CineWhoopDetail movieDetails;



    public AdapterShowTimmings(Context context, List<String> eachmovie, CineWhoopDetail movieDetails, String cinemaName, String cinemaid){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        times = eachmovie;
        this.movieDetails = movieDetails;
        this.cinemaName = cinemaName;
        this.cinemaId = cinemaid;
        stringBuilder = new StringBuilder();
        for (int i = 0;i<times.size();i++){
            stringBuilder.append(times.get(i));
        }
        stringofTimmings = stringBuilder.toString();
        timmingArr = stringofTimmings.split(";");
        for (int x=0;x<timmingArr.length;x++){
            try {
                String _24HourTime = timmingArr[x];
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                Log.e("time a p" , _12HourSDF.format(_24HourDt));
                timmingstoset.add(_12HourSDF.format(_24HourDt));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public ShowTimmingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.select_layout_genre, parent , false);
        ShowTimmingsViewHolder holder=new ShowTimmingsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShowTimmingsViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.genreOrCinema);
        typefaceChange.changetypeface(holder.selectshowtimmings);



        holder.genreOrCinema.setText(timmingstoset.get(position));
    }

    @Override
    public int getItemCount() {
        return timmingstoset.size();
    }




    public class ShowTimmingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView selectshowtimmings ,genreOrCinema;
        RelativeLayout layoutClick;
        public ShowTimmingsViewHolder(View itemView) {
            super(itemView);
            selectshowtimmings = (TextView)itemView.findViewById(R.id.selectType);
            genreOrCinema = (TextView)itemView.findViewById(R.id.cinemaOrGenre);
            layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
            layoutClick.setOnClickListener(this);
            layoutClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent it = new Intent(context , TicketsSelectionClass.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            switch (v.getId()){
                case R.id.layoutClick:
                    Log.e("time", genreOrCinema.getText().toString());
                    Log.e("time", movieDetails.getMovieId() + " " + movieDetails.getTitle()+ " " +cinemaName);

                    it.putExtra("cinemaIDtoSend", cinemaId);
                    it.putExtra("cinemaName" , cinemaName);
                    it.putExtra("MovieId" , movieDetails.getMovieId() );
                    it.putExtra("movieDetails" , movieDetails.getTitle());
                    context.startActivity(it);
                    break;


            }

        }
    }

}
