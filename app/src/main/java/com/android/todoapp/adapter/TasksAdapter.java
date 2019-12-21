package com.android.todoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.todoapp.DataBase.TaskDatabaseContract;
import com.android.todoapp.DataBase.TaskDatabaseHelper;
import com.android.todoapp.R;
import com.android.todoapp.UpdateTask;
import com.android.todoapp.model.TaskDetails;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    List<TaskDetails> taskDetailsList;
    SQLiteDatabase db;
    private Context context;
    private TaskDatabaseHelper dbHelper;


    public TasksAdapter(List<TaskDetails> taskDetailsList, Context context) {
        this.taskDetailsList = taskDetailsList;
        this.context = context;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {

        TaskDetails taskDetails = taskDetailsList.get(position);
        holder.task_name.setText(taskDetails.getTask());
        holder.task_time.setText(taskDetails.getTime());
        holder.task_desc.setText(taskDetails.getDesc());
        holder.task_cat.setText(taskDetails.getCat());
        holder.task_date.setText(taskDetails.getDate());

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int taskId;
                PopupMenu menu = null;
                try {
                    final TaskDetails taskDetails = taskDetailsList.get(position);
                    taskId = taskDetails.getTaskId();
                    dbHelper = new TaskDatabaseHelper(context);
                    db = dbHelper.getWritableDatabase();
                    menu = new PopupMenu(context, holder.ivMenu);

                    menu.inflate(R.menu.popup);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    db.delete(TaskDatabaseContract.TaskDatabase.TABLE_NAME, TaskDatabaseContract.TaskDatabase._ID + " = " + taskId, null);
                                    notifyItemRangeChanged(position, taskDetailsList.size());
                                    taskDetailsList.remove(position);
                                    notifyItemRemoved(position);
                                    db.close();
                                    break;
                                case R.id.update:
                                    Intent intent = new Intent(context, UpdateTask.class);
                                    intent.putExtra("USERID", taskId);
                                    context.startActivity(intent);
                                    break;


                            }


                            return false;
                        }
                    });
                    menu.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        Log.e("Size ", "" + taskDetailsList.size());
        return taskDetailsList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView task_name, task_time, task_desc, task_cat, task_date;
        ImageView ivMenu;

        public TaskViewHolder(View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            task_time = itemView.findViewById(R.id.task_time);
            task_desc = itemView.findViewById(R.id.task_desc);
            task_cat = itemView.findViewById(R.id.task_cat);
            task_date = itemView.findViewById(R.id.task_date);
            ivMenu = itemView.findViewById(R.id.iv_menu);
        }


    }
}