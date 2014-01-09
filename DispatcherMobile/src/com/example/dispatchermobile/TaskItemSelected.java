package com.example.DispatcherMobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.*;
import android.widget.*;
import com.example.DispatcherMobile.models.ContactItem;
import com.example.DispatcherMobile.models.MessageItem;
import com.example.DispatcherMobile.models.TaskItem;


public class TaskItemSelected extends Activity {
    private Intent currentIntent;
    private ToggleButton toggleComplete;
    private LinearLayout llContacts;
    private LinearLayout llMessages;
    private LinearLayout llMain;
    private TaskItem task = new TaskItem();
    private ActionBar actionBar;
    private EditText edtMessageText;
    private ScrollView scrollView;
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskselected_main);
        OnSwipeTouchListener swipeDetector = new OnSwipeTouchListener();
        RelativeLayout rlMain = (RelativeLayout) this.findViewById(R.id.rlTaskSelectedMain);
        rlMain.setOnTouchListener(swipeDetector);
        currentIntent = getIntent();
        context = this;

        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            task = MyApplication.getDataProvider().getTasklocal(extras.getString("data"));
            initializeView();
            updateView(task);
        }

    }

//    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_MIN_DISTANCE = 120;
//        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2,
//                               float velocityX, float velocityY) {
//
//            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
//                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                //From Right to Left
//                return true;
//            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
//                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                //From Left to Right
//                return true;
//            }
//
//            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
//                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                //From Bottom to Top
//                return true;
//            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
//                    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                //From Top to Bottom
//                return true;
//            }
//            return false;
//        }
//        @Override
//        public boolean onDown(MotionEvent e) {
//            //always return true since all gestures always begin with onDown and<br>
//            //if this returns false, the framework won't try to pick up onFling for example.
//            return true;
//        }
//    }
//@Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if (gestureDetector.onTouchEvent(event)) {
//          Toast.makeText(this,"111",Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return false;
//    }


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
        scrollView = (ScrollView) findViewById(R.id.scrlTaskSelected);
        edtMessageText = (EditText) findViewById(R.id.edtNewMessage);
        Button btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendChatMessage().execute();
            }
        });

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    /**
     * Async task to send chat message to server
     * *
     */
    private class SendChatMessage extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            MyApplication.getDataProvider().setMessageToTask(task.getTaskID(), edtMessageText.getText().toString());
            //  updateView(task);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            scrollView.post(new Runnable() {

                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    public void updateView(TaskItem task) {

        actionBar.setTitle(task.getCompanyName());
        actionBar.setIcon(R.drawable.ic_task);
        edtMessageText.setText("");
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
            tvContacts.setText("Contacts (" + task.Contacts.size() + ")");

            for (ContactItem ci : task.Contacts) {
                llContacts.addView(ci.getView(context));
            }
        } else {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("No Contacts ");
        }

        // Showing Messages
        llMessages.removeAllViews();
        if (task.Messages.size() > 0) {
            TextView tvMessages = (TextView) findViewById(R.id.tvMessages);
            tvMessages.setText("Messages (" + task.Messages.size() + ")");

            for (MessageItem mi : task.Messages) {
                llMessages.addView(mi.getView(context));
            }
        } else {
            TextView tvMessages = (TextView) findViewById(R.id.tvMessages);
            tvMessages.setText("No Messages");

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));
    }

    // handler for received Intents for the "my-event" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            if (message.equals("update")) {
                updateView(MyApplication.getDataProvider().getTasklocal(task.getTaskID()));
            }
            // Log.d("receiver", "Got message: " + message);
        }
    };

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    public void finish() {
//        currentIntent.putExtra("update", true);
//        currentIntent.putExtra("view", "tasks");
//        setResult(RESULT_OK, currentIntent);
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
        if (!task.isTaskClosed()) {
            MyApplication.getDataProvider().setTaskCompleted(task.getTaskID());
        } else {
            MyApplication.getDataProvider().setTaskTaked(task.getTaskID());
        }
        finish();
        MyApplication.sendNotificationUpdatedTask(task);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.taskSelectedMenuItem:
                onToggleTask();
                break;
        }
        return (super.onOptionsItemSelected(_item));
    }
}

