package com.example.food_order_application_2.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.R;
import com.example.food_order_application_2.Login_activity.activity_Loginmenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {
    TextView accountName, accountPhone, accountMail;
    FirebaseAuth mAuthentication;
    DatabaseReference account;
    Button btnSignout;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user, container, false);

        mAuthentication = FirebaseAuth.getInstance();
        accountName = view.findViewById(R.id.accountName);
        accountPhone = view.findViewById(R.id.accountPhone);
        accountMail = view.findViewById(R.id.accountMail);
        btnSignout = view.findViewById(R.id.btnLogout);
        loadUserInformation();
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthentication.signOut();
                Intent intent = new Intent(getActivity(), activity_Loginmenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void loadUserInformation() {
        FirebaseUser user = mAuthentication.getCurrentUser();
        account = FirebaseDatabase.getInstance().getReference("User") ;
        account.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue((User.class));
                accountName.setText(user.getName());
                accountPhone.setText(user.getPhone());
                accountMail.setText(user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
