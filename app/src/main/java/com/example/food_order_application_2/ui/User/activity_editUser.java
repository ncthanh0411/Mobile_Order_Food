package com.example.food_order_application_2.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_order_application_2.Adapter.CustomAdapter;
import com.example.food_order_application_2.Menu.activity_food_detail_demo;
import com.example.food_order_application_2.Menu.activity_menu;
import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.Model.food;
import com.example.food_order_application_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class activity_editUser extends AppCompatActivity {
    private FirebaseAuth mAuthentication;
    DatabaseReference account;
    EditText name, mail;
    Button btnSave;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        mAuthentication = FirebaseAuth.getInstance();
        name = findViewById(R.id.NameEdit);
        mail = findViewById(R.id.MailEdit);
        btnSave = findViewById(R.id.btnSave);
        loadUserInformation();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               account.child(user.getUid()).child("name").setValue(name.getText().toString());
               account.child(user.getUid()).child("email").setValue(mail.getText().toString());
               Toast.makeText(activity_editUser.this, "Update sucessfull", Toast.LENGTH_LONG).show();
               finish();
            }
        });
    }

    private void loadUserInformation() {
        user = mAuthentication.getCurrentUser();
        account = FirebaseDatabase.getInstance().getReference("User") ;
        account.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue((User.class));
                name.setText(user.getName());
                mail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
