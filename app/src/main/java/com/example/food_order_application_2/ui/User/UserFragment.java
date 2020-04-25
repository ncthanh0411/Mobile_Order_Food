package com.example.food_order_application_2.ui.User;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.food_order_application_2.MapsActivity;
import com.example.food_order_application_2.Model.User;
import com.example.food_order_application_2.R;
import com.example.food_order_application_2.Login_activity.activity_Loginmenu;
import com.example.food_order_application_2.activity_profileImage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    private TextView accountName, accountPhone, accountMail;
    private FirebaseAuth mAuthentication;
    DatabaseReference account;
    Button btnSignout,btnEdit;
    ImageView imageViewPhone,imageViewMap, imageViewUser, imageViewInfo;
    private static final int REQUEST_CALL = 1;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user, container, false);

        mAuthentication = FirebaseAuth.getInstance();
        accountName = view.findViewById(R.id.accountName);
        accountPhone = view.findViewById(R.id.accountPhone);
        accountMail = view.findViewById(R.id.accountMail);
        btnSignout = view.findViewById(R.id.btnLogout);
        btnEdit = view.findViewById(R.id.btnEdit);
        imageViewPhone = view.findViewById(R.id.imageViewPhone);
        imageViewMap = view.findViewById(R.id.imageViewMap);
        imageViewUser = view.findViewById(R.id.imageViewUser);
        imageViewInfo = view.findViewById(R.id.imageViewInfo);

        loadUserInformation();
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_profileImage.class);
                startActivity(intent);
            }
        });


        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthentication.signOut();
                Intent intent = new Intent(getActivity(), activity_Loginmenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_editUser.class);
                startActivity(intent);
            }
        });
        imageViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();

            }
        });
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        imageViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return view;
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Information Of App")
                .setMessage("TST Food app was built by TST group." +
                        "517H0084: Nguyễn Công Thành   "+ "517H0090: Lê Ngọc Khánh Toàn  " +
                        "517H0079: Nguyễn Bảo Hoàng Sang")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void makePhoneCall(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else {
            String dial = "tel:+84907986613";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
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
                Log.d("abcded",user.getImage());
                String link = user.getImage();

                if(!link.isEmpty()){
                    Picasso.get().load(link).resizeDimen(R.dimen.image_size_user, R.dimen.image_size_user).into(imageViewUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
