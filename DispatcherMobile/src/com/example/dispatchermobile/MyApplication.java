package com.example.DispatcherMobile;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import com.example.DispatcherMobile.models.TaskItem;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class MyApplication extends Application {
    private static DataProvider dataProvider;
    private static Context context;

    private static Activity activity;
    private static Integer notificationCounter = 1;
    private static Intent mDataProviderService;
    public static Boolean isInternetAvailable = false;

    public MyApplication() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        HttpHelpers.initialize(MyApplication.getAppContext());
        dataProvider = new DataProvider();
        dataProvider.initialize();
        mDataProviderService = new Intent(getApplicationContext(), DataProviderService.class);
    }

    public synchronized static Context getAppContext() {
        return MyApplication.context;
    }

    public static DataProvider getDataProvider() {
        if (MyApplication.dataProvider == null) {
            MyApplication.dataProvider = new DataProvider();
            dataProvider.initialize();
        }
        return MyApplication.dataProvider;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        activity = currentActivity;
    }

    public static Activity getCurrentActivity() {
        return activity;

    }

    public static void sendMessage() {
        Intent intent = new Intent("my-event");
        // add data
        intent.putExtra("message", "update");
        LocalBroadcastManager.getInstance(MyApplication.getAppContext()).sendBroadcast(intent);
    }

    private static boolean getNotificationsEnabled() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getCurrentActivity());
        return sharedPrefs.getBoolean("prefEnableNotifications", false);
    }

    public static void sendNotificationUpdatedData() {
        if (getNotificationsEnabled()) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getCurrentActivity())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Tasks updated")
                        .setContentText("Tasklist was updated from internet")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent resultIntent = new Intent(getCurrentActivity(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getCurrentActivity());
        stackBuilder.addParentStack(TaskItemSelected.class);
        stackBuilder.addNextIntent(resultIntent);

        //PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        getCurrentActivity(),
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
//
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        notificationCounter++;
        int mNotificationId = notificationCounter;
        // Gets an instance of the NotificationManager service

        NotificationManager mNotifyMgr = (NotificationManager) getCurrentActivity().getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }                                     }

    public static void sendNotificationUpdatedTask(TaskItem task) {
        if (getNotificationsEnabled()) {
            StringBuilder str = new StringBuilder();

            str.append(task.getCompanyName());
            str.append("\n");
            str.append(task.getDeliveryTime());
            str.append("\n");
            str.append(task.getAddress());

            int mNotificationId = ++notificationCounter;

            Intent takeIntent = new Intent(getCurrentActivity(), DataProviderService.class);
            takeIntent.setAction(Common.ACTION_TAKED);
            takeIntent.putExtra("data", task.getTaskID());
            takeIntent.putExtra("notificationId", mNotificationId);
            PendingIntent piTake = PendingIntent.getService(getCurrentActivity(), 0, takeIntent, 0);

            Intent closeIntent = new Intent(getCurrentActivity(), DataProviderService.class);
            closeIntent.setAction(Common.ACTION_COMPLETED);
            closeIntent.putExtra("notificationId", mNotificationId);
            closeIntent.putExtra("data", task.getTaskID());
            PendingIntent piClose = PendingIntent.getService(getCurrentActivity(), 0, closeIntent, 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getCurrentActivity())
                            .setDefaults(Notification.DEFAULT_ALL)
                                    //  .setStyle()
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Task updated")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(str))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .addAction(R.drawable.ic_accept_dark,
                                    "Take", piTake)
                            .addAction(R.drawable.ic_closed_dark,
                                    "Complete", piClose);

            Intent resultIntent = new Intent(getCurrentActivity(), TaskItemSelected.class);
            resultIntent.putExtra("data", task.getTaskID());
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getCurrentActivity());
            stackBuilder.addParentStack(TaskItemSelected.class);
            stackBuilder.addNextIntent(resultIntent);

            //PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            getCurrentActivity(),
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
//
            mBuilder.setContentIntent(resultPendingIntent);

            // Sets an ID for the notification

            // Gets an instance of the NotificationManager service

            NotificationManager mNotifyMgr = (NotificationManager) getCurrentActivity().getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.

            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    }

}