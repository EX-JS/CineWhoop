package CinewhoopUtil;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by jagteshwar on 06-01-2016.
 */
public class Utilclass {
    Context context;
    Typeface typeface;
    public Utilclass(Context context){
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/cinewhoop.ttf");
    }


    public void applycustomfont(ArrayList<TextView > tv)
    {
    for (int i=0; i<tv.size() ;i++){
        tv.get(i).setTypeface(typeface);
    }


    }

    public void changetypeface(TextView adaptertextview){
        adaptertextview.setTypeface(typeface);
    }

    public void applytypetoToolbar(ActionBar toolbar , String title){
        SpannableString sp = new  SpannableString(title);
        sp.setSpan(new TypefaceSpan("fonts/cinewhoop.ttf") ,0,sp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(sp);
    }
}
