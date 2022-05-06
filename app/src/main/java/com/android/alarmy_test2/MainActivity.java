package com.android.alarmy_test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.android.alarmy_test2.Fragments.HomeFragment;
import com.android.alarmy_test2.Fragments.NewsFragment;
import com.android.alarmy_test2.Fragments.RecordFragment;
import com.android.alarmy_test2.Fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_record:
                        selectedFragment = new RecordFragment();
                        break;
                    case R.id.nav_news:
                        selectedFragment = new NewsFragment();
                        break;
                    case R.id.nav_setting:
                        selectedFragment = new SettingFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };


}