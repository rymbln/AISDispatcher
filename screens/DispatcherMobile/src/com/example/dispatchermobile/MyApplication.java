package com.example.dispatchermobile;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public final class MyApplication extends Application {
    private static DataProvider dataProvider;
    private static Context context;
    private static Activity activity;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
        HttpHelpers.initialize(MyApplication.getAppContext());
        dataProvider = new DataProvider();
        dataProvider.initialize();

    }

    public synchronized static Context getAppContext() {
        return MyApplication.context;
    }

    public static DataProvider getDataProvider()
    {
        if (MyApplication.dataProvider == null)
        {
            MyApplication.dataProvider = new DataProvider();
            dataProvider.initialize();
        }
        return  MyApplication.dataProvider;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        activity = currentActivity;
    }

    public static Activity getCurrentActivity() {
        return activity;
    }
}