package com.example.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FuelHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<String> fuel_id,fuel_time,fuel_money,fuel_liter,fuel_km;
    DataBaseHelper myBD;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        recyclerView = findViewById(R.id.recycle_view);

        myBD = new DataBaseHelper(FuelHistoryActivity.this);
        fuel_id = new ArrayList<>();
        fuel_time = new ArrayList<>();
        fuel_money = new ArrayList<>();
        fuel_liter = new ArrayList<>();
        fuel_km = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(FuelHistoryActivity.this,fuel_id,fuel_time,fuel_money,fuel_liter,fuel_km);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FuelHistoryActivity.this));

    }

    void storeDataInArrays(){
        Cursor cursor = myBD.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                fuel_id.add(cursor.getString(0));
                fuel_time.add(cursor.getString(1));
                fuel_money.add(cursor.getString(2));
                fuel_liter.add(cursor.getString(3));
                fuel_km.add(cursor.getString(4));
            }

        }
    }

}