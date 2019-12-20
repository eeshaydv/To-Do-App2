package com.android.todoapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.todoapp.DataBase.TaskDatabaseContract.TaskDatabase;
import com.android.todoapp.DataBase.TaskDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class TaskCreate extends AppCompatActivity {

    TaskDatabaseHelper dbHelper;
    String task_name, task_time, task_desc, task_cat;
    SQLiteDatabase db;
    private EditText ed_task, ed_time, ed_desc, ed_cat;
    private Button bt_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_create);
        dbHelper = new TaskDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        ed_task = findViewById(R.id.ed_task);
        ed_time = findViewById(R.id.ed_time);
        ed_desc = findViewById(R.id.ed_desc);
        ed_cat = findViewById(R.id.ed_cat);
        bt_add = findViewById(R.id.bt_add);

        ed_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                task_name = ed_task.getText().toString();
                task_time = ed_time.getText().toString();
                task_desc = ed_desc.getText().toString();


                if (TextUtils.isEmpty(task_name)) {
                    showErrorSnack("Please enter task");
                    ed_task.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(task_desc)) {
                    showErrorSnack("Please enter task description");
                    ed_desc.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(task_time)) {
                    showErrorSnack("Please enter time");
                    ed_time.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(task_cat)) {
                    showErrorSnack("Please choose Task Category");
                    ed_cat.requestFocus();
                    return;
                }


                ContentValues values = new ContentValues();
                values.put(TaskDatabase.COLUMN_NAME_COL1, task_name);
                values.put(TaskDatabase.COLUMN_NAME_COL2, task_time);
                values.put(TaskDatabase.COLUMN_NAME_COL3, task_desc);
                values.put(TaskDatabase.COLUMN_NAME_COL4, task_cat);
                long rowId = db.insert(TaskDatabase.TABLE_NAME, null, values);
                if (rowId != -1) {
                    Toast.makeText(TaskCreate.this, "Task Created succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TaskCreate.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TaskCreate.this, "Something Went Wrong! ", Toast.LENGTH_SHORT).show();

                }


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

            AlertDialog.Builder adb = new AlertDialog.Builder(TaskCreate.this);
            adb.setTitle("Select Category");
            adb.setItems(categoryNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ed_cat.setText(categoryNames[which]);
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
}