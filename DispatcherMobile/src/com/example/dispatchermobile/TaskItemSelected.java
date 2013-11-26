package com.example.dispatchermobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class TaskItemSelected extends Activity {
    private Intent currentIntent;
    private TextView deliveryTimeTV;
    private TextView addressTV;
    private TextView commentTV;
    private ToggleButton toggleComplete;
    private LinearLayout llContacts;
    private LinearLayout llMessages;
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
            String str = extras.getString("data")  ;
            task = dataProvider.getTasklocal(extras.getString("data"));
            initializeView();
            updateView(task);
        }
    }

    public void initializeView() {
        deliveryTimeTV = (TextView) findViewById(R.id.deliveryTimeTextView);
        addressTV = (TextView) findViewById(R.id.addressTextView);
        commentTV = (TextView) findViewById(R.id.commentTextView);
        toggleComplete = (ToggleButton) findViewById(R.id.toggleCompleteTask);
        llContacts = (LinearLayout) findViewById(R.id.llContacts);
        llMessages = (LinearLayout) findViewById(R.id.llMessages);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public void updateView(TaskItem task) {


        actionBar.setTitle(task.getCompanyName());
        deliveryTimeTV.setText(task.getDeliveryTime());
        addressTV.setText(task.getAddress());
        commentTV.setText(task.getComment());

        if (task.getLastStatus().startsWith("Выполнено")) {
            toggleComplete.setChecked(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(0xFFff957a));
        } else if (task.getLastStatus().startsWith("Принято")) {
            toggleComplete.setChecked(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(0xFFB1FF90));
        }
        else
        {
            toggleComplete.setChecked(false);
        }
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

    public void onToggleClicked(View view) {
        ITaskProvider _pr = new DataProvider();
        boolean on = toggleComplete.isChecked();
        if (on) {

            _pr.setTaskCompleted(task.getTaskID());
            //currentIntent.putExtra("Res", "Checked");

            Toast.makeText(context, "This function is not correct worked yet. " +
                    task.getCompanyName() + " - " +
                    task.getLastStatus() + " - " +
                    task.getLastStatusDate(), Toast.LENGTH_SHORT).show();
        } else {

            _pr.setTaskTaked(task.getTaskID());
            //currentIntent.putExtra("Res", "UnChecked");
            Toast.makeText(context, "This function is not correct worked yet. " +
                    task.getCompanyName() + " - " +
                    task.getLastStatus() + " - " +
                    task.getLastStatusDate(), Toast.LENGTH_SHORT).show();

        }
        currentIntent.putExtra("update", true);
        setResult(RESULT_OK, currentIntent);
        super.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, TaskReader.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(_item));
    }
}