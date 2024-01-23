package com.example.myapplication.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Models.Account;

@Dao
public interface accountDAO {

    @Insert (onConflict = REPLACE)
    void insert (Account account);

    @Query("UPDATE account SET name = :name, description = :description, imgID = :imgID WHERE ID = :id")
    void update (int id, String name, String description, int imgID);
}
