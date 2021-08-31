package com.i320.expenses.fuel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expenses.R;

public class FuelUpdateActivity extends AppCompatActivity {

    //private Button button_update, button_delete;
    private EditText edit_text_money, edit_text_liters, edit_text_KM;
    private TextView liters_in_hundred_KM, price_of_liter, KM_per_liter;
    private String id, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

//        button_update = findViewById(R.id.button_update);
//        button_delete = findViewById(R.id.button_delete);
        edit_text_money = findViewById(R.id.TextMoney2);
        edit_text_liters = findViewById(R.id.TextLiters2);
        edit_text_KM = findViewById(R.id.TextKM2);
        liters_in_hundred_KM = findViewById(R.id.LiterIn1002);
        price_of_liter = findViewById(R.id.PriceOfLiter2);
        KM_per_liter = findViewById(R.id.KMPerLiter2);
        getIntentData();
        refreshText();

        EditText[] edit_arr = {edit_text_money, edit_text_liters, edit_text_KM};
        for (EditText edit_tmp : edit_arr
        ) {
            edit_tmp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    refreshText();
                }
            });
        }

//        button_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelUpdateActivity.this);
//
//                myDB.updateData(id, time,
//                        getDoubleFromEditText(edit_text_money),
//                        getDoubleFromEditText(edit_text_liters),
//                        getDoubleFromEditText(edit_text_KM));
//            }
//        });
//
//        button_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                confirmDialog();
//            }
//        });
    }

    //connect the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //gives the options menu the functions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelUpdateActivity.this);

            myDB.updateData(id, time,
                    getDoubleFromEditText(edit_text_money),
                    getDoubleFromEditText(edit_text_liters),
                    getDoubleFromEditText(edit_text_KM));

            finish();
        }else if (item.getItemId() == R.id.delete){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ask for confirmation and delete one row
     */
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("delete?");
        builder.setMessage("Are you sure you want to delete that?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelUpdateActivity.this);
                myDB.deleteRow(id);
                finish();
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
     * refresh the text on screen
     */
    void refreshText() {
        double money = getDoubleFromEditText(edit_text_money);
        double liters = getDoubleFromEditText(edit_text_liters);
        double km = getDoubleFromEditText(edit_text_KM);

        double price = liters != 0 ? money / liters : 0;
        double km_per_liter_number = liters != 0 ? km / liters : 0;
        double liter_price_in_hundred = km_per_liter_number != 0 ? 100 / km_per_liter_number : 0;

        //setting the numbers in the screen
        price_of_liter.setText(String.format("%.2f", price));
        KM_per_liter.setText(String.format("%.2f", km_per_liter_number));
        liters_in_hundred_KM.setText(String.format("%.2f", liter_price_in_hundred));
    }

    /**
     * get double from EditText
     *
     * @param text
     * @return the number and 0 if nothing inside
     */
    double getDoubleFromEditText(EditText text) {
        String tmp_str = text.getText().toString();
        return Double.parseDouble(tmp_str.equals("") ? "0" : tmp_str);
    }


    void start_activity(Class<?> next_activity) {
        Intent intent = new Intent(this, next_activity);
        startActivity(intent);
    }

    /**
     * get the data from parent
     */
    void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("money") &&
                getIntent().hasExtra("liter") && getIntent().hasExtra("km") &&
                getIntent().hasExtra("time")) {
            id = getIntent().getStringExtra("id");
            time = getIntent().getStringExtra("time");
            edit_text_money.setText(getIntent().getStringExtra("money"));
            edit_text_liters.setText(getIntent().getStringExtra("liter"));
            edit_text_KM.setText(getIntent().getStringExtra("km"));
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }
}