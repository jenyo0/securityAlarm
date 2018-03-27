package samsungkh.com.kh_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sangwon on 2017-11-19.
 */

public class BootReceiver extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    public static final String PARAM_MORNING = "M";
    public static final String PARAM_AFTERNOON = "A";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("jojo", "BootReceiver !!");

        if (intent.getAction().equals(INTENT_ACTION)) {

            Intent myIntent = new Intent(context, BroadCaseD.class);

            AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);

            AlarmManagerUtil.setAlarm (PARAM_MORNING);
            AlarmManagerUtil.setAlarm (PARAM_AFTERNOON);
        }
    }

}

