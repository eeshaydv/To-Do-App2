package com.android.todoapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.todoapp.DataBase.TaskDatabaseContract.TaskDatabase;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TaskCreation.db";
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TaskDatabaseContract.TaskDatabase.TABLE_NAME +
            "( " + TaskDatabase._ID + " INTEGER PRIMARY KEY," +
            TaskDatabase.COLUMN_NAME_COL1 + " text," +
            TaskDatabase.COLUMN_NAME_COL2 + " text," +
            TaskDatabase.COLUMN_NAME_COL3 + " text," +
            TaskDatabase.COLUMN_NAME_COL4 + " text)";
    private static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + TaskDatabase.TABLE_NAME;

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
