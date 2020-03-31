package com.example.food_order_application_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_food_detail extends AppCompatActivity {

    ImageView imageView;
    TextView textView_food_name, textView_food_description, textView_price_value, textView_amount_value;
    EditText editText_note;
    Button btn_minus, btn_plus;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        findView();

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 10) {
                    count+=1;
                    textView_amount_value.setText(String.valueOf(count));
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 1) {
                    count-=1;
                    textView_amount_value.setText(String.valueOf(count));
                }
            }
        });
    }

    public void findView() {
        imageView = findViewById(R.id.image_item);
        textView_food_name = findViewById(R.id.textView_food_name);
        textView_food_description = findViewById(R.id.textView_food_description);
        textView_price_value = findViewById(R.id.textView_price_value);
        textView_amount_value = findViewById(R.id.textView_amount_value);
        editText_note = findViewById(R.id.editText_note);
        btn_minus = findViewById(R.id.button_minus);
        btn_plus = findViewById(R.id.button_plus);
    }
}
