package com.example.myapplication.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.myapplication.Models.Notes;

@Dao
public interface mainDAO {

    @Insert (onConflict = REPLACE)
    void insert (Notes notes);

    @Query ("SELECT * FROM notes ORDER BY date DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    void update (int id, String title, String notes);

    @Delete
    void delete (Notes notes);

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin (int id, boolean pin);

}
