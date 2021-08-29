package com.example.expenses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class FuelHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<String> fuel_id,fuel_time,fuel_money,fuel_liter,fuel_km;
    DataBaseHelperFuel myBD;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        recyclerView = findViewById(R.id.recycle_view);

        myBD = new DataBaseHelperFuel(FuelHistoryActivity.this);
        fuel_id = new ArrayList<>();
        fuel_time = new ArrayList<>();
        fuel_money = new ArrayList<>();
        fuel_liter = new ArrayList<>();
        fuel_km = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(this,FuelHistoryActivity.this,fuel_id,fuel_time,fuel_money,fuel_liter,fuel_km);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FuelHistoryActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            DataBaseHelperFuel myDB = new DataBaseHelperFuel(FuelHistoryActivity.this);
            myDB.removeAllData();

            //way to update screen beautifully
            Intent intent = new Intent(this,FuelHistoryActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(this, "Delete all", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    void storeDataInArrays(){
        Cursor cursor = myBD.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }else {
            findViewById(R.id.imageView_empty).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView_empty).setVisibility(View.INVISIBLE);
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