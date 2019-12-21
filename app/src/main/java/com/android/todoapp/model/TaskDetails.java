package com.android.todoapp.model;

public class TaskDetails {

    int taskId;
    private String task_name, task_time, task_desc, task_cat, task_date;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTask() {

        return task_name;
    }

    public void setTask(String task_name) {
        this.task_name = task_name;
    }

    public String getTime() {
        return task_time;
    }

    public void setTime(String task_time) {
        this.task_time = task_time;
    }

    public String getDesc() {
        return task_desc;
    }

    public void setDesc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getCat() {
        return task_cat;
    }
    public void setCat(String task_cat) {
        this.task_cat = task_cat;
    }

    public String getDate() {
        return task_date;
    }

    public void setDate(String task_date) {
        this.task_date = task_date;
    }



}
