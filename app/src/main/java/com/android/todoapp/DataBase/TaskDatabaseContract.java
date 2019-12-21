package com.android.todoapp.DataBase;

import android.provider.BaseColumns;


public final class TaskDatabaseContract {

    private TaskDatabaseContract() {
    }

    public static class TaskDatabase implements BaseColumns {
        public static final String TABLE_NAME = "task_details";
        public static final String COLUMN_NAME_COL1 = "task";
        public static final String COLUMN_NAME_COL2 = "time";
        public static final String COLUMN_NAME_COL3 = "description";
        public static final String COLUMN_NAME_COL4 = "category";
        public static final String COLUMN_NAME_COL5 = "date";

    }


}
