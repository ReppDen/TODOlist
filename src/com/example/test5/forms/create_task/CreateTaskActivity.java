package com.example.test5.forms.create_task;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.test5.R;

/**
 * Здесь будет ваша реклама
 *
 * @author Drepp
 * @since: 08.05.13
 */
public class CreateTaskActivity extends Activity {

    EditText taskDescription;
    Button close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_task);
        String taskText = (String) getIntent().getSerializableExtra(getString(R.string.createTask_taskText));

        taskDescription = (EditText) findViewById(R.id.create_task_taskDescription);
        close = (Button) findViewById(R.id.create_task_close);
        taskDescription.setText(taskText);

        initListeners();
    }

    private void initListeners() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTaskActivity.this.finish();
            }
        });
    }
}
