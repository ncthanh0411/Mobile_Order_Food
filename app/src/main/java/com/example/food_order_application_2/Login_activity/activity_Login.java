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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class activity_Login extends AppCompatActivity {
    Button btnReceiveSMS;
    TextInputEditText phone;
    Firebase mRef;
    FirebaseAuth mAuthentication;
    ProgressDialog progressDialog;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthentication = FirebaseAuth.getInstance();


        btnReceiveSMS = findViewById(R.id.btnReceiveSMS);
        phone = findViewById(R.id.phoneLoginEt);
        ccp = findViewById(R.id.ccp_Login);

        progressDialog = new ProgressDialog(activity_Login.this);
        progressDialog.setMessage("Login user...");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

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
                    String phoneNumber = "+" + ccp.getFullNumber() + number;
                    Log.d("cc2",phoneNumber);
                    Intent intent = new Intent(activity_Login.this,activity_VerifyPhone.class);
                    intent.putExtra("phoneNumber",phoneNumber);
                    startActivity(intent);
                }

            }
        });
    }

    public void signIn(String email, String pass){
        progressDialog.show();
        mAuthentication.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        progressDialog.dismiss();
                        Toast.makeText(activity_Login.this,"Successful",Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuthentication.getCurrentUser();
                        Intent intent_foodmenu = new Intent(activity_Login.this, activity_bottomNav.class);
                        startActivity(intent_foodmenu);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity_Login.this, "Fail to login", Toast.LENGTH_LONG).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(activity_Login.this, "" +e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });;
    }
}
