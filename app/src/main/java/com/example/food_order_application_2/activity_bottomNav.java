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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class activity_bottomNav extends AppCompatActivity {

    private HomeFragment homeFragment;
    private activity_menu activity_Menu;
    private UserFragment userFragment;

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

        homeFragment = new HomeFragment();
        activity_Menu = new activity_menu();
        userFragment = new UserFragment();

        setFragment(homeFragment);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            setFragment(homeFragment);
                            return true;
                        case R.id.navigation_menu:
                            setFragment(activity_Menu);
                            return true;
                        case R.id.navigation_account:
                            setFragment(userFragment);
                            return true;
                        default:
                            return false;
                    }
                }
            };
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
