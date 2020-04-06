package com.example.food_order_application_2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.food_order_application_2.Menu.activity_menu;
import com.example.food_order_application_2.ui.User.UserFragment;
import com.example.food_order_application_2.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

public class activity_bottomNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_menu, R.id.navigation_account)
                .build();

    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_menu:
                            selectedFragment = new activity_menu();
                            break;
                        case R.id.navigation_account:
                            selectedFragment = new UserFragment();
                            break;
                    }
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                           selectedFragment).commit();
                    return true;
                }
            };

}
