package exousiatech.cinewhoop;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;



/**
 * Created by jagteshwar on 03-02-2016.
 */
public class CineWhoopAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        LeakCanary.install(this);
    }
}
