package com.example.annikadiekmann.game_backlog;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskItemDao {

    @Query("SELECT * FROM TaskItem ORDER BY timeStart")
    LiveData<List<TaskItem>> getAllTaskItems();

    @Insert
    void insertTaskItems(TaskItem taskItems);

    @Delete
    void deleteTaskItems(TaskItem taskItems);

    @Update
    void updateTaskItems(TaskItem taskItems);


}
