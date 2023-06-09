package com.application.gymtracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.application.gymtracker.Models.Pull;

@Database(entities = Pull.class, version = 1, exportSchema = false)
public abstract class RoomDBPush extends RoomDatabase {
    private static final String DATABASE_NAME = "Pushnew";
    private static RoomDBPush database;

    public synchronized static RoomDBPush getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDBPush.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract MainDAO mainDAO();
}
