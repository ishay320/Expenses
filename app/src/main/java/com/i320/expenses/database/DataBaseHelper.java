package com.i320.expenses.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Expenses.db";
    private static final int DATABASE_VERSION = 4;

    //tables
    private static final String TABLE_NAME_FUEL = "fuel";
    private static final String TABLE_NAME_PRODUCT = "products";

    //commune column
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_PRICE = "price";

    //fuel column      //maybe gas station?
    private static final String COLUMN_LITER = "fuel_liter";
    private static final String COLUMN_KM = "fuel_km";
    private static final String COLUMN_CAR_NUMBER = "car_number";

    //product column
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_SN = "serial_number";
    private static final String COLUMN_STORE_NAME = "store_name";
    private static final String COLUMN_WEIGHT = "weight";

    private final Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String fuel_create =
                "CREATE TABLE " + TABLE_NAME_FUEL +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TIME + " DATETIME, " +
                        COLUMN_PRICE + " REAL, " +
                        COLUMN_LITER + " REAL, " +
                        COLUMN_KM + " REAL, " +
                        COLUMN_CAR_NUMBER + " TEXT);";
        db.execSQL(fuel_create);

        String product_create =  "CREATE TABLE " + TABLE_NAME_PRODUCT +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " DATETIME, " +
                COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                COLUMN_PRICE + " DECIMAL NOT NULL, " +
                COLUMN_SN + " INT, " +
                COLUMN_STORE_NAME + " TEXT, " +
                COLUMN_WEIGHT + " DECIMAL);";
        db.execSQL(product_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //for upgrade from version 3
        if (db.getVersion() == 3) {
            String query = "SELECT * FROM " + TABLE_NAME_FUEL;
            Cursor cursor = db.rawQuery(query, null);
            ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
            while (cursor.moveToNext()) {//read the data from DB
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_TIME, cursor.getString(1));
                cv.put(COLUMN_PRICE, cursor.getString(2));
                cv.put(COLUMN_LITER, cursor.getString(3));
                cv.put(COLUMN_KM, cursor.getString(4));
                cv.put(COLUMN_CAR_NUMBER, cursor.getString(5));
                contentValues.add(cv);
            }
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FUEL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
            onCreate(db);
            for (ContentValues cv :
                    contentValues) {
                db.insert(TABLE_NAME_FUEL, null, cv);
            }
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FUEL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
            onCreate(db);
        }
    }

    public long addFuelRow(String time, double money, double liter, double km, String car_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PRICE, money);
        cv.put(COLUMN_LITER, liter);
        cv.put(COLUMN_KM, km);
        cv.put(COLUMN_CAR_NUMBER, car_number);

        long result = db.insert(TABLE_NAME_FUEL, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
    public long addProductRow(String time, String product_name, double price, long serial_number,String store_name,double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_SN, serial_number);
        cv.put(COLUMN_STORE_NAME, store_name);
        cv.put(COLUMN_WEIGHT, weight);

        long result = db.insert(TABLE_NAME_PRODUCT, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public void removeAllFuelData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FUEL, null, null);
    }
    public void removeAllProductData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_PRODUCT, null, null);
    }

    public Cursor readAllFuelData() {
        String query = "SELECT * FROM " + TABLE_NAME_FUEL;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllProductData() {
        String query = "SELECT * FROM " + TABLE_NAME_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public long updateFuelData(String row_id, String time, double money, double liter, double km, String car_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, money);
        cv.put(COLUMN_KM, km);
        cv.put(COLUMN_LITER, liter);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_CAR_NUMBER, car_number);
        long result = db.update(TABLE_NAME_FUEL, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public long updateProductData(String row_id,String time, String product_name, double price, long serial_number,String store_name,double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_SN, serial_number);
        cv.put(COLUMN_STORE_NAME, store_name);
        cv.put(COLUMN_WEIGHT, weight);

        long result = db.update(TABLE_NAME_PRODUCT, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public long deleteFuelRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_FUEL, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public long deleteProductRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_PRODUCT, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
