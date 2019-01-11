package com.example.annikadiekmann.game_backlog;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {

    private List<TaskItem> mTaskItems;
    final private TaskItemClickListener mTaskItemClickListener;
    private Context mContext;

    public interface TaskItemClickListener{
        void taskItemOnClick(int i);
        void onBucketListCheckBoxChange(int position, boolean mDone);
    }

    public TaskItemAdapter(List<TaskItem> mTaskItems, TaskItemClickListener mTaskItemClickListener) {
        this.mTaskItems = mTaskItems;
        this.mTaskItemClickListener = mTaskItemClickListener;
    }

    @NonNull
    @Override
    public TaskItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowlayout, parent, false);

        // Return a new holder instance
        TaskItemAdapter.ViewHolder viewHolder = new TaskItemAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskItemAdapter.ViewHolder holder, int position) {

        TaskItem game = mTaskItems.get(position);
        holder.mTask.setText(game.getTask());
        holder.mTimeStart.setText(game.getTimeStart());
        holder.mTimeEnd.setText(game.getTimeEnd());
        holder.mDone.setChecked(game.isChecked());
        crossOutTextIfDone(holder.mTask, holder.mDone);
    }

    @Override
    public int getItemCount() { return mTaskItems.size(); }

    public void crossOutTextIfDone(TextView title, CheckBox done) {

        if (done.isChecked()) {
            title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            title.setPaintFlags(title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTask;
        public TextView mTimeStart;
        public TextView mTimeEnd;
        public CheckBox mDone;


        public ViewHolder(View itemView) {

            super(itemView);
            mTask = itemView.findViewById(R.id.taskText);
            mTimeStart = itemView.findViewById(R.id.timeTextStart);
            mTimeEnd = itemView.findViewById(R.id.timeTextEnd);
            mDone = itemView.findViewById(R.id.checkbox_done);

            mDone.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    mTaskItemClickListener.onBucketListCheckBoxChange(
                            clickedPosition,
                            mDone.isChecked()
                    );
                    crossOutTextIfDone(mTask, mDone);
                }
            });

            mTask.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    mTaskItemClickListener.taskItemOnClick(clickedPosition);
                }
            });

        }

    }

    public void swapList (List<TaskItem> newList) {
        mTaskItems = newList;

        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


}



