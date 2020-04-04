package com.example.food_order_application_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application_2.Model.Cart;

import java.util.ArrayList;

public class CustomAdpater_Cart extends RecyclerView.Adapter<CustomAdpater_Cart.MyViewHolder> {

    ArrayList<Cart> data;
    Context context;

    public CustomAdpater_Cart(ArrayList<Cart> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_cart, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_quan.setText(data.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_price, tv_quan;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.textView_product_name);
            tv_price = itemView.findViewById(R.id.textView_price);
            tv_quan = itemView.findViewById(R.id.textView_quan);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}