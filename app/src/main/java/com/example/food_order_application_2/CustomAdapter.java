package com.example.food_order_application_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application_2.Model.food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    ArrayList<food> data;
    Context context;


    public CustomAdapter(ArrayList<food> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_menu,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String link = data.get(position).getImg();
        holder.name.setText(data.get(position).getName());
        holder.price.setText(data.get(position).getPrice() + "$");
        Picasso.get().load(link).resizeDimen(R.dimen.image_size, R.dimen.image_size).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price,texTv;
        LinearLayout linearLayout;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameProduct);
            price = itemView.findViewById(R.id.priceProduct);
            imageView = itemView.findViewById(R.id.imageViewFood);
            //linearLayout = itemView.findViewById(R.id.clickLayout);
           // btnNumberCount = itemView.findViewById(R.id.number_counter);
        }
    }
}
