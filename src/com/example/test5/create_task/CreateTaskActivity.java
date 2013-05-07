package com.example.test5.create_task;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.example.test5.R;
import com.example.test5.models.TaskModel;

/**
 * Здесь будет ваша реклама
 *
 * @author Drepp
 * @since: 08.05.13
 */
public class CreateTaskActivity extends Activity {

    EditText taskDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_task);
        String taskText = (String) getIntent().getSerializableExtra(getString(R.string.createTask_taskText));

        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDescription.setText(taskText);
    }
}
