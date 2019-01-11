package com.example.annikadiekmann.game_backlog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TaskItem.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {


    public abstract TaskItemDao taskItemDao();

    private final static String NAME_DATABASE = "taskItem_log";


    //Static instance
    private static AppDatabase sInstance;

    static AppDatabase getInstance(Context context) {

        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .build();

        }

        return sInstance;

    }

}


