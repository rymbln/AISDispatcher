package com.example.dispatchermobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TaskItemSelected extends Activity {
    private Intent currentIntent;
    private TextView companyNameTV;
    private TextView deliveryTimeTV;
    private TextView addressTV;
    private TextView commentTV;
    private TextView lastStatusTV;
    private TextView lastStatusDateTV;
    private TextView driverNameTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_item_selected);

        currentIntent = getIntent();

        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            TaskItem _item = com.example.dispatchermobile.TaskReader.SelectedTaskItem;
            //  TaskItem _item = extras.getSMTH("taskItem");
            initializeView();
            updateView(_item);
        }


    }

    public void initializeView() {
        companyNameTV = (TextView) findViewById(R.id.companyNameTextView);
        deliveryTimeTV = (TextView) findViewById(R.id.deliveryTimeTextView);
        addressTV = (TextView) findViewById(R.id.addressTextView);
        commentTV = (TextView) findViewById(R.id.commentTextView);
        lastStatusTV = (TextView) findViewById(R.id.lastStatusTextView);
        lastStatusDateTV = (TextView) findViewById(R.id.lastStatusDateTextView);
        driverNameTV = (TextView) findViewById(R.id.driverNameTextView);
    }

    public void updateView(TaskItem _it) {
        TaskItem _item = _it;

        companyNameTV.setText(_item.getCompanyName());
        deliveryTimeTV.setText(_item.getDeliveryTime());
        addressTV.setText(_item.getAddress());
        commentTV.setText(_item.getComment());
        lastStatusTV.setText(_item.getLastStatus());
        lastStatusDateTV.setText(_item.getLastStatusDate());
        driverNameTV.setText(_item.getDriverName());
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        //boolean on = ((ToggleButton) view).isChecked();
        boolean on = true;
        if (on) {
            currentIntent.putExtra("Res", "Checked");
        } else {
            currentIntent.putExtra("Res", "UnChecked");
        }
        setResult(Activity.RESULT_OK, currentIntent);

    }

}