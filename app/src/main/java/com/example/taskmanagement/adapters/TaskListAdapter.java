package com.example.taskmanagement.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.db.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context context;

    public TaskListAdapter(Context context){
        this.context = context;
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, int position) {
        Task data = taskList.get(position);
        String id = data.getID() +". ";
        holder.tvTaskNumber.setText(id);
        holder.tvTaskName.setText(data.getTaskName());
        holder.tvTaskCategory.setText(data.getTaskCategory());
        holder.tvTaskDate.setText(data.getTaskDate());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskNumber, tvTaskName, tvTaskCategory, tvTaskDate;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTaskNumber = view.findViewById(R.id.tv_task_number);
            tvTaskName = view.findViewById(R.id.tv_task_name);
            tvTaskCategory = view.findViewById(R.id.tv_task_category);
            tvTaskDate = view.findViewById(R.id.tv_task_date);
        }
    }
}
