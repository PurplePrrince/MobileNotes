package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationTakerActivity extends AppCompatActivity {
    EditText editText_notification_title;
    TextView notification_date_text;
    FloatingActionButton save_notification;
    ExtendedFloatingActionButton show_dates;
    Notification notification;
    boolean isOldNotification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_edit);

        editText_notification_title = findViewById(R.id.editText_notification_title);
        save_notification = findViewById(R.id.save_notification);
        show_dates = findViewById(R.id.show_dates);


        notification = new Notification();
        try {
            notification = (Notification) getIntent().getSerializableExtra("old_notification");
            editText_notification_title.setText(notification.getTitle());
            notification_date_text.setText(notification.getDate());
            isOldNotification = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.notification_btn);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.notes_btn) {
                startActivities(new Intent[]{new Intent(getApplicationContext(), MainActivity.class)});
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.account_btn) {
                startActivities(new Intent[]{new Intent(getApplicationContext(), AccountActivity.class)});
                overridePendingTransition(0, 0);
                return true;
            }
            return itemId == R.id.notification_btn;
        });

        save_notification.setOnClickListener(view -> {
            String title = editText_notification_title.getText().toString();
            String date = notification_date_text.getText().toString();

            if(title.isEmpty()) {
                Toast.makeText(NotificationTakerActivity.this, "Добавьте заголовок", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isOldNotification) {
                notification = new Notification();
            }

            notification.setTitle(title);
            notification.setDate(date);

            Intent intent = new Intent();
            intent.putExtra("notification", notification);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
