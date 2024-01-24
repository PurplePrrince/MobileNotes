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
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.Adapter.NotificationsListAdapter;
import com.example.myapplication.DataBase.RoomDB;
import com.example.myapplication.Models.Notification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recycler_notifications;
    FloatingActionButton fab_notification_add;
    NotificationsListAdapter notificationsListAdapter;
    RoomDB database;
    List<Notification> notifications = new ArrayList<>();
    SearchView searchView_notifications;
    Notification selectedNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_list);

        recycler_notifications = findViewById(R.id.recycler_notifications);
        fab_notification_add = findViewById(R.id.fab_notification_add);

        searchView_notifications = findViewById(R.id.searchView_notifications);

        database = RoomDB.getInstance(this);
        notifications = database.notificationDAO().getAll();

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

        updateRecyclre(notifications);

        fab_notification_add.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, NotificationTakerActivity.class);
            startActivity(intent);
        });

        searchView_notifications.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

//        findViewById(R.id.show_dates).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });
//
//        notification = new Notification();
//        try {
//            notification = (Notification) getIntent().getSerializableExtra("old_notification");
//            notification_title.setText(notification.getTitle());
//
//            isOldNotification = true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        save_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String title = notification_title.getText().toString();
//                int date = date_text.getInputType();
//
//                if(title.isEmpty()) {
//                    Toast.makeText(NotificationActivity.this, "Заполните название уведомления", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!isOldNotification) {
//                    notification = new Notification();
//                }
//
//                notification.setTitle(title);
//                notification.setDate(date);
//
//                Intent intent = new Intent();
//                intent.putExtra("notification", notification);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//            }
//        });
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.notification_btn);
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int itemId = item.getItemId();
//                if (itemId == R.id.notes_btn) {
//                    startActivities(new Intent[] {new Intent(getApplicationContext(), MainActivity.class)});
//                    overridePendingTransition(0, 0);
//                    return true;
//                } else if (itemId == R.id.account_btn) {
//                    startActivities(new Intent[]{new Intent(getApplicationContext(), AccountActivity.class)});
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                return itemId == R.id.notification_btn;
//            }
//        });
//
//    }
//
//    private void showDatePickerDialog() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this,
//                this,
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        );
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        String date = dayOfMonth + "/" + month + 1 + "/" + year;
//        date_text.setText(date);
//    }
    }

    private void filter(String newText) {
        List<Notification> filteredList = new ArrayList<>();

        for (Notification singleNotification : notifications) {
            if (singleNotification.getTitle().toLowerCase().contains(newText.toLowerCase()))
                filteredList.add(singleNotification);
        }
        notificationsListAdapter.filterList(filteredList);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notification new_notification = (Notification) data.getSerializableExtra("notification");
                database.notificationDAO().insert(new_notification);
                notifications.clear();
                notifications.addAll(database.notificationDAO().getAll());
                notificationsListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notification new_notification = (Notification) data.getSerializableExtra("notification");
                database.notificationDAO().update(new_notification.getID(), new_notification.getTitle(), new_notification.getDate());
                notifications.clear();
                notifications.addAll(database.notificationDAO().getAll());
                notificationsListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecyclre(List<Notification> notifications) {
        recycler_notifications.setHasFixedSize(true);
        recycler_notifications.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notificationsListAdapter = new NotificationsListAdapter(NotificationActivity.this, notifications, notificationsClickListener );
    }

    private final NotificationsClickListener notificationsClickListener = new NotificationsClickListener() {
        @Override
        public  void onClick(Notification notification) {
            Intent intent = new Intent(NotificationActivity.this, NotificationTakerActivity.class);
            intent.putExtra("old_notification", notification);
            startActivityForResult(intent, 102);
        }

        @Override
        public  void onLongClick(Notification notification, CardView cardView) {
            selectedNotification = notification;
            showPopup (cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete) {
            database.notificationDAO().delete(selectedNotification);
            notifications.remove(selectedNotification);
            notificationsListAdapter.notifyDataSetChanged();
            Toast.makeText(NotificationActivity.this, "Notification removed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
