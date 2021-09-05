package com.i320.expenses.fuel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenses.R;

import java.util.ArrayList;

public class FuelCustomAdapter extends RecyclerView.Adapter<FuelCustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> fuel_id;
    private final ArrayList<String> fuel_time;
    private final ArrayList<String> fuel_money;
    private final ArrayList<String> fuel_liter;
    private final ArrayList<String> fuel_km;
    private final ArrayList<String> fuel_car;


    FuelCustomAdapter(Activity activity, Context context,
                      ArrayList fuel_id,
                      ArrayList fuel_time,
                      ArrayList fuel_money,
                      ArrayList fuel_liter,
                      ArrayList fuel_km,
                      ArrayList fuel_car) {

        this.activity = activity;
        this.context = context;
        this.fuel_id = fuel_id;
        this.fuel_time = fuel_time;
        this.fuel_money = fuel_money;
        this.fuel_liter = fuel_liter;
        this.fuel_km = fuel_km;
        this.fuel_car = fuel_car;

    }

    @NonNull
    @Override
    public FuelCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelCustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.fuel_id_text.setText(String.valueOf(fuel_id.get(position)));
        holder.fuel_time_text.setText(String.valueOf(fuel_time.get(position)));
        holder.fuel_money_text.setText(String.valueOf(fuel_money.get(position)));
        holder.fuel_liter_text.setText(String.valueOf(fuel_liter.get(position)));
        holder.fuel_km_text.setText(String.valueOf(fuel_km.get(position)));
        holder.fuel_car_text.setText(String.valueOf(fuel_car.get(position)));
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FuelUpdateActivity.class);
                intent.putExtra("id", String.valueOf(fuel_id.get(position)));
                intent.putExtra("money", String.valueOf(fuel_money.get(position)));
                intent.putExtra("liter", String.valueOf(fuel_liter.get(position)));
                intent.putExtra("km", String.valueOf(fuel_km.get(position)));
                intent.putExtra("time", String.valueOf(fuel_time.get(position)));
                intent.putExtra("car_number",String.valueOf(fuel_car.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuel_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fuel_id_text, fuel_time_text, fuel_money_text, fuel_liter_text, fuel_km_text,fuel_car_text;
        LinearLayout main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fuel_id_text = itemView.findViewById(R.id.textView_id);
            fuel_time_text = itemView.findViewById(R.id.textView_time_product);
            fuel_money_text = itemView.findViewById(R.id.textView_product_name);
            fuel_liter_text = itemView.findViewById(R.id.textView_price);
            fuel_km_text = itemView.findViewById(R.id.textView_sn);
            fuel_car_text = itemView.findViewById(R.id.textview_store_name);
            main_layout = itemView.findViewById(R.id.row_select);
        }
    }
}
