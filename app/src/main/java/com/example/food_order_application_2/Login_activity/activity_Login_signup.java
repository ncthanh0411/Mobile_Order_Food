package com.example.food_order_application_2.Login_activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_Login_signup extends AppCompatActivity {

    Button btnSignUp;
    TextInputEditText phone,mail, name, password;
    TextInputLayout nameTIL, passTIL;
    FirebaseAuth mAuthentication;
    ProgressDialog progressDialog;

    User account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuthentication = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.btnSignUp);
        phone =  findViewById(R.id.signup_phoneEt);
        mail = findViewById(R.id.signup_mailEt);
        name = findViewById(R.id.signup_nameEt);
        password = findViewById(R.id.signup_passwordEt);
        //progress show when register
        progressDialog = new ProgressDialog(activity_Login_signup.this);
        progressDialog.setMessage("Registering user...");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String pass = password.getText().toString();
                account = new User(name.getText().toString(), password.getText().toString(),mail.getText().toString(),phone.getText().toString());

                //check if mail or password
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mail.setError("Invalid email");
                    mail.setFocusable(true);
                }

                else if(pass.length() <6) {
                    password.setError("Passord length at least 6 characters");
                    password.setFocusable(true);
                }
                else {
                    Register(email, pass);
                }
            }
        });
    }
    public void Register( String email, String pass){
        progressDialog.show();
        mAuthentication.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuthentication.getCurrentUser();
                            DatabaseReference table_user = FirebaseDatabase.getInstance().getReference("User") ;
                            table_user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(account);
                            // Sign up success, update UI with the signed-in user's information

                            Toast.makeText(activity_Login_signup.this,"Successful",Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity_Login_signup.this, "Fail to register", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(activity_Login_signup.this, "" +e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
