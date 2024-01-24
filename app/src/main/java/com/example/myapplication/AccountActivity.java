package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AccountActivity extends AppCompatActivity {

    ExtendedFloatingActionButton settings;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        settings = findViewById(R.id.settings);
        findViewById(R.id.settings).setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AccountTakerActivity.class);
            view.getContext().startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account_btn);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.notes_btn) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.notification_btn) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return itemId == R.id.account_btn;
        });


    }
}
