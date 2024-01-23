package com.example.myapplication.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Models.Notes;
import com.example.myapplication.Models.Notification;

import java.util.List;

@Dao
public interface notificationDAO {

    @Insert (onConflict = REPLACE)
    void insert (Notification notification);

    @Query ("SELECT * FROM notifications ORDER BY id DESC")
    List<Notification> getAll();

    @Query ("UPDATE notifications SET title = :title, day = :day, month = :month, year = :year WHERE ID = :id")
    void update (String title, int day, int month, int year, int id);

    @Delete
    void delete (Notification notification);
}
