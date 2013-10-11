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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        initializeApp();

        if (savedInstanceState != null) {
            int requireUpdate = savedInstanceState.getInt("requireUpdate");
            if (requireUpdate > 0) {
                updateView();
            }
        }
        updateView();
    }


    private void initializeApp() {
        Button refreshButton = (Button) findViewById(R.id.refreshTask);
        ListView taskListView = getListView();

        HttpHelpers.initialize(this);
        // TODO - Для загрузки локальных Tasks - потом удалить или переработать
        TaskProvider.initializeTasks();

        OnClickListener loadTasksListener = new OnClickListener() {
            public void onClick(View v) {
                updateView();
            }
        };

        AdapterView.OnItemLongClickListener taskListLongClickListner = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaskReader.this, "Long tap is not working yet", Toast.LENGTH_LONG).show();
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        taskListView.setOnItemLongClickListener(taskListLongClickListner);

        refreshButton.setOnClickListener(loadTasksListener);


    }

    public void updateView() {
        refreshTaskList();
        TextView footerText = (TextView) findViewById(R.id.footerText);
        footerText.setText(Common.getCurrentTime());
    }



    private void refreshTaskList() {
        NetTaskOperations _op = new NetTaskOperations(this);
        _op.execute("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("update")) {
                Boolean result = data.getExtras().getBoolean("update");
                if (result != null && result) {
                    this.updateView();
                }
            }
        }
    }

}