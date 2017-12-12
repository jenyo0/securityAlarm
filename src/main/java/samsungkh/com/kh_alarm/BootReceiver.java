package samsungkh.com.kh_alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sangwon on 2017-11-19.
 */

public class BootReceiver extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("jojo", "BootReceiver !!");

        if (intent.getAction().equals(INTENT_ACTION)) {

            Intent myIntent = new Intent(context, BroadCaseD.class);

            AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);

            AlarmManagerUtil.setOnceAlarm(11,30, PendingIntent.getBroadcast(context, 0, myIntent, 0));
            AlarmManagerUtil.setOnceAlarm(16,30,PendingIntent.getBroadcast(context, 1, myIntent, 0));
        }
    }

}
