package com.example.taskmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button btnTaskDate, btnAddTask, btnCancel;
    private EditText etTaskName;
    private TextView tvTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initDatePicker();

        btnTaskDate = findViewById(R.id.btn_picker_task_date);
        btnAddTask = findViewById(R.id.btn_add_task);
        btnCancel = findViewById(R.id.btn_cancel);

        btnTaskDate.setText(getTodaysDate());
        btnAddTask.setBackgroundColor(getResources().getColor(R.color.btn_green));
        btnCancel.setBackgroundColor(getResources().getColor(R.color.btn_red));

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });


    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = makeDateString(day, month, year);
                btnTaskDate.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
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

    private boolean validateNewTask(){
        return true;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}
