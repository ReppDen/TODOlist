package com.example.test5.forms.view_task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import com.example.test5.R;
import com.example.test5.models.TaskModel;

/**
 * акитивити просмотра/редактирования задачи
 *
 * @author Drepp
 * @since: 24.03.13
 */
public class ViewTaskActivity extends Activity {
    EditText taskDescription;
    TaskModel inModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);
        inModel = (TaskModel) getIntent().getSerializableExtra(getString(R.string.viewTask_TaskModel));

        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDescription.setText(inModel.getText());
    }
}