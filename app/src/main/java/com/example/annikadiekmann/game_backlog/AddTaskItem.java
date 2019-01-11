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


public class AddTaskItem extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_taskitem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        mTask = findViewById(R.id.add_taskText);
        mTimeStart = findViewById(R.id.add_taskTimeStart);
        mTimeEnd = findViewById(R.id.add_taskTimeEnd);


        //Initialize the local variables
        db = AppDatabase.getInstance(this);


        FloatingActionButton fab = findViewById(R.id.add_save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int errorStep = 0;
                   String task = mTask.getText().toString();
                   String timeStart = mTimeStart.getText().toString();
                   String timeEnd = mTimeEnd.getText().toString();

                   TaskItem newItem = new TaskItem(task, timeStart, timeEnd, false);

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
                       Intent resultIntent = new Intent(AddTaskItem.this, HomeTaskItem.class);
                       resultIntent.putExtra(HomeTaskItem.EXTRA_REMINDER, newItem);
                       setResult(Activity.RESULT_OK, resultIntent);
                       Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();

                       finish();
                   }
               }
        });


        mTimeStart.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                // Create the new DatePickerDialog instance.
                timePickerDialog = new TimePickerDialog(AddTaskItem.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = " pm";
                        } else {
                            amPm = " am";
                        }

                        mTimeStart.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();

                }
            });

        mTimeEnd.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                // Create the new DatePickerDialog instance.
                timePickerDialog = new TimePickerDialog(AddTaskItem.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = " pm";
                        } else {
                            amPm = " am";
                        }

                        mTimeEnd.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();

            }

        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View view){
                finish();
                }
            });
    }

}