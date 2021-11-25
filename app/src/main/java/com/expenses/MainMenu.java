package com.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expenses.R;
import com.expenses.fuel.FuelCalculatorActivity;
import com.expenses.shopping.ProductsManualInput;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {
    Button button_fuel_calculation, button_manual_input, button_camera_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "logged in", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "not logged in", Toast.LENGTH_LONG).show();
            // No user is signed in
        }

        button_fuel_calculation = findViewById(R.id.fuel_calculation);
        button_manual_input = findViewById(R.id.manual_input);
        button_camera_input = findViewById(R.id.camera_input);

        button_fuel_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_activity(FuelCalculatorActivity.class);
            }
        });

        button_manual_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_activity(ProductsManualInput.class);
            }
        });
    }


    void start_activity(Class<?> next_activity) {
        Intent intent = new Intent(this, next_activity);
        startActivity(intent);
    }
}