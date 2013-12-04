package com.example.dispatchermobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.dispatchermobile.models.ContactItem;
import com.example.dispatchermobile.models.MessageItem;
import com.example.dispatchermobile.models.TaskItem;


public class TaskItemSelected extends Activity {
    private Intent currentIntent;
    private ToggleButton toggleComplete;
    private LinearLayout llContacts;
    private LinearLayout llMessages;
    private LinearLayout llMain;
    private TaskItem task = new TaskItem();
    private ActionBar actionBar;

    private DataProvider dataProvider = new DataProvider();

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskselected_main);

        currentIntent = getIntent();
        context = this;

        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            String str = extras.getString("data");
            task = dataProvider.getTasklocal(extras.getString("data"));
            initializeView();
            updateView(task);
        }
    }


    public void initializeView() {
        toggleComplete = (ToggleButton) findViewById(R.id.toggleCompleteTask);
        toggleComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleTask();
            }
        });
        llMain = (LinearLayout) findViewById(R.id.llTaskSelectedInfoMain);
        llContacts = (LinearLayout) findViewById(R.id.llContacts);
        llMessages = (LinearLayout) findViewById(R.id.llMessages);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    public void updateView(TaskItem task) {
        actionBar.setTitle(task.getCompanyName());
        actionBar.setIcon(R.drawable.ic_task);

        // Set toggle
        if (task.getLastStatus().startsWith("Выполнено")) {
            toggleComplete.setChecked(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(0xFFff957a));
        } else if (task.getLastStatus().startsWith("Принято")) {
            toggleComplete.setChecked(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(0xFFB1FF90));
        } else {
            toggleComplete.setChecked(false);
        }

        //set main info
        llMain.removeAllViews();
        llMain.addView(task.getView(context));

        // Showinf contacts
        llContacts.removeAllViews();
        if (task.Contacts.size() > 0) {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("Contacts ( " + task.Contacts.size() + " )");

            for (ContactItem ci : task.Contacts) {
                llContacts.addView(ci.getView(context));
            }
        } else {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("No Contacts ");
        }

        // Showing Messages
        if (task.Messages.size() > 0) {
            TextView tvMessages = (TextView) findViewById(R.id.tvMessages);
            tvMessages.setText("Messages (" + task.Messages.size() + " )");

            for (MessageItem mi : task.Messages) {
                llMessages.addView(mi.getView(context));
            }
        } else {
            TextView tvMessages = (TextView) findViewById(R.id.tvMessages);
            tvMessages.setText("No Messages");

        }
    }

    @Override
    public void finish() {
        currentIntent.putExtra("update", true);
        currentIntent.putExtra("view", "tasks");
        setResult(RESULT_CANCELED, currentIntent);
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskselected_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (task.isTaskClosed()) {
            menu.findItem(R.id.taskSelectedMenuItem).setIcon(R.drawable.ic_close);
        } else {
            menu.findItem(R.id.taskSelectedMenuItem).setIcon(R.drawable.ic_open);
        }
        return true;
    }


    private void onToggleTask() {
        ITaskProvider _pr = new DataProvider();
        if (task.isTaskClosed()) {
            _pr.setTaskCompleted(task.getTaskID());
        } else {
            _pr.setTaskTaked(task.getTaskID());
        }
        currentIntent.putExtra("update", true);
        currentIntent.putExtra("view", "tasks");
        setResult(RESULT_OK, currentIntent);
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                currentIntent.putExtra("update", true);
                currentIntent.putExtra("view", "tasks");
                setResult(RESULT_OK, currentIntent);
                super.finish();
            case R.id.taskSelectedMenuItem:
                onToggleTask();
            default:
                return (super.onOptionsItemSelected(_item));
        }
    }
}