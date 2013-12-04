package com.example.dispatchermobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 04.12.13
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context, "Don't panik but your time is up!!!!.",
        //       Toast.LENGTH_LONG).show();
        // Vibrate the mobile phone
        //Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(2000);
    }
}
