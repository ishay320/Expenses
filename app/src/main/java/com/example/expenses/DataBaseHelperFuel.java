package com.example.expenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DataBaseHelperFuel extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Expenses.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "fuel";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIME = "fuel_time";
    private static final String COLUMN_MONEY = "fuel_money";
    private static final String COLUMN_LITER = "fuel_liter";
    private static final String COLUMN_KM = "fuel_km";

    public DataBaseHelperFuel(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TIME + " DATETIME, " +
                        COLUMN_MONEY + " REAL, " +
                        COLUMN_LITER + " REAL, " +
                        COLUMN_KM + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addFuel(String time, double money, double liter, double km){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_MONEY, money);
        cv.put(COLUMN_LITER, liter);
        cv.put(COLUMN_KM, km);
        long result = db.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added Successfully!",Toast.LENGTH_SHORT).show();
        }

    }
    void removeAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " +TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id,String time, double money, double liter, double km){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MONEY,money);
        cv.put(COLUMN_KM,km);
        cv.put(COLUMN_LITER,liter);
        cv.put(COLUMN_TIME,time);
        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();

        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
