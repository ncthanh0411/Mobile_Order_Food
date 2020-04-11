package com.example.food_order_application_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application_2.Model.Cart;
import com.example.food_order_application_2.Model.food;
import com.example.food_order_application_2.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class custom_adapter_cart_history extends RecyclerView.Adapter<custom_adapter_cart_history.myViewHolder> {

    private Context context;
    ArrayList<Cart> data;

    public custom_adapter_cart_history(Context context, ArrayList<Cart> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_cart_history, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.quantity.setText(data.get(position).getQuantity());
        final DatabaseReference foods = FirebaseDatabase.getInstance().getReference("food_menu");
        foods.child(data.get(position).getProductId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                food Food = dataSnapshot.getValue(food.class);
                holder.name.setText(Food.getName());
                holder.price.setText(Food.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView quantity, name, total, price;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.text_view_quantity);
            name = itemView.findViewById(R.id.text_view_name);
            total = itemView.findViewById(R.id.text_view_total);
            price = itemView.findViewById(R.id.text_view_price);
        }
    }
}
