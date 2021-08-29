package com.i320.expenses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expenses.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelActivity extends AppCompatActivity {

    private Button button_save, button_show_history;
    private EditText edit_text_money, edit_text_liters, edit_text_KM;
    private TextView liters_in_hundred_KM, price_of_liter, KM_per_liter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        //connect the text to show the sum
        liters_in_hundred_KM = findViewById(R.id.LiterIn100);
        price_of_liter = findViewById(R.id.PriceOfLiter);
        KM_per_liter = findViewById(R.id.KMPerLiter);

        //connect the input
        edit_text_money = findViewById(R.id.TextMoney);
        edit_text_liters = findViewById(R.id.TextLiters);
        edit_text_KM = findViewById(R.id.TextKM);

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

        //set the save button
        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelperFuel myDB = new DataBaseHelperFuel(FuelActivity.this);

                java.util.Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = formatter.format(date);

                myDB.addRow(format,
                        getDoubleFromEditText(edit_text_money),
                        getDoubleFromEditText(edit_text_liters),
                        getDoubleFromEditText(edit_text_KM));
            }
        });

        //history button
        button_show_history = findViewById(R.id.button_show_history);
        button_show_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_activity(FuelHistoryActivity.class);
            }
        });
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
