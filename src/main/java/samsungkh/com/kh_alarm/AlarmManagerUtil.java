package samsungkh.com.kh_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by user on 2017-12-04.
 */

public  class AlarmManagerUtil {

    static Context mContext;

    public AlarmManagerUtil(Context mContext){
        AlarmManagerUtil.mContext = mContext;
    }

    public static void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent) {
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
    }

    private static long getTriggerAtMillis(int hourOfDay, int minute) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);

        if (currentHourOfDay < hourOfDay || (currentHourOfDay == hourOfDay && currentMinute < minute))
            return getTimeInMillis(false, hourOfDay, minute);
        else
            return getTimeInMillis(true, hourOfDay, minute);
    }

    private static long getTimeInMillis(boolean tomorrow, int hourOfDay, int minute) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        if (tomorrow)
            calendar.add(GregorianCalendar.DAY_OF_YEAR, 1);

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        Log.d("jojo", String.valueOf(calendar.getTime()));
        return calendar.getTimeInMillis();
    }
}
