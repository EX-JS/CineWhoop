package CinewhoopUtil;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Type;

/**
 * Created by jagteshwar on 08-02-2016.
 */
public class CustomTextView extends TextView {
    Context context;
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets() , "fonts/registepage.ttf");
        setTypeface(typeface);
    }
}
