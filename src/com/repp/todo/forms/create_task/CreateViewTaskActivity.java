package com.repp.todo.forms.create_task;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.repp.todo.MainActivity;
import com.repp.todo.R;
import com.repp.todo.crutches.OpenFileDialog;
import com.repp.todo.models.TaskModel;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Форма просмотра и редактирования задачи
 *
 * @author Drepp
 * @since: 08.05.13
 */
public class CreateViewTaskActivity extends Activity {

    final static int RQS_RECORDING = 1;
    private static final int CAMERA_PIC_REQUEST = 1112;
    private static final Random r = new Random();

    MediaPlayer mediaPlayer;

    EditText taskText;
    TextView title,audioName;
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
        audioName = (TextView) findViewById(R.id.create_task_audio_name);
        date = (TextView) findViewById(R.id.create_task_date);
        raitingBar = (RatingBar) findViewById(R.id.create_task_rating);
        play = (ImageButton) findViewById(R.id.create_task_play);
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
        if (t.isNew()) {
            title.setText("Создание задачи");
        } else {
            title.setText("Редактирование задачи");
        }
        raitingBar.setRating((float) t.getRaiting());
        date.setText(sdf.format(t.getDate()));

        if (!t.getPhoto().isEmpty()) {
            // TODO
        }

        if (t.getAudio().isEmpty()) {
            audioName.setText("Файл не выбран");
            play.setEnabled(false);
        }

        if (!t.getAdress().isEmpty()) {
            // TODO
        }


    }

    private void initListeners() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    play.setImageResource(R.drawable.av_play_over_video);
                    mediaPlayer.stop();
                } else {
                    Uri uri = Uri.fromFile(new File(task.getAudio()));
                    mediaPlayer = MediaPlayer.create(CreateViewTaskActivity.this, uri);
                    play.setImageResource(R.drawable.av_pause_over_video);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play.setImageResource(R.drawable.av_play_over_video);
                        }
                    });
                    mediaPlayer.start();
                }
            }
        });

        audioSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileDialog ofd = new OpenFileDialog(CreateViewTaskActivity.this, null, new String[]{".3gpp", ".mp3"},
                        new OpenFileDialog.OnFileSelectedListener() {
                            public void onFileSelected(File f) {
                                task.setAudio(f.getPath());
                                play.setEnabled(true);
                                audioName.setText(f.getPath());
                            }
                        });
                ofd.show();
            }
        });

        photoSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileDialog ofd = new OpenFileDialog(CreateViewTaskActivity.this, null, new String[]{".jpg", ".png"},
                        new OpenFileDialog.OnFileSelectedListener() {
                            public void onFileSelected(File f) {
                                ImageView image = (ImageView) findViewById(R.id.create_task_photo);
                                Uri u = Uri.fromFile(f);
                                image.setImageURI(u);
                            }
                        });
                ofd.show();
            }
        });

        audioCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, RQS_RECORDING);

            }
        });

        photoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File("/todo/" + r.nextInt())));
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });

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
                intent.putExtra(getString(R.string.createTask_taskText), task);
                setResult(RESULT_OK, intent);
                CreateViewTaskActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Диктофон
        if (requestCode == RQS_RECORDING && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getAudioFilePathFromUri(uri);
            getContentResolver().delete(uri, null, null);
            task.setAudio(filePath);
            play.setEnabled(true);
            audioName.setText(filePath);
            Toast.makeText(CreateViewTaskActivity.this,
                    "Запись сохранена: " + filePath,
                    Toast.LENGTH_LONG).show();
        }

        //камера
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ImageView image = (ImageView) findViewById(R.id.create_task_photo);
            image.setImageBitmap(thumbnail);
        }
    }

    private String getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        return cursor.getString(index);
    }
}

