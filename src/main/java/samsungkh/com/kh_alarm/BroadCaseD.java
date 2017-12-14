package samsungkh.com.kh_alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sangwon on 2017-11-19.
 */

public class BroadCaseD extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("jojo", "BroadCaseD");

        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK);

        context.startService(new Intent(context, MainActivity.class));

        if(num >= 2 && num <= 6){
            //요일별 이미지 선택
            LayoutInflater li = LayoutInflater.from(context);
            View v = li.inflate(R.layout.activity_main, null, true);
            ImageView rootView = v.findViewById(R.id.main_img);
            if(num == 2 || num == 3){
                Log.d("jojo", "BroadCaseD12");
                rootView.setBackgroundResource(R.drawable.sec_1);
            }else if(num == 4 || num == 5){
                Log.d("jojo", "BroadCaseD34");
                rootView.setBackgroundResource(R.drawable.sec_2);
            }else{
                Log.d("jojo", "BroadCaseD5");
                rootView.setBackgroundResource(R.drawable.sec_3);
            }

            Log.d("jojo", String.valueOf(num));

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                    .setContentTitle("보안체크!")
                    .setContentText("퇴근 전 보안체크 하시기 바랍니다. 1) 휴대폰 확인 2) 보안용지 확인 3) 책상정돈 4) PC/서랍시건")
                    .setSmallIcon(R.drawable.warnig_icon);

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

            Intent myIntent = new Intent(context, BroadCaseD.class);

            AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(context);

            GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
            int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
            if(currentHourOfDay < 12){
                AlarmManagerUtil.setOnceAlarm(11,30,PendingIntent.getBroadcast(context, 0, myIntent, 0));
            }else{
                AlarmManagerUtil.setOnceAlarm(16,30,PendingIntent.getBroadcast(context, 1, myIntent, 0));
            }
        }

    }

}

