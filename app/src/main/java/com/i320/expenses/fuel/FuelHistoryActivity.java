package com.i320.expenses.fuel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenses.R;

import java.util.ArrayList;

public class FuelHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> fuel_id, fuel_time, fuel_money, fuel_liter, fuel_km;
    FuelDataBaseHelper myDB;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        //what show on the screen
        recyclerView = findViewById(R.id.recycle_view);

        //the DB and the lists that hold the DB for the adapter
        myDB = new FuelDataBaseHelper(FuelHistoryActivity.this);
        fuel_id = new ArrayList<>();
        fuel_time = new ArrayList<>();
        fuel_money = new ArrayList<>();
        fuel_liter = new ArrayList<>();
        fuel_km = new ArrayList<>();

        //pull the BD to the arrays
        storeDataInArrays();

        //the adapter to the recycle view
        customAdapter = new CustomAdapter(this, FuelHistoryActivity.this, fuel_id, fuel_time, fuel_money, fuel_liter, fuel_km);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FuelHistoryActivity.this));

    }

    //recreate the screen when go back from update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    //connect the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //gives the options menu the functions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog(this);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ask for confirmation and delete one row
     */
    void confirmDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("delete all?");
        builder.setMessage("Are you sure you want to delete that?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelHistoryActivity.this);
                myDB.removeAllData();

                //way to update screen beautifully
                Intent intent = new Intent(context, FuelHistoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(context, "all deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    /**
     * read from the array and show the picture if empty
     */
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() != 0) {//check if DB is empty
            findViewById(R.id.imageView_empty).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView_empty).setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()) {//read the data from DB
                fuel_id.add(cursor.getString(0));
                fuel_time.add(cursor.getString(1));
                fuel_money.add(cursor.getString(2));
                fuel_liter.add(cursor.getString(3));
                fuel_km.add(cursor.getString(4));
            }
        }
    }

}