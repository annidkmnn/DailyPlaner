package com.example.annikadiekmann.game_backlog;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

public class MainViewModel {

    private TaskItemRepository mRepository;
    private LiveData<List<TaskItem>> mTaskItems;

    public MainViewModel(Context context) {
        mRepository = new TaskItemRepository(context);
        mTaskItems = mRepository.getAllTaskItems();
    }
    public LiveData<List<TaskItem>> getLogs() {
        return mTaskItems;
    }
    public void insertTaskItems(TaskItem taskItem) {
        mRepository.insertTaskItems(taskItem);
    }
    public void updateTaskItems(TaskItem taskItem) {
        mRepository.updateTaskItems(taskItem);
    }
    public void deleteTaskItems(TaskItem taskItem) {
        mRepository.deleteTaskItems(taskItem);
    }

}
