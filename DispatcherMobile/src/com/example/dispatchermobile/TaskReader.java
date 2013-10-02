package com.example.dispatchermobile;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;



public class TaskReader extends ListActivity {
    public static TaskItem SelectedTaskItem;

    //###################
    //Added in sublime
    //###################

    private OnClickListener loadTasksListener;

    private Intent currentIntent;


    private Button _refreshButton;
    private ListView _taskListView;
    private TextView _footerText;

    private int requireUpdate = 0;

    private void initializeApp() {
        _refreshButton = (Button) findViewById(R.id.refreshTask);
        // get the listview from layout.xml
        taskListView = (ListView) findViewById(android.R.id.list);

        //
        //--Более лучший вариант,
        //особенно если в последствии делать один класс,
        //где будут все листнеры на все основные действия с задачами и сервером
        //

        loadTasksListener = new OnClickListener() {
            public void onClick(View v) {
                updateView();
            }
        };
        _refreshButton.setOnClickListener(loadTasksListener);



        // what to execute, when task is clicked
        taskListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _av, View _view, int _index, long _arg3) {
                SelectedTaskItem = taskItems.get(_index);
                // Call activity shows selected task
                Intent _intent = new Intent("com.example.DispatcherMobile.SelectedTaskItem");

                // -- Попробовать подумать как передать туда экземпляр TaskItem
                //_intent.putExtra("taskItem", _item);

                //startActivity(_intent);
                startActivityForResult(_intent, 10);
            }
        });

        _aa = new ArrayAdapter<TaskItem>(this, R.layout.list_item, taskItems);
        taskListView.setAdapter(_aa);


        HttpHelpers.initialize(this);
        //
        //--Возможный вариант, если не потребуется выносить загрузку задач в отдельный листнер
        //

        // refreshButton.setOnClickListener(new OnClickListener()
        // {
        // 	@Override
        // 	public void onClick(View _v)
        // 	{
        // 		_sourceUrl = "http://dispatcher-app.appspot.com/app/";
        // 		refreshTaskList();
        // 	}
        // });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 10)
            return;
        else {
            Bundle extras = intent.getExtras();
            int returned = extras.getInt("Res");
            Toast.makeText(this, "smth: " + returned, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        initializeApp();

        if (savedInstanceState != null) {
            requireUpdate = savedInstanceState.getInt("requireUpdate");
            if (requireUpdate > 0) {
                updateView();
            }

        }


        refreshTaskList();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("requireUpdate", 1);
    }

    private void updateView() {
        refreshTaskList();

        _footerText = (TextView) findViewById(R.id.footerText);
        //Получаем и записываем в футер текущую дату
        _footerText.setText(Calendar.getInstance().toString());
    }

    ListView taskListView = null;
    ArrayList<TaskItem> taskItems = new ArrayList<TaskItem>();
    ArrayAdapter<TaskItem> _aa = null;

    // public static final int TaskItemDialog = 1;

    // protected Dialog onCreateDialog(int _id, Bundle _args)
    // {
    // 	// TODO Auto-generated method stub
    // 			switch (_id) {
    // 			case TaskItemDialog: {
    // 				LayoutInflater li = LayoutInflater.from(this);
    // 				View _taskDetails = li.inflate(R.layout.task_details, null);

    // 				AlertDialog.Builder taskDialog = new AlertDialog.Builder(this);
    // 				taskDialog.setTitle("Task Item");
    // 				taskDialog.setView(_taskDetails);

    // 				return taskDialog.create();
    // 			}
    // 			}

    // 			return null;
    // }

    // protected void onPrepareDialog(int id, Dialog dialog, Bundle args)
    // {
    // 	// TODO Auto-generated method stub
    // 	switch (id) {
    // 	case TaskItemDialog: {
    // 		AlertDialog taskDialog = (AlertDialog) dialog;

    // 		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
    // 		taskDialog.setTitle(SelectedTaskItem.getCompanyName() + " : "
    // 				+ sdf.format(SelectedTaskItem.getDeliveryTime()));

    // 		String text = SelectedTaskItem.getCompanyName() + " : "
    // 				+ SelectedTaskItem.getDeliveryTime();

    // 		TextView tv = (TextView) taskDialog
    // 				.findViewById(R.id.taskDetailsTextView);
    // 		tv.setText(text);

    // 	}
    // 	}
    // }

    private void refreshTaskList() {

        // ArrayList<TaskItem> newItems = TaskItem.getTaskItems(_sourceUrl);

        // taskItems.clear();
        // taskItems.addAll(newItems);

        // _aa.notifyDataSetChanged();
        ITaskProvider provider = new FakeTasksProvider();
        ArrayList<TaskItem> tasks = provider.getTasks();
        Bind(tasks);
    }

    private void Bind(ArrayList<TaskItem> _tasks) {
        ArrayAdapter<TaskItem> _adapter = new TaskAdapter(this, _tasks);
        setListAdapter(_adapter);
    }


}