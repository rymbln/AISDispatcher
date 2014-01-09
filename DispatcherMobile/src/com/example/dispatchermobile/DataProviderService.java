package com.example.DispatcherMobile;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 06.12.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class DataProviderService extends IntentService {

    public DataProviderService() {

        // The super call is required. The background thread that IntentService
        // starts is labeled with the string argument you pass.
        super("com.example.android.dataproviderservice");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        String taskID = intent.getExtras().getString("data", "");
        Integer notificationId = Common.isNull(intent.getExtras().getInt("notificationId"), 0);
        NotificationManager mNotifyMgr = (NotificationManager) MyApplication.getCurrentActivity().getSystemService(NOTIFICATION_SERVICE);
        // This section handles the 3 possible actions:
        // ping, snooze, and dismiss.
        if (!taskID.equals("")) {
            if (action.equals(Common.ACTION_NEW)) {
                MyApplication.getDataProvider().setTaskCreated(taskID);
                if (notificationId > 0)
                    mNotifyMgr.cancel(notificationId);

            } else if (action.equals(Common.ACTION_TAKED)) {
                MyApplication.getDataProvider().setTaskTaked(taskID);
                if (notificationId > 0)
                    mNotifyMgr.cancel(notificationId);
            } else if (action.equals(Common.ACTION_COMPLETED)) {
                MyApplication.getDataProvider().setTaskCompleted(taskID);
                if (notificationId > 0)
                    mNotifyMgr.cancel(notificationId);
            }
        }
    }
}
