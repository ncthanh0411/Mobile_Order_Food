package com.example.food_order_application_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.food_order_application_2.Model.Cart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_cart_detail extends AppCompatActivity {

    Button btn_order;
    FloatingActionButton btn_back;

    ArrayList<Cart> data;
    RecyclerView recyclerView;
    CustomAdpater_Cart adapter_cart;

    DatabaseReference mData;
    ArrayList<String> key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        btn_back = findViewById(R.id.btnBack);
        btn_order = findViewById(R.id.btn_order);
        recyclerView = findViewById(R.id.recyclerView);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ////////
        recyclerView.setHasFixedSize(true);
        //Receive data from activity_menu
        Intent intent =getIntent();
        data = intent.getParcelableArrayListExtra("result");


        mData = FirebaseDatabase.getInstance().getReference("User");

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /////////////
        recyclerView.setLayoutManager(new LinearLayoutManager(
                activity_cart_detail.this,
                LinearLayoutManager.VERTICAL,
                false));
        adapter_cart = new CustomAdpater_Cart(data, activity_cart_detail.this);
        recyclerView.setAdapter(adapter_cart);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity_cart_detail.this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
