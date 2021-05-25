package com.example.taskmanagement;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteractiveArrayAdapter extends RecyclerView.Adapter<InteractiveArrayAdapter.TaskModelRecyclerViewHolder> {

    private List<TaskModel> mListOfTasks;
    private LayoutInflater mInflater;
    private int rowIndex;
    private Activity context;

    public InteractiveArrayAdapter(Activity newContext, List<TaskModel> newListOfTasks){
        mInflater = newContext.getLayoutInflater();
        this.mListOfTasks = newListOfTasks;
    }

    @NonNull
    @Override
    public TaskModelRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View row = mInflater.inflate(R.layout.row_task,null);
        return new TaskModelRecyclerViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskModelRecyclerViewHolder taskModelRecyclerViewHolder, int newRowIndex){
        this.rowIndex = newRowIndex;
        TaskModel value = mListOfTasks.get(newRowIndex);

        taskModelRecyclerViewHolder.tvTaskNumber.setText(newRowIndex+1+". ");
        taskModelRecyclerViewHolder.tvTaskName.setText(value.getTaskName());
        taskModelRecyclerViewHolder.tvTaskCategory.setText(value.getTaskCategory());
        taskModelRecyclerViewHolder.tvTaskDate.setText(value.getTaskDate());
    }

    @Override
    public int getItemCount() {
        return mListOfTasks.size();
    }

    public class TaskModelRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskNumber, tvTaskName, tvTaskCategory, tvTaskDate;
        View mainElement;

        public TaskModelRecyclerViewHolder(@NonNull View rowElement) {
            super(rowElement);
            this.mainElement = rowElement;
            tvTaskNumber = rowElement.findViewById(R.id.tv_task_number);
            tvTaskName = rowElement.findViewById(R.id.tv_task_name);
            tvTaskCategory = rowElement.findViewById(R.id.tv_task_category);
            tvTaskDate = rowElement.findViewById(R.id.tv_task_date);
        }
    }
}
