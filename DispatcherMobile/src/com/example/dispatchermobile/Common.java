package com.example.dispatchermobile;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 08.10.13
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    public static ProgressDialog ProgressBar;

    public static void initializeProgressBar(Activity _parent)
    {
        final ProgressDialog _pd = new ProgressDialog(_parent);
        _pd.setMessage(_parent.getString(R.string.labelLoading));
        _pd.setCancelable(false);
        ProgressBar = _pd;
        ProgressBar.show();
    }

    public static void closeProgressBar()
    {
        ProgressBar.hide();
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return String.format("%02d:%02d:%02d", hour, minute, second); // ЧЧ:ММ:СС - формат времени
    }

    public static <T> T isNull(T objectToCheck, T defaultValue) {
        return objectToCheck==null ? defaultValue : objectToCheck;
    }

    public static final String ACTION_TAKED = "com.example.dispatchermobile.ACTION_SET_TASK_TAKED";
    public static final String ACTION_COMPLETED = "com.example.dispatchermobile.ACTION_SET_TASK_COMPLETED";
    public static final String ACTION_NEW = "com.example.dispatchermobile.ACTION_SET_TASK_NEW";
}
