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

public class MainActivity extends AppCompatActivity {

    DevicePolicyManager deviceMgr;
    ComponentName comp;
    private ComponentName mAdminComponentName;

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        deviceMgr = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        comp = new ComponentName(this, UninstallDeviceAdminReceiver.class);

        setContentView(R.layout.activity_main);
        Log.d("jojo", "onCreate");

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

        Log.d("jojo", "setAlarm1");
        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
        AlarmManagerUtil.setOnceAlarm(11, 30, pendingIntent);

    }

    public void setAlarm2() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, myIntent, 0);

        Log.d("jojo", "setAlarm1");
        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
        AlarmManagerUtil.setOnceAlarm(16, 30, pendingIntent);

    }
}

