package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.Notes;
import com.example.myapplication.Models.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    EditText notification_title;
    Button show_dates, show_time, save_notification;
    TextView date_text, time_text;
    Notification notification;
    boolean isOldNotification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_edit);

        notification = new Notification();
        try {
            notification = (Notification) getIntent().getSerializableExtra("old_notification");
            notification_title.setText(notification.getTitle());
            date_text.setText(notification.getDate());
            isOldNotification = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        save_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = notification_title.getText().toString();
                int date = date_text.getInputType();

//                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
//                Date date = new Date();

                if (!isOldNotification) {
                    notification = new Notification();
                }

                notification.setTitle(title);
                notification.setDate(date);

                Intent intent = new Intent();
                intent.putExtra("notification", notification);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.notification);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.notes) {
                    startActivities(new Intent[]{new Intent(getApplicationContext(), MainActivity.class)});
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.notification) {
                    return true;
                } else if (itemId == R.id.account) {
                    startActivities(new Intent[]{new Intent(getApplicationContext(), NotificationActivity.class)});
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }
}
