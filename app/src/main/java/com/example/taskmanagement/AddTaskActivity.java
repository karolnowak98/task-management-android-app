package com.example.taskmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button btnTaskDate;
    private EditText etTaskName;
    private TextView tvTaskName;
    private RadioGroup rgTasks;
    private boolean test = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initDatePicker();

        btnTaskDate = findViewById(R.id.btn_picker_task_date);
        Button btnAddTask = findViewById(R.id.btn_add_task);
        Button btnCancel = findViewById(R.id.btn_cancel);
        etTaskName = findViewById(R.id.et_task_name);
        tvTaskName = findViewById(R.id.tv_task_name);
        rgTasks = findViewById(R.id.rg_tasks);

        if(test)
            btnTaskDate.setText(getTodaysDate());

        btnAddTask.setBackgroundColor(getResources().getColor(R.color.btn_green));
        btnCancel.setBackgroundColor(getResources().getColor(R.color.btn_red));

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            if(validateNewTask()) {
                String taskCategory = ((RadioButton) findViewById(rgTasks.getCheckedRadioButtonId())).getText().toString();
                intent.putExtra("taskName", etTaskName.getText().toString());
                intent.putExtra("taskCategory", taskCategory);
                intent.putExtra("taskDate", btnTaskDate.getText().toString());
                MainActivity.NUMBER_OF_TASKS++;
                setResult(RESULT_OK, intent);
                finish();
            }
            else{
                //setResult(RESULT_OK, intent);
                //finish();
            }
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    private void showDateTimeDialog(final Button btnTaskDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        btnTaskDate.setText(simpleDateFormat.format(calendar));
                    }
                };
                new TimePickerDialog(AddTaskActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
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

    private String getTodaysDate() {
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
        if ((etTaskName.getText().toString().trim().equals("")) || (rgTasks.getCheckedRadioButtonId() == -1))
            return false;
        return true;
    }
}
