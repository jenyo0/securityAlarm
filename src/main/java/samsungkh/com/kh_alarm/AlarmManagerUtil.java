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

    public static final String PARAM_MORNING = "M";
    public static final int SCOPE_OF_TIME = 10;
    public static final int GAP_OF_TIME = 10;
    public static final int MORNING_TIME = 11;
    public static final int AFTERNOON_TIME = 16;

    static Context mContext;
    public AlarmManagerUtil(Context mContext){
        AlarmManagerUtil.mContext = mContext;
    }

    public static void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent, boolean atOnce) {
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute, atOnce), alarmPendingIntent);
       } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute, atOnce), alarmPendingIntent);
       }else {
           alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAtMillis(hourOfDay, minute, atOnce), alarmPendingIntent);
       }
    }

    private static long getTriggerAtMillis(int hourOfDay, int minute, boolean atOnce) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);

        if(atOnce) { //처음 등록하는 알람이면 시각 비교 후, 오늘 or 내일 등록
            if (currentHourOfDay < hourOfDay || (currentHourOfDay == hourOfDay && currentMinute < minute))
                return getTimeInMillis(false, hourOfDay, minute);
            else
                return getTimeInMillis(true, hourOfDay, minute);
        }
        else{ //false면 다음날 알람 등록
            return getTimeInMillis(true, hourOfDay, minute);
        }
    }

    private static long getTimeInMillis(boolean tomorrow, int hourOfDay, int minute) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        if (tomorrow) calendar.add(GregorianCalendar.DAY_OF_YEAR, 1);

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        Log.d("kwak","set time : "+String.valueOf(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    public static void setAlarm(String gubun, boolean atOnce){

        Intent myIntent = new Intent(mContext, BroadCaseD.class);
//        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(mContext);

        int randomMin = (int)(Math.random()*SCOPE_OF_TIME);
        int startCount,endCount,hour;

        if(PARAM_MORNING.equals(gubun)){
            startCount = 0;
            endCount = 3;
            hour = MORNING_TIME;
        }else{
            startCount = 3;
            endCount = 6;
            hour =  AFTERNOON_TIME;
        }

        for (int count = startCount ; count < endCount ;count++){
            setOnceAlarm(hour,(randomMin + ((count%3)*GAP_OF_TIME)), PendingIntent.getBroadcast(mContext, count, myIntent, 0), atOnce);
        }
    }
}
