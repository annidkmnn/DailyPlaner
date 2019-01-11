package com.example.annikadiekmann.game_backlog;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.annikadiekmann.game_backlog.TaskItemAdapter.*;

public class TaskItemHistory extends AppCompatActivity implements TaskItemClickListener {


    private List<TaskItem> mTaskItems;
    private TaskItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;

    public static final String EXTRA_REMINDER = "TaskItem";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_item_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        //Initialize the local variables
        mTaskItems = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getLogs().observe(this, new Observer<List<TaskItem>>() {
            @Override
            public void onChanged(@Nullable List<TaskItem> taskItems) {
                mTaskItems = taskItems;
                updateUI();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View viewHistory){
                finish();
            }
        });

    }

    private void updateUI(){
        if (mAdapter == null) {
            mAdapter = new TaskItemAdapter(mTaskItems, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mTaskItems);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            if (resultCode == RESULT_OK){
                TaskItem doneTaskItem = data.getParcelableExtra(TaskItemHistory.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                mMainViewModel.insertTaskItems(doneTaskItem);

                updateUI();
            }
    }

    @Override
    public void taskItemOnClick(int i) {

    }

    @Override
    public void onBucketListCheckBoxChange(int position, boolean mDone) {

    }
}
