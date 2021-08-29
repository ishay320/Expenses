package com.example.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> fuel_id,fuel_time,fuel_money,fuel_liter,fuel_km;

    CustomAdapter(Context context,
                  ArrayList fuel_id,
                  ArrayList fuel_time,
                  ArrayList fuel_money,
                  ArrayList fuel_liter,
                  ArrayList fuel_km ){

        this.context = context;
        this.fuel_id = fuel_id;
        this.fuel_time = fuel_time;
        this.fuel_money = fuel_money;
        this.fuel_liter = fuel_liter;
        this.fuel_km = fuel_km;

    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.fuel_id_text.setText(String.valueOf(fuel_id.get(position)));
        holder.fuel_time_text.setText(String.valueOf(fuel_time.get(position)));
        holder.fuel_money_text.setText(String.valueOf(fuel_money.get(position)));
        holder.fuel_liter_text.setText(String.valueOf(fuel_liter.get(position)));
        holder.fuel_km_text.setText(String.valueOf(fuel_km.get(position)));
    }

    @Override
    public int getItemCount() {
        return fuel_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fuel_id_text,fuel_time_text,fuel_money_text,fuel_liter_text,fuel_km_text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fuel_id_text = itemView.findViewById(R.id.fuel_id);
            fuel_time_text = itemView.findViewById(R.id.textView_time);
            fuel_money_text = itemView.findViewById(R.id.textView_money);
            fuel_liter_text = itemView.findViewById(R.id.textView_liter);
            fuel_km_text = itemView.findViewById(R.id.textView_km);
        }
    }
}
