package com.i320.expenses.shopping;

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
import com.i320.expenses.database.DataBaseHelper;

import java.util.ArrayList;

public class ProductsHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> product_id, product_time, product_name, product_price, product_serial_number, product_store_name,product_weight;
    DataBaseHelper myDB;
    ProductsCustomAdapter productsCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_history); //maybe can conjoin activities

        //what show on the screen
        recyclerView = findViewById(R.id.recycle_view);

        //the DB and the lists that hold the DB for the adapter
        myDB = new DataBaseHelper(ProductsHistoryActivity.this);
        product_id = new ArrayList<>();
        product_time = new ArrayList<>();
        product_name = new ArrayList<>();
        product_price = new ArrayList<>();
        product_serial_number = new ArrayList<>();
        product_store_name = new ArrayList<>();
        product_weight = new ArrayList<>();

        //pull the BD to the arrays
        storeDataInArrays();

        //the adapter to the recycle view
        productsCustomAdapter = new ProductsCustomAdapter(this, ProductsHistoryActivity.this, product_id, product_time, product_price, product_name, product_serial_number, product_store_name,product_weight);

        recyclerView.setAdapter(productsCustomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsHistoryActivity.this));

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
        inflater.inflate(R.menu.option_menu_history, menu);
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
                DataBaseHelper myDB = new DataBaseHelper(ProductsHistoryActivity.this);
                myDB.removeAllProductData();

                //way to update screen beautifully
                Intent intent = new Intent(context, ProductsHistoryActivity.class);
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
        Cursor cursor = myDB.readAllProductData();
        if (cursor.getCount() != 0) {//check if DB is empty
            findViewById(R.id.imageView_empty).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView_empty).setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()) {//read the data from DB
                product_id.add(cursor.getString(0));
                product_time.add(cursor.getString(1));
                product_name.add(cursor.getString(2));
                product_price.add(cursor.getString(3));
                product_serial_number.add(cursor.getString(4));
                product_store_name.add(cursor.getString(5));
                product_weight.add(cursor.getString(6));
            }
        }
    }

}