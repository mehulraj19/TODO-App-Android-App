package com.example.todoapp.adapter;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskViewModel;
import com.example.todoapp.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTodoClickListener onTodoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = onTodoClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Task task = taskList.get(position);
        String formattedDate = Utils.formatDate(task.getDueDate());

        holder.textView.setText(task.getTask());
        if (task.isDone()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.radioButton.setChecked(true);
        }else {
            holder.textView.setPaintFlags(0);
            holder.radioButton.setChecked(false);
        }
        holder.todayChip.setText(formattedDate);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(ColorStateList.valueOf(Utils.priorityColor(task)));
        holder.todayChip.setButtonTintList(ColorStateList.valueOf(Utils.priorityColor(task)));

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatCheckBox radioButton;
        public AppCompatTextView textView;
        public Chip todayChip;
        public AppCompatImageButton imageButton;
        OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super((itemView));
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            textView = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            imageButton = itemView.findViewById(R.id.deleteButton);
            this.onTodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
//textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Task currTask = taskList.get(getAdapterPosition());
            if (id == R.id.todo_row_layout) {
                onTodoClickListener.onTodoClick(currTask);
            } else if(id == R.id.todo_radio_button) {
                if (radioButton.isChecked()){
                    currTask.setDone(true);
                }else {
                    currTask.setDone(false);
                }
                TaskViewModel.update(currTask);
            }else if (id == R.id.deleteButton) {
                onTodoClickListener.onTodoRadioButton(currTask);
            }
        }
    }
}
