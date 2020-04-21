package com.example.food_order_application_2.Login_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_order_application_2.R;
import com.example.food_order_application_2.activity_bottomNav;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class activity_Login extends AppCompatActivity {
    Button btnReceiveSMS;
    FloatingActionButton btnBack;
    TextInputEditText phone;
    Firebase mRef;
    FirebaseAuth mAuthentication;
    ProgressDialog progressDialog;
    CountryCodePicker ccp;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthentication = FirebaseAuth.getInstance();

        btnBack = findViewById(R.id.btnBack);
        btnReceiveSMS = findViewById(R.id.btnReceiveSMS);
        phone = findViewById(R.id.phoneLoginEt);
        ccp = findViewById(R.id.ccp_Login);


        progressDialog = new ProgressDialog(activity_Login.this);
        progressDialog.setMessage("Login user...");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReceiveSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = phone.getText().toString();
                if(number.isEmpty()){
                    phone.setError("Your Phone number is empty");
                    phone.requestFocus();
                }
                else if(number.length() < 9){
                    phone.setError("Invalid phone number");
                    phone.requestFocus();
                }
                else{

                    final String phoneNumber =  "+" + ccp.getFullNumber() + number;

                    mData = FirebaseDatabase.getInstance().getReference("User");
                    mData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String test = null;
                            for(DataSnapshot data: dataSnapshot.getChildren())
                            {

                                if(data.child("phone").getValue(String.class).equals(phoneNumber))
                                {
                                    test = phoneNumber;
                                    Log.d("abcde",data.child("phone").getValue(String.class));
                                    Intent intent = new Intent(activity_Login.this,activity_VerifyPhone.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    startActivity(intent);
                                }
                            }
                            if(test == null){
                                Toast.makeText(activity_Login.this, "Your phone number is incorrect, please register your phone number!",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //If user haven't have an account yet, then toast a message to display warnining
                }

            }
        });
    }


}
