package com.example.DispatcherMobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

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

    public static boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getCurrentActivity().getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

        }
        return false;
    }

    public static boolean isLoginPassCorrected(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getCurrentActivity());
        String strLogin = sharedPrefs.getString("prefUsername", "NULL");
        String strPass =  sharedPrefs.getString("prefUserpass", "NULL");
        if (strLogin.equals("NULL") || strPass.equals("NULL") || strLogin.equals("") || strPass.equals(""))
        {
            return false;
        }
        else
        {
        return true;
        }
    }

    public static boolean isCorrectEnteredLoginPassword(String login, String pass)
    {
        return true;
    }

    // Save the active screen: TASKS = 1 ; COMPANIES = 2
    public static int ACTIVE_SCREEN = 1;

    public static final String ACTION_TAKED = "com.example.DispatcherMobile.ACTION_SET_TASK_TAKED";
    public static final String ACTION_COMPLETED = "com.example.DispatcherMobile.ACTION_SET_TASK_COMPLETED";
    public static final String ACTION_NEW = "com.example.DispatcherMobile.ACTION_SET_TASK_NEW";

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int ACTION_SWIPE_TO_LEFT = 1;
    private static final int ACTION_SWIPE_TO_RIGHT = 2;
    private static final int ACTION_SWIPE_TO_UP = 3;
    private static final int ACTION_SWIPE_TO_DOWN = 4;

}
