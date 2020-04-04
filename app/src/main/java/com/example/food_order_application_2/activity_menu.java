package com.example.food_order_application_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application_2.Model.Cart;
import com.example.food_order_application_2.Model.food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class activity_menu extends Fragment {

    private ArrayList<food> data;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private DatabaseReference mData;
    private ArrayList<String> key;
    private Button btn_cart;
    private Cart order;
    ArrayList<Cart> save_order;
    Integer total = 0;
    Integer totalPrice = 0;
    Boolean button_appear = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  view  = inflater.inflate(R.layout.activity_menufood, container, false);
        key = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        initView();
        //btn cart
        btn_cart = view.findViewById(R.id.btn_cart);

        //Set button cart visible, button_appear is used to check if btn_cart appear or not
        if(button_appear == false) {
            btn_cart.setVisibility(View.GONE);
        }

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), activity_cart_detail.class);
                startActivity(intent2);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            DatabaseReference foods = FirebaseDatabase.getInstance().getReference("food_menu");
            // button cart appear
            btn_cart.setVisibility(View.VISIBLE);
            button_appear = true;

            save_order = new ArrayList<>();
            order = (Cart) data.getSerializableExtra("result");

            foods.child(order.getProductId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    food food = dataSnapshot.getValue((food.class));

                    totalPrice = food.getPrice()*order.getQuantity();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            total += order.getQuantity();
            save_order.add(new Cart(order.getProductId(),order.getQuantity()));
            btn_cart.setText("Total Foods: "+ total + ", Total Price:" + totalPrice +"$");


        }
    }

    private void initView() {

        recyclerView.setHasFixedSize(true);
        data = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("food_menu");

        //pushdata to firebase
        //mData.push().setValue(new food("Fried Chicken","this is fried chicken assasdasd",200,"anh"));

        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Add Id to key

                String keys = dataSnapshot.getKey();
                Log.d("qweasd",dataSnapshot.getKey());
                key.add(keys);
                //retrieve value from firebase
                food food_menu = dataSnapshot.getValue(food.class);
                data.add(food_menu);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        adapter = new CustomAdapter(data,getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(getActivity(), activity_food_detail_demo.class);
                        intent.putExtra("FoodID", key.get(position));
                        //startActivity(intent);
                        startActivityForResult(intent,1);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
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
