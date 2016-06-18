package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import CinewhoopUtil.ConfigClass;
import CinewhoopUtil.Utilclass;
import RetrofitPackage.CineWhoopDetail;
import RetrofitPackage.MovieTimmingDetails;
import exousiatech.cinewhoop.R;
import exousiatech.cinewhoop.ShowTimmingsClass;

/**
 * Created by jagteshwar on 27-01-2016.
 */
public class AdapterSelectedMovieCinema extends RecyclerView.Adapter<AdapterSelectedMovieCinema.selectedMovieCinemaViewHolder>  {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    CineWhoopDetail eachmovie;
    MovieTimmingDetails timmingDetails;

    String str[];

    public AdapterSelectedMovieCinema(Context context, CineWhoopDetail eachmovie, MovieTimmingDetails timmingDetails){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        this.eachmovie = eachmovie;
        this.timmingDetails = timmingDetails;

        String string =  eachmovie.getCinema_id();
        str = string.split(",");
    }
    @Override
    public selectedMovieCinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.select_layout_genre, parent , false);
        selectedMovieCinemaViewHolder holder=new selectedMovieCinemaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(selectedMovieCinemaViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.genreOrCinema);
        typefaceChange.changetypeface(holder.selectcinemaWithMovie);
        holder.genreOrCinema.setText(timmingDetails.getData().get(position).getCinemaName());

        /*
            if (str[position].equalsIgnoreCase("1") ){
              holder.genreOrCinema.setText("Hoyts Stafford");
            }else if (str[position].equalsIgnoreCase("2") ){
                holder.genreOrCinema.setText("Hoyts Redcliffe");
            }else if (str[position].equalsIgnoreCase("3") ){
                holder.genreOrCinema.setText("Hoyts Sunnybank");
            }else if (str[position].equalsIgnoreCase("4") ){
                holder.genreOrCinema.setText("Yatala Drive In");
            }else if (str[position].equalsIgnoreCase("5") ){
                holder.genreOrCinema.setText("Dendy Portside");
            }else if (str[position].equalsIgnoreCase("6") ){
                holder.genreOrCinema.setText("New Farm Cinemas");

        }*/
    }

    @Override
    public int getItemCount() {
        return timmingDetails.getData().size();
    }



    public class selectedMovieCinemaViewHolder extends RecyclerView.ViewHolder  {
        TextView selectcinemaWithMovie ,genreOrCinema;
        RelativeLayout layoutClick;
        public selectedMovieCinemaViewHolder(View itemView) {
            super(itemView);
            selectcinemaWithMovie = (TextView)itemView.findViewById(R.id.selectType);
            genreOrCinema = (TextView)itemView.findViewById(R.id.cinemaOrGenre);
            layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
            layoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectcinemaWithMovie.getText();

                    Intent it = new Intent(context , ShowTimmingsClass.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            it.putExtra("movie_Time_detail", eachmovie);
            it.putExtra(ConfigClass.TimmingsCinema,timmingDetails.getData().get(getAdapterPosition()) );
            it.putExtra("cinema_name", genreOrCinema.getText().toString());
            context.startActivity(it);
                }
            });

        }

    }
}
