package com.example.dispatchermobile;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import java.util.Calendar;
import android.widget.Toast;
import android.widget.ListView;



public class TaskReader extends ListActivity {

    private OnClickListener loadTasksListener;
    private Button _refreshButton;
    private TextView _footerText;

    private int requireUpdate = 0;

    private ListView taskListView = null;
    public static ArrayList<TaskItem> taskItems = new ArrayList<TaskItem>();
    private ArrayAdapter<TaskItem> _aa = null;

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
        updateView();
    }


    private void initializeApp() {
        _refreshButton = (Button) findViewById(R.id.refreshTask);
        taskListView = (ListView) findViewById(android.R.id.list);
        loadTasksListener = new OnClickListener() {
            public void onClick(View v) {
                updateView();
            }
        };
        _refreshButton.setOnClickListener(loadTasksListener);

        _aa = new ArrayAdapter<TaskItem>(this, R.layout.list_item, taskItems);
        setListAdapter(_aa);


        HttpHelpers.initialize(this);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        TaskItem _ti = (TaskItem) getListAdapter().getItem(position);
        String _str = _ti.toJSON().toString();

        Intent _intent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
        _intent.putExtra("taskItem", _str);
        startActivity(_intent);
       // Toast.makeText(this, "not working yet", Toast.LENGTH_LONG).show();
    }



    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return String.format("%02d:%02d:%02d", hour, minute, second); // ЧЧ:ММ:СС - формат времени
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("requireUpdate", 1);
    }

    private void updateView() {
        refreshTaskList();

        _footerText = (TextView) findViewById(R.id.footerText);
        //Получаем и записываем в футер текущую дату
        _footerText.setText(getCurrentTime());
    }



    private void refreshTaskList() {

        NetTaskOperations _op = new NetTaskOperations(this);
        _op.execute("");
        taskItems = _op.getTaskItems();

    }


}