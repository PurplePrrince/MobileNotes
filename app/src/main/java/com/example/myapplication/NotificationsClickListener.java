package com.example.myapplication;

import androidx.cardview.widget.CardView;

import com.example.myapplication.Models.Notification;

public interface NotificationsClickListener {
    void onClick(Notification notification);
    void onLongClick(Notification notification, CardView cardview);
}
