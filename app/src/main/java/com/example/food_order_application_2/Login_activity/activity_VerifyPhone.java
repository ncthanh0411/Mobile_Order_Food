package com.example.food_order_application_2.Login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.R;
import com.example.food_order_application_2.activity_bottomNav;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class activity_VerifyPhone extends AppCompatActivity {

    private String verificationID;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button btn;
    private EditText OTPText;
    User account;
    FloatingActionButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyphone);

        btn = findViewById(R.id.btnAttend);
        progressBar = findViewById(R.id.progressBar);
        OTPText = findViewById(R.id.OTPText);
        btnBack = findViewById(R.id.btnBack);
        mAuth = FirebaseAuth.getInstance();
        String phoneNumber = null;
        if(getIntent().getStringExtra("phoneNumber") != null){
            phoneNumber = getIntent().getStringExtra("phoneNumber");
        }
        if(getIntent().getStringExtra("phoneNumberSignup") != null){
            phoneNumber = getIntent().getStringExtra("phoneNumberSignup");
        }

        if(getIntent().getParcelableExtra("user") != null){
            account = getIntent().getParcelableExtra("user");
        }

        sendVerificationCode(phoneNumber);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = OTPText.getText().toString().trim();
                if(code.isEmpty() || code.length() < 6){
                    OTPText.setError("Invalid code...");
                    OTPText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(account != null){
                                DatabaseReference table_user = FirebaseDatabase.getInstance().getReference("User") ;
                                table_user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(account);
                            }
                            Intent intent = new Intent(activity_VerifyPhone.this, activity_bottomNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(activity_VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(activity_VerifyPhone.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}
