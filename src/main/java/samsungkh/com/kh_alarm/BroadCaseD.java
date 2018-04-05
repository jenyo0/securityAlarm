package samsungkh.com.kh_alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sangwon on 2017-11-19.
 */

public class BroadCaseD extends BroadcastReceiver {

    public static final String PARAM_MORNING = "M";
    public static final String PARAM_AFTERNOON = "A";
    public static final int MONDAY = 2;
    public static final int FRIDAY = 6;
    public static final int LAST_MIN = 20;
    public static final int MORNING_TIME = 11;
    public static final int AFTERNOON_TIME = 16;

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK);
        if(num >= MONDAY && num <= FRIDAY){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder mBuilder;
//            NotificationCompat.Builder mBuilder;
            if(Build.VERSION.SDK_INT >= 26){
                NotificationChannel mChannel = new NotificationChannel ("androidSecuApp","androidSecuApp",NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel (mChannel);
                mBuilder = new Notification.Builder (context, mChannel.getId ());
            }else{
                mBuilder = new Notification.Builder (context);
            }
//            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
            mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                    .setContentTitle("Check your Security!")
                    .setContentText("퇴근 전 보안체크 하시기 바랍니다. 1) 휴대폰 확인 2) 보안용지 확인 3) 책상정돈 4) PC/서랍시건")
                    .setSmallIcon(R.drawable.lock_256px);

            Intent resultIntent = new Intent(context, MainActivity.class);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity (
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);
            int mNotificationId = 001;
            notificationManager.notify(mNotificationId, mBuilder.build ());
        }

        //Get current time
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);

        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);

        //마지막꺼 울리고나면 다음날꺼 셋팅
       if(curHour == MORNING_TIME && curMin >= LAST_MIN){
           AlarmManagerUtil.setAlarm (PARAM_MORNING, false);
       }else if(curHour == AFTERNOON_TIME && curMin >= LAST_MIN ){
           AlarmManagerUtil.setAlarm (PARAM_AFTERNOON, false);
        }
    }
}

