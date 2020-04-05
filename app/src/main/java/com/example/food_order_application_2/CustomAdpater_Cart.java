package com.example.food_order_application_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.food_order_application_2.Model.Cart;
import com.example.food_order_application_2.Model.food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdpater_Cart extends RecyclerView.Adapter<CustomAdpater_Cart.MyViewHolder>{

    ArrayList<Cart> data;
    Context context;
    onItemClick itemclick;

    public CustomAdpater_Cart(ArrayList<Cart> data, Context context, onItemClick listener) {
        this.data = data;
        this.context = context;
        this.itemclick = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_cart, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        DatabaseReference foods = FirebaseDatabase.getInstance().getReference("food_menu");
        holder.elegantNumberButton.setNumber(String.valueOf(data.get(position).getQuantity()));
        holder.elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setQuantity(Integer.parseInt(holder.elegantNumberButton.getNumber()));
                notifyDataSetChanged();
                //Toast.makeText(context, data.get(position).getQuantity() +"", Toast.LENGTH_SHORT).show();
                if (data.get(position).getQuantity() == 0) {
                    data.remove(position);
                }
                itemclick.onClick(data);
            }
        });

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

//    public void updateData() {
//        itemclick.onClick(data);
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_price, tv_total;
        ImageView imageView;
        ElegantNumberButton elegantNumberButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.textView_product_name);
            tv_price = itemView.findViewById(R.id.textView_price);
            imageView = itemView.findViewById(R.id.imageView);
            elegantNumberButton = itemView.findViewById(R.id.number_button_cart);
        }
    }
}