package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jagteshwar on 30-01-2016.
 */
public class DatabaseCinewhoop extends SQLiteOpenHelper {
    public static final String DBNAME = "mydb.db";
    public static final String TABLENAME = "cinewhoop";
    public static final int VERSIONID = 1;

    public static String CategoryName = "Name";
    public static String CategoryValue = "id";
    public static final String TABLENAMEOffer = "cinewhoopOffer";
    public static String OfferPrice = "price";
    public static String OfferId = "idOffer";
    public static String OfferIdprimary = "idOfferprimary";
    public static String OfferName = "OfferName";


    public static String QUERY = "create table " + TABLENAME + " ( " + CategoryName
            + " Text primary key, " + CategoryValue + " TEXT not null " + " )";
public static String QUERYOFFER = "create table " + TABLENAMEOffer + " ( " +  OfferIdprimary
            + " integer primary key autoincrement, "  + OfferId
    + " TEXT not null, "+ OfferName + " TEXT not null, " + OfferPrice + " TEXT not null "+ " )";



    public DatabaseCinewhoop (Context ct){
        super(ct, DBNAME, null, VERSIONID);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(QUERY);
            db.execSQL(QUERYOFFER);
            Log.e("Tableeeeeeeeeeeeeee", "table Created");

        }catch (Exception e){
            Log.e("error exp" , e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
