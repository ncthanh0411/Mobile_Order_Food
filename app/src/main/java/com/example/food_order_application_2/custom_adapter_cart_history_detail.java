package com.example.food_order_application_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application_2.Adapter.custom_adapter_cart_history;
import com.example.food_order_application_2.Model.FoodHistory;
import com.example.food_order_application_2.Model.Order;

import java.util.ArrayList;

class custom_adapter_cart_history_detail extends RecyclerView.Adapter<custom_adapter_cart_history_detail.myViewHolder>{
    private Context context;
    private ArrayList<FoodHistory> data;

    public custom_adapter_cart_history_detail(Context context, ArrayList<FoodHistory> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_cart_history_detail, parent, false);
        return new custom_adapter_cart_history_detail.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.name.setText(data.get(position).getProductId());
        holder.quantity.setText("Quantity: " + data.get(position).getQuantity());
        holder.price.setText("Price: " +data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            quantity = itemView.findViewById(R.id.tv_quantity);
            price = itemView.findViewById(R.id.tv_price);

        }
    }
}
