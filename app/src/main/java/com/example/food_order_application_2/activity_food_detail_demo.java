package com.example.food_order_application_2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class activity_food_detail_demo extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btn_cart;
    ElegantNumberButton numberButton;

    String foodID = "";

    //FirebaseDatabase database;
    //DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_demo);

        //Firebase
        //database = FirebaseDatabase.getInstance();
        //foods = database.getReference("Foods");

        numberButton = findViewById(R.id.number_button);
        btn_cart = findViewById(R.id.btnCart);

        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        if (getIntent() != null) {
            foodID = getIntent().getStringExtra("FoodID");
        }
        if (foodID.isEmpty()) {
            getDetailFood(foodID);
        }
    }

    private void getDetailFood(String foodID) {
//        foods.child(foodID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Food food = dataSnapshot.getValue((Food.class));
//
//                Picasso.get().load(food.getImage()).into(food_image);
//
//                collapsingToolbarLayout.setTitle(food.getName());
//
//                food_price.setText(food.getPrice());
//
//                food_name.setText(food.getName());
//
//                food_description.setText(food.getDescription());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
