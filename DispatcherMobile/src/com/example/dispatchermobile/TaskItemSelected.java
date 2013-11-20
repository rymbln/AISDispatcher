package com.example.dispatchermobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TaskItemSelected extends Activity {
    private Intent currentIntent;
    private TextView companyNameTV;
    private TextView deliveryTimeTV;
    private TextView addressTV;
    private TextView commentTV;
    private ToggleButton toggleComplete;
    private ListView contactsListView         ;

    private ContactListAdapter contactsAdapter;

    private TaskItem task;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taskselected_main);



        currentIntent = getIntent();
        context = this;


        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            task = new TaskItem(extras.getString("taskItem"));
//            initializeView();
//            updateView(task);
        }

        task = SharedTask.SelectedTask;
        initializeView();
        updateView(task);

    }

    public void initializeView() {
        companyNameTV = (TextView) findViewById(R.id.companyNameTextView);
        deliveryTimeTV = (TextView) findViewById(R.id.deliveryTimeTextView);
        addressTV = (TextView) findViewById(R.id.addressTextView);
        commentTV = (TextView) findViewById(R.id.commentTextView);
        toggleComplete = (ToggleButton) findViewById(R.id.toggleCompleteTask);

        contactsListView = (ListView) findViewById(R.id.lstContacts);
    }

    public void updateView(TaskItem task) {


        companyNameTV.setText(task.getCompanyName());
        deliveryTimeTV.setText(task.getDeliveryTime());
        addressTV.setText(task.getAddress());
        commentTV.setText(task.getComment());

        if (task.getLastStatus().startsWith("Выполнено")) {
            toggleComplete.setChecked(true);
        } else {
            toggleComplete.setChecked(false);
        }

        contactsAdapter = new ContactListAdapter(this, task.Contacts);
        contactsListView.setAdapter(contactsAdapter);

//        contactsListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });

    }

    public void onToggleClicked(View view) {
        ITaskProvider _pr = new TaskProvider();
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


}