package samsungkh.com.kh_alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sangwon on 2017-11-19.
 */

public class BroadCaseD extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK);
        if(num >= 2 && num <= 6){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                    .setContentTitle("Check your Security!")
                    .setContentText("퇴근 전 보안체크 하시기 바랍니다. 1) 휴대폰 확인 2) 보안용지 확인 3) 책상정돈 4) PC/서랍시건")
                    .setSmallIcon(R.drawable.lock_256px);

            Intent resultIntent = new Intent(context, MainActivity.class);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);
            int mNotificationId = 001;
            notificationManager.notify(mNotificationId, mBuilder.build());

        }

        //Get current time
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);

       if(curHour == 11  && curMin >= 20){
            AlarmManagerUtil.setAlarm(context, "M");
       }else if(curHour == 16 && curMin >= 20){
            AlarmManagerUtil.setAlarm(context, "A");
        }
    }

//    private void setAlarm(Context context, String gubun){
//
//        Intent myIntent = new Intent(context, BroadCaseD.class);
//        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);
//
//        int randomMin = (int)(Math.random()*10 + 1);
//        int startCount,endCount,hour;
//
//        if("M".equals(gubun)){
//            startCount = 0;
//            endCount = 3;
//            hour = 11;
//        }else{
//            startCount = 3;
//            endCount = 6;
//            hour = 16;
//        }
//
//        //test
//        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
//        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
//        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);
//
//        for (int count = startCount ; count < endCount ;count++){
//            AlarmManagerUtil.setOnceAlarm(hour,randomMin + (count%3)*10, PendingIntent.getBroadcast(context, count, myIntent, 0), false);
////            if("M".equals(gubun)){
//////                AlarmManagerUtil.setOnceAlarm(10,50+(int)(Math.random()*40 + 1),PendingIntent.getBroadcast(context, 0, myIntent, 0));
////                alarmManagerUtil.setOnceAlarm(curHour,curMin+1 + (count%3)*1, PendingIntent.getBroadcast(context, count, myIntent, 0));
////            }else{
////                alarmManagerUtil.setOnceAlarm(curHour,curMin+1 + (count%3)*1, PendingIntent.getBroadcast(context, count, myIntent, 0));
////            }
//        }
//    }
}

