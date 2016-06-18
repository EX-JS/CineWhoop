package RecyclerAdapter;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import CinewhoopUtil.ConfigClass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.CineWhoopDetail;
import exousiatech.cinewhoop.R;

import CinewhoopUtil.Utilclass;
import exousiatech.cinewhoop.BookingScreen;


/**
 * Created by john on 12/28/2015.
 */
public class AdapterMovieList extends RecyclerView.Adapter<AdapterMovieList.MyviewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    List<CineWhoopDetail> details, details2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    boolean cinemaFilter, genreFilter, dateFilter;

    public AdapterMovieList(Context context, List<CineWhoopDetail> details) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        this.details = details;
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        acessDataBase = new DatabaseHelperCinewhoop(context);
    }


    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_custom_view, parent, false);
        MyviewHolder holder = new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        typefaceChange.changetypeface(holder.movieTitle);
        typefaceChange.changetypeface(holder.movieduration);
        typefaceChange.changetypeface(holder.movieType);
        typefaceChange.changetypeface(holder.bookBtn);

        holder.movieTitle.setText(details.get(position).getTitle());
        holder.movieduration.setText(convertmintohours(details.get(position).getMovieLenght()));

        holder.movieType.setText(details.get(position).getCategory());
        holder.movieRating.setText(details.get(position).getRating());
        Picasso.with(context)
                .load(ConfigClass.BASE_URL + "admin/upload/" + details.get(position).getFeatured_image().get(0))
                .placeholder(R.drawable.preloader)
                .into(holder.movieBackgroundImage);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitle, movieType, movieduration, movieRating;
        ImageView movieBackgroundImage;
        TextView bookBtn;

        public MyviewHolder(View itemView) {
            super(itemView);

            movieTitle = (TextView) itemView.findViewById(R.id.movieName);
            movieType = (TextView) itemView.findViewById(R.id.movieType);
            movieduration = (TextView) itemView.findViewById(R.id.movieTime);
            movieRating = (TextView) itemView.findViewById(R.id.rating);
            movieBackgroundImage = (ImageView) itemView.findViewById(R.id.movieBackground);
            bookBtn = (TextView) itemView.findViewById(R.id.bookBtn);

            bookBtn.setOnClickListener(this);
            movieBackgroundImage.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent it = null;
            try {
                it = new Intent(context, BookingScreen.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("movie_detail", details.get(getAdapterPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (v.getId()) {

                case R.id.movieBackground:
                    context.startActivity(it);

                    break;
                case R.id.bookBtn:
                    context.startActivity(it);
                    break;
            }
        }
    }

    public void animateTo(ArrayList<CineWhoopDetail> contactList) {
        applyAndAnimateRemovals(contactList);
    }


    private void applyAndAnimateRemovals(ArrayList<CineWhoopDetail> mcontactList) {
        details = mcontactList;
        notifyDataSetChanged();

    }

    public String convertmintohours(String min) {
        String startTime = "00:00";
        int minutes = Integer.parseInt(min);
        int h = minutes / 60 + Integer.valueOf(startTime.substring(0, 1));
        int m = minutes % 60 + Integer.valueOf(startTime.substring(3, 4));
        String newtime = h + " hr " + m + " min";
        return newtime;
    }

}
