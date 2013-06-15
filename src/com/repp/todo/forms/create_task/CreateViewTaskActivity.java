package com.repp.todo.forms.create_task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.repp.todo.MainActivity;
import com.repp.todo.R;
import com.repp.todo.models.TaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Форма просмотра и редактирования задачи
 *
 * @author Drepp
 * @since: 08.05.13
 */
public class CreateViewTaskActivity extends Activity {

    EditText taskText;
    TextView title;
    RatingBar raitingBar;
    ImageButton play;
    ProgressBar progress;
    Button audioSelect;
    Button audioCreate;
    Button photoSelect;
    Button photoCreate;
    ImageButton addressSelect;
    EditText addressText;
    TextView date;
    Button save;
    Button close;

    TaskModel task;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_task);
        task = (TaskModel) getIntent().getSerializableExtra(getString(R.string.createTask_taskText));
        title = (TextView) findViewById(R.id.create_task_title);
        taskText = (EditText) findViewById(R.id.create_task_taskText);
        date = (TextView) findViewById(R.id.create_task_date);
        raitingBar = (RatingBar) findViewById(R.id.create_task_rating);
        play = (ImageButton) findViewById(R.id.create_task_play);
        progress = (ProgressBar) findViewById(R.id.create_task_progress);
        audioSelect = (Button) findViewById(R.id.create_task_select_audio);
        audioCreate = (Button) findViewById(R.id.create_task_create_audio);
        photoSelect = (Button) findViewById(R.id.create_task_select_photo);
        photoCreate = (Button) findViewById(R.id.create_task_create_photo);
        addressSelect = (ImageButton) findViewById(R.id.create_task_address_select);
        addressText = (EditText) findViewById(R.id.create_task_adress_text);
        save = (Button) findViewById(R.id.create_task_save);
        close = (Button) findViewById(R.id.create_task_close);

        initDeafultState(task);

        initListeners();
    }

    private void initDeafultState(TaskModel t) {
        taskText.setText(t.getText());
        if (t.isNew())  {
            title.setText("Создание задачи");
        } else {
            title.setText("Редактирование задачи");
        }
        raitingBar.setRating((float) t.getRaiting());
        date.setText(sdf.format(t.getDate()));

        if (!t.getPhoto().isEmpty()) {
            // TODO
        }

        if (!t.getAudio().isEmpty()) {
            // TODO
        }

        if (!t.getAdress().isEmpty()) {
            // TODO
        }


    }

    private void initListeners() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateViewTaskActivity.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setText(taskText.getText().toString());
                try {
                    task.setDate(sdf.parse(date.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                task.setRaiting(Math.round(raitingBar.getRating()));
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.createTask_taskText),task);
                setResult(RESULT_OK, intent);
                CreateViewTaskActivity.this.finish();
            }
        });
    }
}
