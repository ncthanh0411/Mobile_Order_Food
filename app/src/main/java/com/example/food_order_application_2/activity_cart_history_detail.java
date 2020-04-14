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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application_2.Adapter.CustomAdapter;
import com.example.food_order_application_2.Menu.activity_food_detail_demo;
import com.example.food_order_application_2.Menu.activity_menu;
import com.example.food_order_application_2.Model.FoodHistory;
import com.example.food_order_application_2.Model.food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_cart_history_detail extends AppCompatActivity {
    RecyclerView recyclerView;
    custom_adapter_cart_history_detail adapter;
    ArrayList<FoodHistory> data;
    DatabaseReference mData;
    FloatingActionButton btnBack;
    TextView textView_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_history_detail);

        recyclerView = findViewById(R.id.recycler_view_history_detail);
        textView_order = findViewById(R.id.tv_order_name);
        initView();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        recyclerView.setHasFixedSize(true);
        data = new ArrayList<>();
        data = getIntent().getParcelableArrayListExtra("cart_food_detail");
        Toast.makeText(activity_cart_history_detail.this, data.size() +"",Toast.LENGTH_SHORT).show();
        mData = FirebaseDatabase.getInstance().getReference("food_menu");

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(int i =0; i< data.size(); i++) {
                    Log.d("asdaasd", dataSnapshot.child(data.get(i).getProductId()).toString());
                    String name = dataSnapshot.child(data.get(i).getProductId()).child("name").getValue().toString();
                    data.get(i).setProductId(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(
                activity_cart_history_detail.this,
                LinearLayoutManager.VERTICAL,
                false));
        adapter = new custom_adapter_cart_history_detail(activity_cart_history_detail.this, data);
        recyclerView.setAdapter(adapter);


    }
}
