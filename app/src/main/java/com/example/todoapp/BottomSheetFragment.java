package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.todoapp.model.Priority;
import com.example.todoapp.model.SharedViewModel;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskViewModel;
import com.example.todoapp.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    private EditText enterTodo;
    private ImageButton priorityButton;
    private ImageButton calendarButton;
    private ImageButton saveButton;
    private CalendarView calendarView;
    Calendar calendar = Calendar.getInstance();
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private Group calendarGroup;
    private Date dueDate;
    private SharedViewModel sharedViewModel;
    private int selectedButtonId;
    private boolean isEdit;
    private Priority priority;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // Calendar Group
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);

        // enter todos
        enterTodo = view.findViewById(R.id.enter_todo_et);

        // saving...
        saveButton = view.findViewById(R.id.save_todo_button);

        //priority
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        //pre-defined calendar views
        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calendarButton.setOnClickListener(v -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideSoftKeyboard(v);
        });
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
        });
        priorityButton.setOnClickListener(v -> {
            Utils.hideSoftKeyboard(v);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });
        priorityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                selectedButtonId = checkedId;
                selectedRadioButton =  view.findViewById(selectedButtonId);
                if (selectedRadioButton.getId() == R.id.radioButton_high) {
                    priority = Priority.HIGH;
                } else if (selectedRadioButton.getId() == R.id.radioButton_med) {
                    priority = Priority.MEDIUM;
                } else if(selectedRadioButton.getId() == R.id.radioButton_low) {
                    priority = Priority.LOW;
                } else {
                    priority = Priority.LOW;
                }
            }else{
                priority = Priority.LOW;
            }
        });

        saveButton.setOnClickListener(v -> {
            String task = enterTodo.getText().toString();
            if (!TextUtils.isEmpty(task) && dueDate != null && priority != null) {
                Task myTask = new Task(task, priority, dueDate, Calendar.getInstance().getTime(), false);
                if (isEdit) {
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                }else{
                    TaskViewModel.insert(myTask);
                }
                enterTodo.setText("");
                if (this.isVisible()) this.dismiss();
            }else{
                Snackbar.make(saveButton, "Empty Task", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
        }
    }
}