package com.example.test5;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.test5.models.TaskModel;

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

        initListeners();

    }

    private void initListeners() {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String text = ((EditText) v).getText().toString();
                         if (!text.isEmpty()) {
                             adapter.add(new TaskModel(0L, text));
                             // TODO create a task
                             ((EditText) v).setText("");
                             return true;
                         }
                    }
                }
                return false;
            }
        });
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              // TODO запусть просмота задачи
            }
        });
    }
}
