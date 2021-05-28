package com.example.taskmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.adapters.TaskListAdapter;
import com.example.taskmanagement.db.RoomDB;
import com.example.taskmanagement.db.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private TaskListAdapter taskListAdapter;
    private List<Task> taskList;
    private TextView tvNoTasks, tvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Button btnAddTask = findViewById(R.id.btn_add_task);
        tvNoTasks = findViewById(R.id.tv_no_tasks);
        tvTasks = findViewById(R.id.tv_tasks);

        RoomDB db = RoomDB.getInstance(this);
        taskList = db.taskDao().getAllTasks();

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        initRecyclerView();
        loadTaskList();
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.rv_list_of_tasks);
        rv.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(dividerItemDecoration);

        taskListAdapter = new TaskListAdapter(this);
        rv.setAdapter(taskListAdapter);
        rv.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent newData) {
        super.onActivityResult(requestCode, resultCode, newData);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            loadTaskList();
        }
    }

    private void loadTaskList(){
        RoomDB db = RoomDB.getInstance(getApplicationContext());
        taskList = db.taskDao().getAllTasks();
        taskListAdapter.setTaskList(taskList);
        if(taskList.size() > 0){
            tvNoTasks.setVisibility(View.GONE);
            tvTasks.setVisibility(View.VISIBLE);
        }
    }
}