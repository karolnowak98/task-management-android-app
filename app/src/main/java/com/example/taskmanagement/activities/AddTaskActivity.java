package com.example.taskmanagement.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagement.R;
import com.example.taskmanagement.adapters.TaskListAdapter;
import com.example.taskmanagement.db.Task;
import com.example.taskmanagement.db.RoomDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button btnTaskDate, btnAddTask, btnCancel;
    private EditText etTaskName;
    private RadioGroup rgTasks;
    private boolean test = true;
    private TaskListAdapter adapter;
    private final List<Task> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initDatePicker();

        etTaskName = findViewById(R.id.et_task_name);
        btnTaskDate = findViewById(R.id.btn_picker_task_date);
        rgTasks = findViewById(R.id.rg_tasks);
        btnAddTask = findViewById(R.id.btn_add_task);
        btnCancel = findViewById(R.id.btn_cancel);

        btnTaskDate.setText(getTodayDate());

        btnAddTask.setBackgroundColor(getResources().getColor(R.color.btn_green));
        btnCancel.setBackgroundColor(getResources().getColor(R.color.btn_red));

        btnAddTask.setOnClickListener(v -> {
            if(validateNewTask()){
                String taskName = etTaskName.getText().toString().trim();
                String taskDate = btnTaskDate.getText().toString().trim();
                String taskCategory = ((RadioButton) findViewById(rgTasks.getCheckedRadioButtonId())).getText().toString().trim();
                saveNewTask(taskName, taskDate, taskCategory);
            }
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt("YEAR", datePickerDialog.getDatePicker().getYear());
        outState.putInt("MONTH", datePickerDialog.getDatePicker().getMonth());
        outState.putInt("DAY", datePickerDialog.getDatePicker().getDayOfMonth());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        test = false;
        super.onRestoreInstanceState(savedInstanceState);
        datePickerDialog.getDatePicker().updateDate(savedInstanceState.getInt("YEAR"),
                                                    savedInstanceState.getInt("MONTH"),
                                                    savedInstanceState.getInt("DAY"));
    }

    //______________________DATE METHODS______________________

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            btnTaskDate.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year + " r.     ";
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "stycznia";
        if(month == 2)
            return "lutego";
        if(month == 3)
            return "marca";
        if(month == 4)
            return "kwietnia";
        if(month == 5)
            return "maja";
        if(month == 6)
            return "czerwca";
        if(month == 7)
            return "lipca";
        if(month == 8)
            return "sierpnia";
        if(month == 9)
            return "września";
        if(month == 10)
            return "października";
        if(month == 11)
            return "listopada";
        if(month == 12)
            return "grudnia";
        return "stycznia";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private boolean validateNewTask(){
        if (rgTasks.getCheckedRadioButtonId() == -1) {
            RadioButton rb = findViewById(R.id.rb_other);
            rb.setError(getString(R.string.err_rg_task_category));
        }
        if (etTaskName.getText().toString().trim().equals(""))
            etTaskName.setError(getString(R.string.err_task_name));
        Log.e("1", String.valueOf(rgTasks.getCheckedRadioButtonId()));

        return (!etTaskName.getText().toString().trim().equals("")) && (rgTasks.getCheckedRadioButtonId() != -1);
    }

    private void saveNewTask(String taskName, String taskDate, String taskCategory){

        RoomDB database = RoomDB.getInstance(this.getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskCategory(taskCategory);
        task.setTaskDate(taskDate);
        database.taskDao().insertTask(task);
        /*dataList.clear();
        dataList.addAll(database.taskDao().getAllTasks());
        adapter.notifyDataSetChanged();*/

        setResult(RESULT_OK, intent);
        Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_text, Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
