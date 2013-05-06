package com.example.test5;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.test5.models.TaskModel;
import com.example.test5.view_task.ViewTaskActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button add;
    ListView taskList;
    EditText editText;
    ArrayAdapter<TaskModel> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.editText);
        taskList = (ListView) findViewById(R.id.tasklist);
        adapter = new ArrayAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view);

        taskList.setAdapter(adapter);
        add = (Button) findViewById(R.id.addbutton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                intent.putExtra(getString(R.string.task_identificator), "OMG!!!! it works!!!");
                startActivity(intent);
            }
        });
        initListeners();



    }

    private void initListeners() {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String text = ((EditText) v).getText().toString();
                        System.out.println(text.isEmpty());
                        if (!text.trim().isEmpty()) {
                             //create a simple task
                            TaskModel task = new TaskModel(0L, text);

                            // add a task into list
                            adapter.insert(task, 0);
                             ((EditText) v).setText("");
                         }
                    }
                }
                return true;
            }
        });
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                intent.putExtra(getString(R.string.task_identificator), adapter.getItem(position));
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
