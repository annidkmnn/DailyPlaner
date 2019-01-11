package com.example.annikadiekmann.game_backlog;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskItemRepository {
    private AppDatabase mAppDatabase;
    private TaskItemDao mTaskItemDao;
    private LiveData<List<TaskItem>> mTaskItems;
    private Executor mExecutor = Executors.newSingleThreadExecutor();


    public TaskItemRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
        mTaskItemDao = mAppDatabase.taskItemDao();
        mTaskItems = mTaskItemDao.getAllTaskItems();
    }
    public LiveData<List<TaskItem>> getAllTaskItems() {
        return mTaskItems;
    }

    public void insertTaskItems(final TaskItem taskItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskItemDao.insertTaskItems(taskItem);
            }
        });
    }

    public void updateTaskItems(final TaskItem taskItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskItemDao.updateTaskItems(taskItem);
            }
        });
    }

    public void deleteTaskItems(final TaskItem taskItem) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskItemDao.deleteTaskItems(taskItem);
            }
        });
    }
}
