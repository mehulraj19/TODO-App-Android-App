package com.example.todoapp.adapter;

import com.example.todoapp.model.Task;

public interface OnTodoClickListener {

    void onTodoClick(Task task);

    void onTodoRadioButton(Task task);

    void onTodoCheckboxButton(Task task);

}
