package com.example.annikadiekmann.game_backlog;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HomeTaskItem extends AppCompatActivity implements TaskItemAdapter.TaskItemClickListener  {

    private List<TaskItem> mTaskItems;
    private TaskItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;

    EditText mTask;
    EditText mTimeStart;
    EditText mTimeEnd;


    public static final String EXTRA_REMINDER = "TaskItem";
    public static final int REQUESTCODE_ADDING = 1234;
    public static final int REQUESTCODE_EDITING = 5678;
    private int mModifyPosition;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_taskitem, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_taskitem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the local variables
        mTaskItems = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        updateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getLogs().observe(this, new Observer<List<TaskItem>>() {
            @Override
            public void onChanged(@Nullable List<TaskItem> taskItems) {
                mTaskItems = taskItems;
                updateUI();
            }
        });

        mTask = findViewById(R.id.add_taskText);
        mTimeStart = findViewById(R.id.add_taskTimeStart);
        mTimeEnd = findViewById(R.id.add_taskTimeEnd);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        mMainViewModel.deleteTaskItems(mTaskItems.get(position));

                    }

                    @Override
                    public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                        new RecyclerViewSwipeDecorator.Builder(HomeTaskItem.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addBackgroundColor(ContextCompat.getColor(HomeTaskItem.this, R.color.colorDeleteTask))
                                .addActionIcon(R.drawable.ic_delete)
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }


                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }



    public void goToAddPage(View view) {

        Intent intent = new Intent(HomeTaskItem.this, AddTaskItem.class);
        startActivityForResult(intent,REQUESTCODE_ADDING);
    }

    public void goToHistoryPage(View viewHistory) {
        Intent intentHistory = new Intent(HomeTaskItem.this, TaskItemHistory.class);
        startActivity(intentHistory);
    }

    public void onTaskListCheckBoxChange(int position, boolean mDone) {
        TaskItem taskItem = mTaskItems.get(position);
        taskItem.setDone(mDone);
        mTaskItems.set(position, taskItem);
        mMainViewModel.updateTaskItems(mTaskItems.get(position));
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new TaskItemAdapter(mTaskItems, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mTaskItems);
        }
    }

    @Override
    public void taskItemOnClick(int i) {
        Intent intent = new Intent(HomeTaskItem.this, UpdateTaskItem.class);
        mModifyPosition = i;
        intent.putExtra(EXTRA_REMINDER, mTaskItems.get(i));
        startActivityForResult(intent, REQUESTCODE_EDITING);
    }

    @Override
    public void onBucketListCheckBoxChange(int position, boolean mDone) {
        Intent doneIntent = new Intent(HomeTaskItem.this, TaskItemHistory.class);
        doneIntent.putExtra(EXTRA_REMINDER, mTaskItems.get(position));
        setResult(Activity.RESULT_OK, doneIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUESTCODE_ADDING){
            if (resultCode == RESULT_OK){
                TaskItem addedTaskItem = data.getParcelableExtra(HomeTaskItem.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                mMainViewModel.insertTaskItems(addedTaskItem);

                updateUI();
            }
        } else if (requestCode == REQUESTCODE_EDITING){
            if(resultCode == RESULT_OK){
                TaskItem updatedTaskItem = data.getParcelableExtra(HomeTaskItem.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                mMainViewModel.updateTaskItems(updatedTaskItem);

                updateUI();
            }
        }
    }

}


