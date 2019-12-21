package com.android.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todoapp.DataBase.TaskDatabaseContract;
import com.android.todoapp.DataBase.TaskDatabaseHelper;
import com.android.todoapp.adapter.TasksAdapter;
import com.android.todoapp.model.TaskDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TasksAdapter tasksAdapter;
    TaskDetails taskDetails;
    List<TaskDetails> taskDetailsList;
    TaskDatabaseHelper taskDatabaseHelper;
    FloatingActionButton add;
    SQLiteDatabase db;
    Button log_out;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDatabaseHelper = new TaskDatabaseHelper(MainActivity.this);
        db = taskDatabaseHelper.getReadableDatabase();


        log_out = findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(I);

            }
        });


        recyclerView = findViewById(R.id.task_rv);
        add = findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, TaskCreate.class);
                startActivity(in);

            }
        });
        taskDetailsList = new ArrayList<TaskDetails>();
        taskDetailsList.clear();
        Cursor c1 = db.query(TaskDatabaseContract.TaskDatabase.TABLE_NAME, null, null, null, null, null, null);

        if (c1 != null && c1.getCount() != 0) {
            taskDetailsList.clear();
            while (c1.moveToNext()) {
                TaskDetails userDetailsItem = new TaskDetails();


                userDetailsItem.setTaskId(c1.getInt(c1.getColumnIndex(TaskDatabaseContract.TaskDatabase._ID)));
                userDetailsItem.setTask(c1.getString(c1.getColumnIndex(TaskDatabaseContract.TaskDatabase.COLUMN_NAME_COL1)));
                userDetailsItem.setTime(c1.getString(c1.getColumnIndex(TaskDatabaseContract.TaskDatabase.COLUMN_NAME_COL2)));
                userDetailsItem.setDesc(c1.getString(c1.getColumnIndex(TaskDatabaseContract.TaskDatabase.COLUMN_NAME_COL3)));
                userDetailsItem.setCat(c1.getString(c1.getColumnIndex(TaskDatabaseContract.TaskDatabase.COLUMN_NAME_COL4)));
                taskDetailsList.add(userDetailsItem);


            }


        }

        c1.close();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tasksAdapter = new TasksAdapter(taskDetailsList, MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tasksAdapter);


    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

}
