package com.example.food_order_application_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class activity_Login extends AppCompatActivity {
    Button btnLogin, btnRegister;
    TextInputEditText mail, password;
    Firebase mRef;
    FirebaseAuth mAuthentication;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthentication = FirebaseAuth.getInstance();
        mail = findViewById(R.id.mailEt);
        password = findViewById(R.id.passwordEt);

        btnLogin = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(activity_Login.this);
        progressDialog.setMessage("Login user...");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_Login.this, activity_Login_signup.class);
                startActivity(intent);
            }
        });
        /*
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               table_user.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       if(dataSnapshot.child(mail.getText().toString()).exists()) {
                           //User information
                           User user = dataSnapshot.child(mail.getText().toString()).getValue(User.class);
                           if (user.getPassword().equals(password.getText().toString())) {
                               Toast.makeText(activity_Login.this, "Sucessfull", Toast.LENGTH_LONG).show();
                           } else {
                               Toast.makeText(activity_Login.this, "Fail", Toast.LENGTH_LONG).show();
                           }
                       }
                       else {
                           Toast.makeText(activity_Login.this, "User not exist", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }
       });
       */

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String pass = password.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mail.setError("Invalid email");
                    mail.setFocusable(true);
                }

                else if(pass.length() <6) {
                    password.setError("Password length at least 6 characters");
                    password.setFocusable(true);
                }
                else {
                    signIn(email, pass);
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
