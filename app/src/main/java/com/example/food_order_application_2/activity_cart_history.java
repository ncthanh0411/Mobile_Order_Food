package com.example.food_order_application_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.food_order_application_2.Adapter.CustomAdapter;
import com.example.food_order_application_2.Adapter.custom_adapter_cart_history;
import com.example.food_order_application_2.Model.Cart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class activity_cart_history extends AppCompatActivity {

    RecyclerView recyclerView;
    custom_adapter_cart_history adapter;
    ArrayList<Cart> data;
    DatabaseReference databaseReference;
    FloatingActionButton btn_back;
    ArrayList<String> id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_history);

        recyclerView = findViewById(R.id.recycler_view_history);
        btn_back = findViewById(R.id.btnBack);

        data = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String keys = dataSnapshot.getKey();
                id.add(keys);
                Log.d("id", dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(activity_cart_history.this, LinearLayoutManager.VERTICAL,false));
        adapter = new custom_adapter_cart_history(activity_cart_history.this, data, id);
        recyclerView.setAdapter(adapter);
    }
}