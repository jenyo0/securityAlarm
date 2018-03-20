package samsungkh.com.kh_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public static void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent, boolean atOnce) {
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
           if(atOnce){
               alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
           }else{
               alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute)+(24*60*60*1000), alarmPendingIntent);
           }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
           if(atOnce) {
               alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
           }else{
               alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute)+(24*60*60*1000), alarmPendingIntent);
           }
        else {
           if (atOnce) {
               alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
           }else{
               alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute)+(24*60*60*1000), alarmPendingIntent);
           }
       }
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

    public static void setAlarm(Context context, String gubun){

        Intent myIntent = new Intent(context, BroadCaseD.class);
        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);

        int randomMin = (int)(Math.random()*10 + 1);
        int startCount,endCount,hour;

        if("M".equals(gubun)){
            startCount = 0;
            endCount = 3;
            hour = 11;
        }else{
            startCount = 3;
            endCount = 6;
            hour = 16;
        }

        //test
//        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
//        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
//        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);

        for (int count = startCount ; count < endCount ;count++){
            AlarmManagerUtil.setOnceAlarm(hour,randomMin + (count%3)*10, PendingIntent.getBroadcast(context, count, myIntent, 0), false);
//            if("M".equals(gubun)){
////                AlarmManagerUtil.setOnceAlarm(10,50+(int)(Math.random()*40 + 1),PendingIntent.getBroadcast(context, 0, myIntent, 0));
//                setOnceAlarm(curHour,curMin+1 + (count%3)*1, PendingIntent.getBroadcast(context, count, myIntent, 0),true);
//            }else{
//                setOnceAlarm(curHour,curMin+1 + (count%3)*1, PendingIntent.getBroadcast(context, count, myIntent, 0),true);
//            }
        }
    }
}
