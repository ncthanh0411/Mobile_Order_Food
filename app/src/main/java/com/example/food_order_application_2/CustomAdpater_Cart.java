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
import com.example.food_order_application_2.Model.food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        DatabaseReference foods = FirebaseDatabase.getInstance().getReference("food_menu");
        holder.tv_quan.setText("Quantity: " + data.get(position).getQuantity() + "");
        foods.child(data.get(position).getProductId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                food Food = dataSnapshot.getValue(food.class);
                holder.tv_name.setText(Food.getName());
                holder.tv_price.setText("Price: " + Food.getPrice() +"$");

                String link = Food.getImg();
                Picasso.get().load(link).resizeDimen(R.dimen.image_size, R.dimen.image_size).into(holder.imageView);
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