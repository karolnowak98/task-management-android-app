package com.example.taskmanagement.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagement.R;
import com.example.taskmanagement.db.RoomDB;
import com.example.taskmanagement.db.Task;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    public static final String[] MONTHS = {"stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października", "listopada", "grudnia"};

    private Button btnTaskDate;
    private EditText etTaskName;
    private RadioGroup rgTasks;
    private DatePickerDialog datePickerDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initDatePicker();
        initAlertDialogBuilder();



        Button btnAddTask = findViewById(R.id.btn_add_task);
        Button btnCancel = findViewById(R.id.btn_cancel);
        etTaskName = findViewById(R.id.et_task_name);
        btnTaskDate = findViewById(R.id.btn_picker_task_date);
        rgTasks = findViewById(R.id.rg_tasks);
        alertDialog = alertDialogBuilder.create();

        btnTaskDate.setText(getTodayDate());

        btnAddTask.setOnClickListener(v -> {
            if(validateNewTask()){
                String taskName = etTaskName.getText().toString().trim();
                String taskDate = btnTaskDate.getText().toString().trim();
                String taskCategory = ((RadioButton) findViewById(rgTasks.getCheckedRadioButtonId())).getText().toString().trim();
                saveNewTask(taskName, taskDate, taskCategory);
            }
            else alertDialog.show();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("alert_dialog_status", alertDialog.isShowing());
        outState.putInt("year", datePickerDialog.getDatePicker().getYear());
        outState.putInt("month", datePickerDialog.getDatePicker().getMonth());
        outState.putInt("day", datePickerDialog.getDatePicker().getDayOfMonth());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getBoolean("alert_dialog_status"))
            alertDialog.show();

        int year = savedInstanceState.getInt("year");
        int month = savedInstanceState.getInt("month");
        int day = savedInstanceState.getInt("day");

        datePickerDialog.getDatePicker().updateDate(year, month, day);

        month+=1;
        btnTaskDate.setText(makeDateString(day, month, year));
    }

    private void initAlertDialogBuilder(){
        alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.dialog_yes, null)
                .setNegativeButton(R.string.dialog_no, (dialog, which) -> {
                    setResult(RESULT_CANCELED, new Intent());
                    finish();
                });
    }

    private boolean validateNewTask(){
        if (etTaskName.getText().toString().trim().equals(""))
            etTaskName.setError(getString(R.string.err_task_name));

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

        Toast toast = Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT);
        toast.show();

        setResult(RESULT_OK, intent);
        finish();
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

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + MONTHS[month] + " " + year + " r.";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}
