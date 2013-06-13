package com.example.test5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.example.test5.crutches.MainAdapter;
import com.example.test5.forms.map.MapActivity;
import com.example.test5.models.TaskModel;
import com.example.test5.forms.create_task.CreateTaskActivity;
import com.example.test5.forms.view_task.ViewTaskActivity;

import java.util.Random;

public class MainActivity extends Activity {

    ImageButton addBtn, mapBtn, quickAddButton, addGroupBtn, authBtn;
    ListView taskList;
    EditText taskText;
    ArrayAdapter<TaskModel> adapter;
    private View.OnClickListener nextTimeBabyListener;

    {
        nextTimeBabyListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Будет реализовано в следующей версии").setNeutralButton("Ok", null);
                builder.create();
                builder.show();
            }
        };
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        taskText = (EditText) findViewById(R.id.main_taskText);
        taskList = (ListView) findViewById(R.id.main_tasklist);
        addBtn = (ImageButton) findViewById(R.id.main_addbutton);
        mapBtn = (ImageButton) findViewById(R.id.main_mapBtn);
        quickAddButton = (ImageButton) findViewById(R.id.main_quickAddButton);
        addGroupBtn = (ImageButton) findViewById(R.id.main_addGroupBtn);
        authBtn = (ImageButton) findViewById(R.id.main_authBtn);


        // неведомая фигня инициализирующая список
//        adapter = new ArrayAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view,R.id.list_view_taskText);
        adapter = new MainAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view);
        taskList.setFocusable(true);
        taskList.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        authBtn.setOnClickListener(nextTimeBabyListener);
        addGroupBtn.setOnClickListener(nextTimeBabyListener);
        quickAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskText.getText() != null && !taskText.getText().toString().isEmpty()) {
                    addItemToTaskList(taskText.getText().toString());
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
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
                        addItemToTaskList(((EditText) v).getText().toString());
                    }
                }
                return true;
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reliseOmNomNom();
            }
        });
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                intent.putExtra(getString(R.string.viewTask_TaskModel), adapter.getItem(position));
                MainActivity.this.startActivity(intent);
                return true;
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

    private void addItemToTaskList(String text) {
        if (!text.trim().isEmpty()) {
            //create a simple task
            // TODO (Drepp) запихать куда нибудь свежесозданную таску
            TaskModel task = new TaskModel(new Random().nextLong(), text);

            // add a task into list
            adapter.insert(task, 0);
            taskText.setText("");
        }
    }

    private void reliseOmNomNom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are u sure about it?")
                .setPositiveButton("NoNoNoNo!!!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("NomNomNomNom!!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }
}
