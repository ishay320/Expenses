package com.i320.expenses.fuel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.expenses.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelCalculatorActivity extends AppCompatActivity {

    private Button button_save, button_show_history;
    private EditText edit_text_money, edit_text_liters, edit_text_KM;
    private TextView liters_in_hundred_KM, price_of_liter, KM_per_liter;
    private Spinner drop_down_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_calculator);

        //connect the text to show the sum
        liters_in_hundred_KM = findViewById(R.id.LiterIn100);
        price_of_liter = findViewById(R.id.PriceOfLiter);
        KM_per_liter = findViewById(R.id.KMPerLiter);

        //connect the input
        edit_text_money = findViewById(R.id.TextMoney);
        edit_text_liters = findViewById(R.id.TextLiters);
        edit_text_KM = findViewById(R.id.TextKM);
        drop_down_car = findViewById(R.id.spinner_car_select);

        //apply listener for updating all text from input
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

//        //set the save button
//        button_save = findViewById(R.id.button_save);
//        button_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelCalculatorActivity.this);
//
//                java.util.Date date = new Date();
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String format = formatter.format(date);
//
//                myDB.addRow(format,
//                        getDoubleFromEditText(edit_text_money),
//                        getDoubleFromEditText(edit_text_liters),
//                        getDoubleFromEditText(edit_text_KM));
//            }
//        });

        //history button
        button_show_history = findViewById(R.id.button_show_history);
        button_show_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_activity(FuelHistoryActivity.class);
            }
        });


////drop down menu
        Spinner dropdown = findViewById(R.id.spinner_car_select);
//create a list of items for the spinner.
        String[] items = new String[]{"example car 1", "example car 2", "example car 3"};
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        String name = sharedPreferences.getString("car_number", "");
        items[0] = name;
        name = sharedPreferences.getString("car_number2", "");
        items[1] = name;
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    //connect the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_calculator, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //gives the options menu the functions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            FuelDataBaseHelper myDB = new FuelDataBaseHelper(FuelCalculatorActivity.this);

            java.util.Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(date);

            myDB.addRow(format,
                    getDoubleFromEditText(edit_text_money),
                    getDoubleFromEditText(edit_text_liters),
                    getDoubleFromEditText(edit_text_KM),
                    drop_down_car.getSelectedItem().toString());

        }else if (item.getItemId() == R.id.setting){
            //go to setting
            start_activity(FuelSettingsActivity.class);
        }
        return super.onOptionsItemSelected(item);
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

}
