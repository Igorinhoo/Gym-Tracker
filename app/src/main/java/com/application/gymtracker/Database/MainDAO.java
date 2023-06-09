package com.application.gymtracker.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.application.gymtracker.MainActivity;
import com.application.gymtracker.Models.Pull;

import java.util.List;
@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Pull pull);

    @Delete
    void delete(Pull pull);

    @Query("SELECT * FROM pulltb ORDER BY ID DESC")
    List<Pull> getAll();

    @Query("UPDATE pulltb SET exe_name = :exe_name, reps_numb = :reps_numb, weight = :weight, date = :date WHERE ID = :id")
    void update(int id, String exe_name, String reps_numb, String weight, String date);
}
