package com.example.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button_calculate, button_save;
    private EditText edit_text_money, edit_text_liters, edit_text_KM;
    private TextView liters_in_hundred_KM, price_of_liter, KM_per_liter;
    EditText test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attach_events();
    }

    private void attach_events(){
        liters_in_hundred_KM = findViewById(R.id.LiterIn100);
        price_of_liter = findViewById(R.id.PriceOfLiter);
        KM_per_liter = findViewById(R.id.KMPerLiter);
        edit_text_money = ((EditText)findViewById(R.id.TextMoney));
        edit_text_liters = ((EditText)findViewById(R.id.TextLiters));
        edit_text_KM = ((EditText)findViewById(R.id.TextKM));
        //apply listener for all text
        EditText edit_arr[] = {edit_text_money, edit_text_liters, edit_text_KM};
        for (EditText edit_tmp:edit_arr
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
    }
    void refreshText(){
        double money = getDoubleFromEditText(edit_text_money);
        double liters = getDoubleFromEditText(edit_text_liters);
        double km = getDoubleFromEditText(edit_text_KM);

        double price = liters!= 0 ? money/liters : 0;
        double km_per_liter_number = liters!= 0 ? km/liters : 0;
        double liter_price_in_hundred = km_per_liter_number!= 0 ? 100/km_per_liter_number : 0;

        //setting the numbers in the screen
        price_of_liter.setText(String.format("price of liter: %.2f", price));
        KM_per_liter.setText(String.format("1 liter is %.2f KM", km_per_liter_number));
        liters_in_hundred_KM.setText(String.format("100 KM is %.2f liter", liter_price_in_hundred));
    }

    /**
     * get double from EditText
     * @param text
     * @return the number and 0 if nothing inside
     */
    double getDoubleFromEditText(EditText text){
        String tmp_str = text.getText().toString();
        return Double.parseDouble(tmp_str.equals("") ? "0":tmp_str);
    }
}
//                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
