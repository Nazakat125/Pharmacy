package com.example.projectp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class pharmacyInterface extends AppCompatActivity {
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_interface);

        // Receive "number" value from Intent
        Intent intent = getIntent();
        String phone = intent.getStringExtra("number");
        String pharmacyName = intent.getStringExtra("pharmacyName");

        // Create a Bundle and set "number" value to it
        Bundle bundle = new Bundle();
        bundle.putString("number", phone);
        bundle.putString("pharmacyName", pharmacyName);

        // Create instances of fragments
        Message messageFragment = new Message();
        Report reportFragment = new Report();
        Home homeFragment = new Home();

        // Set the bundle with "number" to each fragment
        messageFragment.setArguments(bundle);
        reportFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);

        // Find the BottomNavigationView in the layout
        bnView = findViewById(R.id.bottom_navigation);

        // Replace the initial fragment with the Home fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment).commit();

        // Set initial arguments for the Home fragment
        homeFragment.setArguments(bundle);

        // Set a listener for BottomNavigationView
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    // Replace with Home fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, homeFragment).commit();
                    // Set arguments for Home fragment
                    homeFragment.setArguments(bundle);

                    return true;
                } else if (itemId == R.id.menu_messagess) {
                    // Replace with Message fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, messageFragment).commit();
                    // Set arguments for Message fragment
                    messageFragment.setArguments(bundle);
                    return true;
                } else if (itemId == R.id.menu_report) {
                    // Replace with Report fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, reportFragment).commit();
                    // Set arguments for Report fragment
                    reportFragment.setArguments(bundle);
                    return true;
                }
                return false;
            }
        });

    }
}
