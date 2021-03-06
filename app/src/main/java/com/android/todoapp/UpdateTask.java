package com.android.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.todoapp.DataBase.TaskDatabaseContract.TaskDatabase;
import com.android.todoapp.DataBase.TaskDatabaseHelper;
import com.android.todoapp.model.TaskDetails;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateTask extends AppCompatActivity {

    TaskDatabaseHelper dbHelper;
    EditText ed_update_task, ed_update_time, ed_update_desc, ed_update_cat, ed_update_date;
    Button btUpdate;
    List<TaskDetails> userDetailsList;
    String task_name, task_time, task_desc, task_cat, task_date;
    SQLiteDatabase db;
    Calendar calendar = Calendar.getInstance();
    int chr, cmin, cdate, cmonth, cyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_taskk);
        dbHelper = new TaskDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        ed_update_task = findViewById(R.id.ed_update_task);
        ed_update_time = findViewById(R.id.ed_update_time);
        ed_update_desc = findViewById(R.id.ed_update_desc);
        ed_update_cat = findViewById(R.id.ed_update_cat);

        ed_update_date = findViewById(R.id.ed_update_date);

        ed_update_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UpdateTask.this, t1, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        ed_update_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateTask.this, t2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });








        btUpdate = findViewById(R.id.bt_update);

        final int rowId = getIntent().getIntExtra("USERID", -1);
        Cursor c1 = db.query(TaskDatabase.TABLE_NAME, null, TaskDatabase._ID + " = " + rowId, null, null, null, null);

        userDetailsList = new ArrayList<TaskDetails>();
        userDetailsList.clear();


        if (c1 != null && c1.getCount() != 0) {
            while (c1.moveToNext()) {

                ed_update_task.setText(c1.getString(c1.getColumnIndex(TaskDatabase.COLUMN_NAME_COL1)));
                ed_update_time.setText(c1.getString(c1.getColumnIndex(TaskDatabase.COLUMN_NAME_COL2)));
                ed_update_desc.setText(c1.getString(c1.getColumnIndex(TaskDatabase.COLUMN_NAME_COL3)));
                ed_update_cat.setText(c1.getString(c1.getColumnIndex(TaskDatabase.COLUMN_NAME_COL4)));
                ed_update_date.setText(c1.getString(c1.getColumnIndex(TaskDatabase.COLUMN_NAME_COL5)));

            }

        }
        ed_update_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


      btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateTask.this);
                builder.setTitle("Exit");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setMessage("are you sure do you want to Update the task?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        task_name = ed_update_task.getText().toString();
                        task_time = ed_update_time.getText().toString();
                        task_date = ed_update_desc.getText().toString();
                        task_desc = ed_update_desc.getText().toString();
                        task_date = ed_update_date.getText().toString();



                        if (TextUtils.isEmpty(task_name)) {
                            showErrorSnack("Please enter task");
                            ed_update_task.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(task_desc)) {
                            showErrorSnack("Please enter task description");
                            ed_update_desc.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(task_time)) {
                            showErrorSnack("Please enter time");
                            ed_update_time.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(task_cat)) {
                            showErrorSnack("Please choose Task Category");
                            ed_update_cat.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(task_date)) {

                            showErrorSnack("Please choose Task date");


                            ed_update_date.requestFocus();
                            return;
                        }


                        ContentValues values = new ContentValues();
                        values.put(TaskDatabase.COLUMN_NAME_COL1, task_name);
                        values.put(TaskDatabase.COLUMN_NAME_COL2, task_time);
                        values.put(TaskDatabase.COLUMN_NAME_COL3, task_desc);
                        values.put(TaskDatabase.COLUMN_NAME_COL4, task_cat);
                        values.put(TaskDatabase.COLUMN_NAME_COL5, task_date);
                        int updateId = db.update(TaskDatabase.TABLE_NAME, values, TaskDatabase._ID + " = " + rowId, null);
                        if (updateId != -1) {

                            Toast.makeText(UpdateTask.this, "Task Details Upated succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateTask.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(UpdateTask.this, "Task Updation Failed", Toast.LENGTH_SHORT).show();

                        }



                    }
                });
                
                builder.create().show();
            }
        });






    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }


    void load() {
        final String[] categoryNames = {"Personal", "Official"};

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(UpdateTask.this);
            adb.setTitle("Select Category");
            adb.setItems(categoryNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ed_update_cat.setText(categoryNames[which]);
                    task_cat = categoryNames[which];



                }
            });
            adb.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorSnack(String msg) {

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snackbar = Snackbar.make(viewGroup, Html.fromHtml("<b>" + msg + "</b>"), Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#075A90"));
        snackbar.show();
    }

    TimePickerDialog.OnTimeSetListener t1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            chr = hourOfDay;
            cmin = minute;
            task_time = chr + ":" + cmin;

            ed_update_time.setText("" + task_time);


        }
    };

    DatePickerDialog.OnDateSetListener t2=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cyear=year;
            cdate=dayOfMonth;
            cmonth=month;
            task_date=cdate+"-" +cmonth+" "+cdate;
            ed_update_date.setText(""+task_date);
        }
    };


}