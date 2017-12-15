package samsungkh.com.kh_alarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity{

    DevicePolicyManager deviceMgr;
    ComponentName comp;
    ImageView imgView;

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        deviceMgr = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        comp = new ComponentName(this, UninstallDeviceAdminReceiver.class);

       setContentView(R.layout.activity_main);
       imgView = (ImageView) findViewById(R.id.main_img);
       imgView.setBackgroundResource(R.drawable.sec_1);

        if (!deviceMgr.isAdminActive(comp)) {
            Log.d("jojo", "Main :admin is false");
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp);
            intent.putExtra(DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL, comp);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getString(R.string.devicePolicyManagerMessage));
            startActivityForResult(intent, 0);
        } else {
            Log.d("jojo", "Main : admin is true");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK);

        if(num >= 2 && num <= 6) {
            //요일별 이미지 선택
            switch(num){
                case 2: imgView.setBackgroundResource(R.drawable.sec_1); break;
                case 3: imgView.setBackgroundResource(R.drawable.sec_2); break;
                case 4: imgView.setBackgroundResource(R.drawable.sec_3); break;
                case 5: imgView.setBackgroundResource(R.drawable.sec_4); break;
                case 6: imgView.setBackgroundResource(R.drawable.sec_5); break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("jojo", "onActiveResult");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("jojo", "onActiveResult-OK");
        }

        setAlarm1();
        setAlarm2();
    }

    public void setAlarm1(){

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
//        AlarmManagerUtil.setOnceAlarm(11, 10, pendingIntent);

        //test
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);
        AlarmManagerUtil.setOnceAlarm(curHour, curMin+1, pendingIntent);

    }

    public void setAlarm2() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, myIntent, 0);

        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
        AlarmManagerUtil.setOnceAlarm(16, 30, pendingIntent);

    }

}

