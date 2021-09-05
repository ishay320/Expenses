package com.i320.expenses.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ProductsDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Expenses.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_SN = "serial_number";
    private static final String COLUMN_STORE_NAME = "store_name";
    private static final String COLUMN_WEIGHT = "weight";

    private final Context context;

    public ProductsDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TIME + " DATETIME, " +
                        COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                        COLUMN_PRICE + " DECIMAL NOT NULL, " +
                        COLUMN_SN + " INT, " +
                        COLUMN_STORE_NAME + " TEXT, " +
                        COLUMN_WEIGHT + " DECIMAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRow(String time, String product_name, double price, long serial_number,String store_name,double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_SN, serial_number);
        cv.put(COLUMN_STORE_NAME, store_name);
        cv.put(COLUMN_WEIGHT, weight);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void removeAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id,String time, String product_name, double price, long serial_number,String store_name,double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PRODUCT_NAME, product_name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_SN, serial_number);
        cv.put(COLUMN_STORE_NAME, store_name);
        cv.put(COLUMN_WEIGHT, weight);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        }
    }
}