package com.example.test5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.example.test5.map.MapActivity;
import com.example.test5.models.TaskModel;
import com.example.test5.create_task.CreateTaskActivity;
import com.example.test5.view_task.ViewTaskActivity;

public class MainActivity extends Activity {

    Button quickAdd, mapBtn;
    ListView taskList;
    EditText taskText;
    ArrayAdapter<TaskModel> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        taskText = (EditText) findViewById(R.id.main_taskText);
        taskList = (ListView) findViewById(R.id.main_tasklist);
        quickAdd = (Button) findViewById(R.id.main_addbutton);
        mapBtn = (Button) findViewById(R.id.main_mapBtn);

        // неведомая фигня инициализирующая список
        adapter = new ArrayAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view,R.id.list_view_taskText);
        taskList.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        quickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                intent.putExtra(getString(R.string.createTask_taskText), taskText.getText().toString());
                startActivity(intent);
            }
        });
        taskText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String text = ((EditText) v).getText().toString();
                        if (!text.trim().isEmpty()) {
                            //create a simple task
                            // TODO (Drepp) запихать куда нибудь свежесозданную таску
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
                intent.putExtra(getString(R.string.viewTask_TaskModel), adapter.getItem(position));
                MainActivity.this.startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
