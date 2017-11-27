package samsungkh.com.kh_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("jojo", "onCreate");

        setAlarm1();
        setAlarm2();
    }

    public void setAlarm1(){

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        //오전 알람
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 30, 0);
        Log.d("jojo", calendar.getTime().toString());
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void setAlarm2(){

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, myIntent, 0);

        //오후 알람
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 30, 0);
        Log.d("jojo", calendar.getTime().toString());
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}

