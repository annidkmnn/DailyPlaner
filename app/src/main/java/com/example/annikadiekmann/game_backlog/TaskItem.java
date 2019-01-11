package com.example.annikadiekmann.game_backlog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "taskItem")
public class TaskItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "task")
    private String mTask;

    @ColumnInfo(name = "timeStart")
    public String mTimeStart;

    @ColumnInfo(name = "timeEnd")
    private String mTimeEnd;

    @ColumnInfo(name = "done")
    private Boolean mDone;


    public TaskItem(String mTask, String mTimeStart, String mTimeEnd, Boolean mDone) {
        this.mTask = mTask;
        this.mTimeStart = mTimeStart;
        this.mTimeEnd = mTimeEnd;
        this.mDone = mDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return mTask;
    }

    public void setTask(String mTask) { this.mTask = mTask; }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String mTimeStart) {
        this.mTimeStart = mTimeStart;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String mTimeEnd) {
        this.mTimeEnd = mTimeEnd;
    }

    public Boolean getDone() {
        return mDone;
    }

    public boolean isChecked() { return mDone; }

    public void setDone(Boolean mDone) {
        this.mDone = mDone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.mTask);
        dest.writeString(this.mTimeStart);
        dest.writeString(this.mTimeEnd);
        dest.writeValue(this.mDone);
    }

    protected TaskItem(Parcel in) {
        this.id = in.readLong();
        this.mTask = in.readString();
        this.mTimeStart = in.readString();
        this.mTimeEnd = in.readString();
        this.mDone = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel source) {
            return new TaskItem(source);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };
}


