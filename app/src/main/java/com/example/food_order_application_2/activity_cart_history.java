package com.example.food_order_application_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.activity_cart_history_detail;
import com.example.food_order_application_2.Adapter.custom_adapter_cart_history;
import com.example.food_order_application_2.Menu.activity_food_detail_demo;
import com.example.food_order_application_2.Menu.activity_menu;
import com.example.food_order_application_2.Model.Cart;
import com.example.food_order_application_2.Model.FoodHistory;
import com.example.food_order_application_2.Model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class activity_cart_history extends AppCompatActivity {

    RecyclerView recyclerView;
    custom_adapter_cart_history adapter;

    String user_ID;

    DatabaseReference databaseReference;

    ArrayList<Order> data;
    ArrayList<FoodHistory> data_cartFood;
    ArrayList<String> id ;
    ArrayList<String> save_UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_history);

        save_UserId = getIntent().getStringArrayListExtra("saveUserID");

        //Get Current UserID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_ID = user.getUid();

        recyclerView = findViewById(R.id.recycler_view_history);
        data = new ArrayList<>();
        id = new ArrayList<>();
        data_cartFood = new ArrayList<>();



        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                //Compare if user is exists
                if(user_ID.equals(dataSnapshot.child("User ID").getValue().toString())) {
                    String keys = dataSnapshot.getKey();
                    id.add(keys);
                    for (int i = 0; i < save_UserId.size(); i++) {
                        if (dataSnapshot.hasChild(save_UserId.get(i))) {

                            String productID = dataSnapshot.child(save_UserId.get(i)).child("productId").getValue().toString();
                            String quantity = dataSnapshot.child(save_UserId.get(i)).child("quantity").getValue().toString();
                            String price = dataSnapshot.child(save_UserId.get(i)).child("price").getValue().toString();

                            FoodHistory foodList;
                            foodList = new FoodHistory(keys, productID, Integer.parseInt(quantity), Integer.parseInt(price),"");
                            data_cartFood.add(foodList);
                        }
                    }

                    Log.d("thanhToan", dataSnapshot.getValue().toString());

                    if (dataSnapshot.child("Address").getValue() != null) {
                        String address = dataSnapshot.child("Address").getValue().toString();
                        String total_price = dataSnapshot.child("Total price").getValue().toString();
                        String user_id = dataSnapshot.child("User ID").getValue().toString();
                        String date2 = dataSnapshot.child("Date").getValue().toString();

                        data.add(new Order("1", address, total_price, user_id, date2));
                    }
                }

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





        recyclerView.setLayoutManager(new LinearLayoutManager(activity_cart_history.this, LinearLayoutManager.VERTICAL,false));
        adapter = new custom_adapter_cart_history(activity_cart_history.this, data, id, data_cartFood);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new activity_menu.RecyclerItemClickListener(activity_cart_history.this, recyclerView ,new activity_menu.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(activity_cart_history.this, activity_cart_history_detail.class);
                        ArrayList<FoodHistory>data_cartFood_detail = new ArrayList<>();
                        int order_count = 0;
                        int order_temp = 0;
                        Boolean flag = false;
                        for(int i = 0; i< data_cartFood.size(); i++)
                        {
                            if(!flag) {
                                if (order_temp != Integer.parseInt(data_cartFood.get(i).getId())) {
                                    order_count += 1;
                                    order_temp = Integer.parseInt(data_cartFood.get(i).getId());
                                }
                            }
                            if(data_cartFood.get(i).getId().equals(id.get(position))){
                                data_cartFood_detail.add(data_cartFood.get(i));
                                flag = true;
                            }
                        }

                        intent.putExtra("cart_food_detail", data_cartFood_detail);
                        intent.putExtra("order_count", order_count);
                        startActivity(intent);
                        //startActivityForResult(intent,1);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }




    //REcyclerview onclick
    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private activity_menu.RecyclerItemClickListener.OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, activity_menu.RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){

        }
    }
}