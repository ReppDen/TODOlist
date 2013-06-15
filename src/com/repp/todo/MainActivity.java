package com.repp.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.repp.todo.crutches.MainAdapter;
import com.repp.todo.forms.create_task.CreateViewTaskActivity;
import com.repp.todo.forms.map.MapActivity;
import com.repp.todo.models.TaskModel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();
    private static int CREATE_TASK = 1;
    private static int VIEW_TASK = 2;
    private final static String PATH = "/sdcard/tasks.xml";
    ImageButton addBtn, mapBtn, quickAddButton, addGroupBtn, authBtn;
    ListView taskList;
    EditText taskText;
    List<TaskModel> tasks = new ArrayList<TaskModel>();
    ArrayAdapter<TaskModel> adapter;
    Document document;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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

        initXml();

        // неведомая фигня инициализирующая список
//        adapter = new ArrayAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view,R.id.list_view_taskText);
        adapter = new MainAdapter<TaskModel>(this.getBaseContext(), R.layout.list_view, tasks);
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
                Intent intent = new Intent(MainActivity.this, CreateViewTaskActivity.class);
                intent.putExtra(getString(R.string.createTask_taskText), new TaskModel());
                startActivityForResult(intent, CREATE_TASK);
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CreateViewTaskActivity.class);
                intent.putExtra(getString(R.string.createTask_taskText), adapter.getItem(position));
                MainActivity.this.startActivityForResult(intent, VIEW_TASK);
            }
        });
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Удалить задачу?")
                        .setNegativeButton("Нет", null)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.remove((TaskModel) parent.getAdapter().getItem(position));
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();

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
            TaskModel task = new TaskModel(text);
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

    /**
     * создает xmlку с тасками если ее еще нет
     */
    private void initXml()  {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(PATH);
        if (!xmlFile.exists()) {
            if (!xmlFile.exists()) {
                try {
                    xmlFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Element tasks = new Element("tasks");
                document = new Document(tasks);

                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(document, new FileWriter(PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                document = builder.build(xmlFile);
                Element rootNode = document.getRootElement();
                List<Element> list = rootNode.getChildren("task");

                for (Element node : list) {
                    TaskModel m = new TaskModel();
                    m.setId(Long.valueOf(node.getChildText("id")));
                    m.setText(node.getChildText("text"));
                    m.setCompleted(Boolean.valueOf(node.getChildText("completed")));
                    m.setRaiting(Integer.valueOf(node.getChildText("raiting")));
                    m.setPhoto(node.getChildText("photo"));
                    m.setAudio(node.getChildText("audio"));
                    m.setAdress(node.getChildText("address"));
                    try {
                        m.setDate(sdf.parse(node.getChildText("date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tasks.add(m);
                }

            } catch (IOException io) {
                System.out.println(io.getMessage());
            } catch (JDOMException jdomex) {
                System.out.println(jdomex.getMessage());
            }

        }
    }


    /**
     * сохраняет все задачи
     */
    private void saveTasks() {
        Element tasksEl  = document.getRootElement();
        tasksEl.removeContent();
        for (TaskModel t : tasks) {
            Element el = new Element("task");
            el.addContent(new Element("id").setText(String.valueOf(t.getId())));
            el.addContent(new Element("text").setText(t.getText()));
            el.addContent(new Element("date").setText(sdf.format(t.getDate())));
            el.addContent(new Element("raiting").setText(String.valueOf(t.getRaiting())));
            el.addContent(new Element("completed").setText(String.valueOf(t.getCompleted())));
            el.addContent(new Element("audio").setText(String.valueOf(t.getAudio())));
            el.addContent(new Element("photo").setText(String.valueOf(t.getPhoto())));
            el.addContent(new Element("address").setText(String.valueOf(t.getPhoto())));

            tasksEl.addContent(el);
        }

        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(PATH));
        }  catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"task is not saved!",5);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIEW_TASK) {
            if (resultCode == RESULT_OK) {
                TaskModel task = (TaskModel) data.getExtras().get(getString(R.string.createTask_taskText));
                for (int i = 0, tasksSize = tasks.size(); i < tasksSize; i++) {
                    TaskModel t = tasks.get(i);
                    if (t.getId().equals(task.getId())) {
                        System.out.println("qweqwew");
                        tasks.set(i,task);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == CREATE_TASK) {
            if (resultCode == RESULT_OK) {
                TaskModel task = (TaskModel) data.getExtras().get(getString(R.string.createTask_taskText));
                tasks.add(task);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
       saveTasks();
        super.onPause();
    }

}
