package com.expenses.shopping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expenses.R;
import com.expenses.database.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductsManualInput extends AppCompatActivity {

    Button button_save, button_history;
    EditText editText_product_name,editText_price,editText_serial_number,editText_store_name,editText_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        //connect the elements to xml
        editText_product_name = findViewById(R.id.autoCompleteTextView_product_name);
        editText_price = findViewById(R.id.editTextNumberDecimal_price);
        editText_serial_number = findViewById(R.id.editTextNumberSigned_serial_number);
        editText_store_name = findViewById(R.id.autoCompleteTextView_store_name);
        editText_weight = findViewById(R.id.editTextNumberDecimal_weight);

        button_save = findViewById(R.id.button_save_product);
        button_history = findViewById(R.id.button_history_products);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_name, store_name;
                double price,weight;
                long serial_number;
                product_name = editText_product_name.getText().toString();
                price = getDoubleFromEditText(editText_price);
                serial_number = getLongFromEditText(editText_serial_number);
                store_name = editText_store_name.getText().toString();
                weight = getDoubleFromEditText(editText_weight);

                DataBaseHelper myDB = new DataBaseHelper(ProductsManualInput.this);

                java.util.Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = formatter.format(date);

                myDB.addProductRow(format,
                        product_name,
                        price,
                        serial_number,
                        store_name,
                        weight);

            }
        });
        button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsManualInput.this, ProductsHistoryActivity.class);
                startActivity(intent);
            }
        });
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

    /**
     * get long from EditText
     *
     * @param text
     * @return the number and 0 if nothing inside
     */
    long getLongFromEditText(EditText text) {
        String tmp_str = text.getText().toString();
        return Long.parseLong(tmp_str.equals("") ? "0" : tmp_str);
    }


}