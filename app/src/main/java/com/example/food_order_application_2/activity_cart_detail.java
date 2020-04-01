package com.example.food_order_application_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_cart_detail extends AppCompatActivity {

    Button btn_order;
    FloatingActionButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        btn_back = findViewById(R.id.btnBack);
        btn_order = findViewById(R.id.btn_order);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_cart_detail.this, activity_menu.class);
                startActivity(intent);
            }
        });
    }
}
