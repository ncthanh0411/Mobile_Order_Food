package com.example.food_order_application_2.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application_2.Adapter.CustomAdpater_Cart;
import com.example.food_order_application_2.Model.Cart;
import com.example.food_order_application_2.R;
import com.example.food_order_application_2.onItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_cart_detail extends AppCompatActivity implements onItemClick {

    Button btn_order;
    FloatingActionButton btn_back;
    TextView textView_total;
    EditText ed_address;

    ArrayList<Cart> data;
    RecyclerView recyclerView;
    CustomAdpater_Cart adapter_cart;

    DatabaseReference mData, mData2;
    int count_ID;
    int sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        btn_back = findViewById(R.id.btnBack);
        btn_order = findViewById(R.id.btn_order);
        ed_address = findViewById(R.id.editText_address);
        recyclerView = findViewById(R.id.recyclerView);
        textView_total = findViewById(R.id.textView_total);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent();
                intent_back.putExtra("value_update", data);
                setResult(RESULT_OK,intent_back);
                finish();
            }
        });

        ////////
        recyclerView.setHasFixedSize(true);
        //Receive data from activity_menu
        Intent intent = getIntent();
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
        adapter_cart = new CustomAdpater_Cart(data, activity_cart_detail.this, this);
        recyclerView.setAdapter(adapter_cart);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity_cart_detail.this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //onClick(data);
        for (int i = 0; i <= data.size() - 1; i++) {
            sum += data.get(i).getPrice() * data.get(i).getQuantity();
        }
        textView_total.setText("Total price: " + sum + "$ ");

        //set button state through address
        ed_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = ed_address.getText().toString().trim();

                btn_order.setEnabled(!address.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //set button state through list of items
        if (data.isEmpty()) {
            Toast.makeText(activity_cart_detail.this, "Please add at least 1 item.", Toast.LENGTH_SHORT).show();
            btn_order.setEnabled(false);
        }

        // send data to firebase
        mData2 = FirebaseDatabase.getInstance().getReference().child("Order");

        mData2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    count_ID = (int) dataSnapshot.getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = ed_address.getText().toString().trim();
                int total_price = sum;

                //Add order to firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mData2.child(String.valueOf(count_ID +1)).child("User ID").setValue(user.getUid());
                mData2.child(String.valueOf(count_ID +1)).child("Address").setValue(address);
                mData2.child(String.valueOf(count_ID +1)).child("Total price").setValue(total_price);


                for (int i = 0; i <= data.size() -1 ; i++) {
                    Cart order = data.get(i);
                    mData2.child(String.valueOf(count_ID +1)).child(order.getProductId()).setValue(order);
                }

                Toast.makeText(activity_cart_detail.this, "Order has been sent", Toast.LENGTH_SHORT).show();
                data.clear();
                Intent replyIntent = new Intent();
                replyIntent.putExtra("cart", data);
                setResult(RESULT_OK,replyIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(ArrayList<Cart> data) {
        sum = 0;
        for (int i = 0; i <= data.size() - 1; i++) {
            sum += data.get(i).getPrice() * data.get(i).getQuantity();
        }
        textView_total.setText("Total price: " + sum + "$ ");
        adapter_cart.notifyDataSetChanged();
    }
}
