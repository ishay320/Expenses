package com.i320.expenses.shopping;

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
import com.i320.expenses.fuel.FuelUpdateActivity;

import java.util.ArrayList;

public class ProductsCustomAdapter extends RecyclerView.Adapter<ProductsCustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> product_id;
    private final ArrayList<String> product_time;
    private final ArrayList<String> product_name;
    private final ArrayList<String> product_price;
    private final ArrayList<String> product_serial_number;
    private final ArrayList<String> product_store_name;
    private final ArrayList<String> product_weight;


    ProductsCustomAdapter(Activity activity, Context context,
                          ArrayList product_id,
                          ArrayList product_time,
                          ArrayList product_name,
                          ArrayList product_price,
                          ArrayList product_serial_number,
                          ArrayList product_store_name,
                          ArrayList product_weight) {

        this.activity = activity;
        this.context = context;
        this.product_id = product_id;
        this.product_time = product_time;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_serial_number = product_serial_number;
        this.product_store_name = product_store_name;
        this.product_weight = product_weight;

    }

    @NonNull
    @Override
    public ProductsCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.products_my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsCustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.product_id_text.setText(String.valueOf(product_id.get(position)));
        holder.product_time_text.setText(String.valueOf(product_time.get(position)));
        holder.product_name_text.setText(String.valueOf(product_name.get(position)));
        holder.product_price_text.setText(String.valueOf(product_price.get(position)));
        holder.product_serial_number_text.setText(String.valueOf(product_serial_number.get(position)));
        holder.product_store_name_text.setText(String.valueOf(product_store_name.get(position)));
        holder.product_weight_text.setText(String.valueOf(product_weight.get(position)));
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, FuelUpdateActivity.class);
//                intent.putExtra("id", String.valueOf(product_id.get(position)));
//                intent.putExtra("money", String.valueOf(product_name.get(position)));
//                intent.putExtra("liter", String.valueOf(product_price.get(position)));
//                intent.putExtra("km", String.valueOf(product_serial_number.get(position)));
//                intent.putExtra("time", String.valueOf(product_time.get(position)));
//                intent.putExtra("car_number",String.valueOf(product_store_name.get(position)));
//                intent
//                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_id_text, product_time_text, product_name_text, product_price_text, product_serial_number_text, product_store_name_text,product_weight_text;
        LinearLayout main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_text = itemView.findViewById(R.id.textView_id);
            product_time_text = itemView.findViewById(R.id.textView_time_product);
            product_name_text = itemView.findViewById(R.id.textView_product_name);
            product_price_text = itemView.findViewById(R.id.textView_price);
            product_serial_number_text = itemView.findViewById(R.id.textView_sn);
            product_store_name_text = itemView.findViewById(R.id.textview_store_name);
            product_weight_text = itemView.findViewById(R.id.textview_weight);
            main_layout = itemView.findViewById(R.id.product_row_select);
        }
    }
}
