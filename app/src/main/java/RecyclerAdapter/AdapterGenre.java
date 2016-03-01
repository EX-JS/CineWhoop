package RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import CinewhoopUtil.ConfigClass;
import Database.DatabaseHelperCinewhoop;
import RetrofitPackage.CategoryDetails;
import exousiatech.cinewhoop.MoviesActivity;
import exousiatech.cinewhoop.R;

import CinewhoopUtil.Utilclass;
import exousiatech.cinewhoop.SelectGenre;

/**
 * Created by jagteshwar on 05-01-2016.
 */
public class AdapterGenre extends RecyclerView.Adapter<AdapterGenre.GenreViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    Utilclass typefaceChange;
    CategoryDetails detailsCategory;
    Intent it;
    DatabaseHelperCinewhoop acessDataBase;
    ArrayList<String> datafromDatabase = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public AdapterGenre(Context context, CategoryDetails response){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        typefaceChange = new Utilclass(this.context);
        detailsCategory = response;
        acessDataBase = new DatabaseHelperCinewhoop(context);
        sharedPreferences = context.getSharedPreferences(ConfigClass.Shared_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.select_layout_genre, parent , false);
        GenreViewHolder holder=new GenreViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        typefaceChange.changetypeface(holder.genreOrCinema);
        typefaceChange.changetypeface(holder.selectgenre);
        holder.genreOrCinema.setText(detailsCategory.getCategory().get(position));
    }

    @Override
    public int getItemCount() {
        return detailsCategory.getCategory().size() ;
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView selectgenre ,genreOrCinema;
        RelativeLayout layoutClick;
        public GenreViewHolder(View itemView) {
            super(itemView);
            selectgenre = (TextView)itemView.findViewById(R.id.selectType);
            genreOrCinema = (TextView)itemView.findViewById(R.id.cinemaOrGenre);
            layoutClick = (RelativeLayout)itemView.findViewById(R.id.layoutClick);
            layoutClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layoutClick:
                    boolean flag =  acessDataBase.insertDataintoTable("Filterbygenre",detailsCategory.getCategory().get(getAdapterPosition()));
                    if (flag){

                        editor.putBoolean("GenreFilterExist", true);
                        editor.commit();
                        it = new Intent(context , MoviesActivity.class);

                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                        SelectGenre.activity.finish();
                    }else {
                        Toast.makeText(context, " This filter cannot be applied now", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
