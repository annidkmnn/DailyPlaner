package com.example.annikadiekmann.game_backlog;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class UpdateTaskItem extends AppCompatActivity {

    EditText mTask;
    EditText mTimeStart;
    EditText mTimeEnd;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        mTask = findViewById(R.id.update_taskText);
        mTimeStart = findViewById(R.id.update_taskTimeStart);
        mTimeEnd = findViewById(R.id.update_taskTimeEnd);

        //Initialize the local variables
        db = AppDatabase.getInstance(this);


        final TaskItem taskItemUpdate = getIntent().getParcelableExtra(HomeTaskItem.EXTRA_REMINDER);
        mTask.setText(taskItemUpdate.getTask());
        mTimeStart.setText(taskItemUpdate.getTimeStart());
        mTimeEnd.setText(taskItemUpdate.getTimeEnd());


        FloatingActionButton fab = findViewById(R.id.update_save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int errorStep = 0;
            String task = mTask.getText().toString();
            String timeStart = mTimeStart.getText().toString();
            String timeEnd = mTimeEnd.getText().toString();

            /* Checking */
            if (task.trim().length() < 1) {
                errorStep++;
                mTask.setError("Provide a task name.");
            }

            if (timeStart.trim().length() < 1) {
                errorStep++;
                mTimeStart.setError("Provide a time");
            }

            if (timeEnd.trim().length() < 1) {
                errorStep++;
                mTimeEnd.setError("Provide a time");
            }

            if (errorStep == 0) {

                taskItemUpdate.setTask(task);
                taskItemUpdate.setTimeStart(timeStart);
                taskItemUpdate.setTimeEnd(timeEnd);

                //Prepare the return parameter and return
                Intent resultIntent = new Intent(UpdateTaskItem.this,HomeTaskItem.class);
                resultIntent.putExtra(HomeTaskItem.EXTRA_REMINDER, taskItemUpdate);
                setResult(Activity.RESULT_OK, resultIntent);

                Toast.makeText(getApplicationContext(), "Task Updated.", Toast.LENGTH_SHORT).show();

                finish();
            }

            }
        });

        mTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            // Create the new DatePickerDialog instance.


            timePickerDialog = new TimePickerDialog(UpdateTaskItem.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    if (hourOfDay >= 12) {
                        amPm = " pm";
                    } else {
                        amPm = " am";
                    }
                    mTimeStart.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();

            }

        });

        mTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            // Create the new DatePickerDialog instance.

            timePickerDialog = new TimePickerDialog(UpdateTaskItem.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    if (hourOfDay >= 12) {
                        amPm = " pm";
                    } else {
                        amPm = " am";
                    }
                    mTimeEnd.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();

            }

        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
