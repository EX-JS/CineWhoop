package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jagteshwar on 30-01-2016.
 */
public class DatabaseHelperCinewhoop {
    DatabaseCinewhoop dbcinewhoop;
    SQLiteDatabase sqdb;
    String allcolumn[] = { DatabaseCinewhoop.CategoryName , DatabaseCinewhoop.CategoryValue };
    String allcolumnOffer[] = {DatabaseCinewhoop.OfferIdprimary,  DatabaseCinewhoop.OfferId , DatabaseCinewhoop.OfferName,DatabaseCinewhoop.OfferPrice };

    public DatabaseHelperCinewhoop (Context ct){
        try {
            dbcinewhoop = new DatabaseCinewhoop(ct );
            sqdb = dbcinewhoop.getWritableDatabase();
            Log.e("ddddddddddddddddd", "Data Base and table created");
        }catch (Exception e){
            Log.e("Error in connection" , e.getMessage());
        }
    }
    public boolean insertDataintoTable(String filterName , String filterValue){
        boolean flag = false;
        try{
            ContentValues cv = new ContentValues();
            cv.put(DatabaseCinewhoop.CategoryValue , filterName);
            cv.put(DatabaseCinewhoop.CategoryName , filterValue);
            long row = sqdb.insertWithOnConflict(DatabaseCinewhoop.TABLENAME, null, cv , SQLiteDatabase.CONFLICT_IGNORE);
            if (row > 0) {
                flag = true;
            }
        }catch (Exception e){
            Log.e("Error inserting" , e.getMessage());
        }
        return flag;
    }
    public boolean insertDataintoOfferTable(String offerid , String offerName , String OfferPrice){
        boolean flag = false;
        try{
            ContentValues cv = new ContentValues();
            cv.put(DatabaseCinewhoop.OfferId , offerid);
            cv.put(DatabaseCinewhoop.OfferName , offerName);
            cv.put(DatabaseCinewhoop.OfferPrice , OfferPrice);
            long row = sqdb.insert(DatabaseCinewhoop.TABLENAMEOffer, null, cv);
            if (row > 0) {
                flag = true;
            }
        }catch (Exception e){
            Log.e("Error inserting" , e.getMessage());
        }
        return flag;
    }
    public ArrayList<String> selectData() {
        ArrayList<String> data = new ArrayList<String>();
        Cursor c = sqdb.query(DatabaseCinewhoop.TABLENAME, allcolumn, null, null,
                null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            data.add(c.getString(0) + " , " + c.getString(1) );
            c.moveToNext();
        }
        return data;
    }
    public ArrayList<String> selectDataOffer() {
        ArrayList<String> data = new ArrayList<String>();
        Cursor c = sqdb.query(DatabaseCinewhoop.TABLENAMEOffer, allcolumnOffer, null, null,
                null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            data.add(c.getString(0) + "~" + c.getString(1) +"~"+c.getString(2) +"~" +c.getString(3) );
            c.moveToNext();
        }
        return data;
    }
    public boolean deleteTable(){
        sqdb.delete(DatabaseCinewhoop.TABLENAME, null, null);
        return true;
    }
    public boolean deleteTableOffer(){
        sqdb.delete(DatabaseCinewhoop.TABLENAMEOffer, null,null);
        return true;
    }
    public boolean deleteTableOfferwhereid(String id){
        sqdb.delete(DatabaseCinewhoop.TABLENAMEOffer, DatabaseCinewhoop.OfferIdprimary+"=?",new String[]{id});
        return true;
    }
    public boolean offerExistornot(){

        Cursor mCursor = sqdb.rawQuery("SELECT * FROM " + DatabaseCinewhoop.TABLENAMEOffer, null);
        if (mCursor.getCount() ==0){
            return false;
        }else {
            return true;
        }
    }
}
