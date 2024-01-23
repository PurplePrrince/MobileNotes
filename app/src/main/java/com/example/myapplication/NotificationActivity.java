package com.example.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Models.Notification;
import java.util.Calendar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText notification_title;
    Button save_notification;
    TextView date_text;
    Notification notification;
    boolean isOldNotification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_edit);
        date_text = findViewById(R.id.date_text);

        findViewById(R.id.show_dates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        notification = new Notification();
        try {
            notification = (Notification) getIntent().getSerializableExtra("old_notification");
            notification_title.setText(notification.getTitle());

            isOldNotification = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        save_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = notification_title.getText().toString();
                int date = date_text.getInputType();

                if(title.isEmpty()) {
                    Toast.makeText(NotificationActivity.this, "Заполните название уведомления", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + 1 + "/" + year;
        date_text.setText(date);
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
                    startActivities(new Intent[]{new Intent(getApplicationContext(), AccountActivity.class)});
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }
}
