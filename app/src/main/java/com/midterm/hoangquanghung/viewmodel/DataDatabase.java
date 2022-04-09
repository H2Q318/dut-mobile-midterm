package com.midterm.hoangquanghung.viewmodel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm.hoangquanghung.model.Data;

@Database(entities = {Data.class}, version = 1)
public abstract class DataDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
    private static DataDatabase instance;

    public static DataDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    DataDatabase.class, "data").allowMainThreadQueries().build();
        }
        return instance;
    }
}
