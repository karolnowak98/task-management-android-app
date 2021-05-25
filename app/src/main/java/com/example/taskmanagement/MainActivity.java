package com.example.taskmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int NUMBER_OF_TASKS = 0;
    private static final int REQUEST_CODE = 1;

    private ArrayList<TaskModel> mData;
    private RecyclerView mListOfTasks;
    private String taskName, taskCategory, taskDate;
    private TextView tv_no_tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<TaskModel>();
        tv_no_tasks = findViewById(R.id.tv_no_tasks);

        if(savedInstanceState != null){
            mData = savedInstanceState.getParcelableArrayList("COS");
        } else{
            for(int i = 0; i < NUMBER_OF_TASKS; i++){
                mData.add(new TaskModel(taskName, taskCategory, taskDate));
            }
        }


        Button btnAddTask = findViewById(R.id.btn_add_task);
        btnAddTask.setBackgroundColor(getResources().getColor(R.color.btn_green));
        btnAddTask.setOnClickListener(v -> {
            Intent intentAddTask = new Intent(getApplicationContext(), AddTaskActivity.class);
            startActivityForResult(intentAddTask, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent newData) {
        super.onActivityResult(requestCode, resultCode, newData);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(NUMBER_OF_TASKS!=0)
                tv_no_tasks.setVisibility(View.GONE);

            Bundle bundle = newData.getExtras();
            taskName = bundle.getString("taskName");
            taskCategory = bundle.getString("taskCategory");
            taskDate = bundle.getString("taskDate");

            mData.add(new TaskModel(taskName, taskCategory, taskDate));
            InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(this, mData);

            mListOfTasks = findViewById(R.id.rv_list_of_tasks);
            mListOfTasks.setAdapter(adapter);
            mListOfTasks.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}