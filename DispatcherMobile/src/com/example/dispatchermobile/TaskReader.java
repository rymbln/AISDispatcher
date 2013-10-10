package com.example.dispatchermobile;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.Toast;
import android.widget.ListView;




public class TaskReader extends ListActivity {

    private OnClickListener loadTasksListener;
    private AdapterView.OnItemLongClickListener taskListLongClickListner;

    private Button refreshButton;
    private TextView footerText;
    private ListView taskListView;

    private int requireUpdate = 0;

    public static ArrayList<TaskItem> taskItems = new ArrayList<TaskItem>();
    private TaskArrayAdapter adapter = null;

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
        refreshButton = (Button) findViewById(R.id.refreshTask);
        taskListView = getListView();

        loadTasksListener = new OnClickListener() {
            public void onClick(View v) {
                updateView();
            }
        };

        taskListLongClickListner = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaskReader.this, "Long tap is not working yet", Toast.LENGTH_LONG).show();
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        taskListView.setOnItemLongClickListener(taskListLongClickListner);

        refreshButton.setOnClickListener(loadTasksListener);

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
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("requireUpdate", 1);
    }

    private void updateView() {
         refreshTaskList();
        footerText = (TextView) findViewById(R.id.footerText);
        footerText.setText(Common.getCurrentTime());
    }



    private void refreshTaskList() {
        NetTaskOperations _op = new NetTaskOperations(this);
        _op.execute("");
        taskItems = _op.getTaskItems();

    }


}