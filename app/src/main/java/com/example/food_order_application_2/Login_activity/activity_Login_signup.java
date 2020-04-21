package com.example.food_order_application_2.Login_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class activity_Login_signup extends AppCompatActivity {

    Button btnSignUp;
    TextInputEditText phone,mail, name;
    FirebaseAuth mAuthentication;
    ProgressDialog progressDialog;
    CountryCodePicker ccp;
    User account;
    DatabaseReference mData;
    String checkPhone;
    ProgressBar progressBar;
    FloatingActionButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuthentication = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBack);
        phone =  findViewById(R.id.signupPhoneEt);
        mail = findViewById(R.id.signup_mailEt);
        name = findViewById(R.id.signup_nameEt);
        ccp = findViewById(R.id.ccpRegister);
        progressBar = findViewById(R.id.progressBarSignUp);

        progressDialog = new ProgressDialog(activity_Login_signup.this);
        progressDialog.setMessage("Registering user...");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String number = phone.getText().toString();

                //check if mail or password
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mail.setError("Invalid email");
                    mail.setFocusable(true);
                }

                else if(phone.length() < 9) {
                    phone.setError("Invalid phone number");
                    phone.setFocusable(true);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    final String phoneNumber =  "+" + ccp.getFullNumber() + number;
                    mData = FirebaseDatabase.getInstance().getReference("User");

                    mData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            checkPhone = null;
                            for(DataSnapshot data: dataSnapshot.getChildren())
                            {

                                if(data.child("phone").getValue(String.class).equals(phoneNumber))
                                {
                                    checkPhone = phoneNumber;

                                }

                            }
                            if(checkPhone != null) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(activity_Login_signup.this, "This phone has already been registered", Toast.LENGTH_LONG).show();
                            }
                            else {
                                account = new User(name.getText().toString(),mail.getText().toString(),phoneNumber);
                                Intent intent = new Intent(activity_Login_signup.this,activity_VerifyPhone.class);
                                intent.putExtra("phoneNumberSignup",phoneNumber);
                                intent.putExtra("user", account);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });


    }
}
