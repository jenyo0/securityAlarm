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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
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
        Log.d("jojo", "onCreate");
       imgView = (ImageView) findViewById(R.id.main_img);
       imgView.setBackgroundResource(R.drawable.sec_1);

       btn1 = (Button)findViewById(R.id.key_1);
       btn2 = (Button)findViewById(R.id.key_2);
       btn3 = (Button)findViewById(R.id.key_3);
       btn4 = (Button)findViewById(R.id.key_4);
       btn5 = (Button)findViewById(R.id.key_5);
       btn6 = (Button)findViewById(R.id.key_6);
       btn1.setOnClickListener(this);
       btn2.setOnClickListener(this);
       btn3.setOnClickListener(this);
       btn4.setOnClickListener(this);
       btn5.setOnClickListener(this);
       btn6.setOnClickListener(this);


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
        AlarmManagerUtil.setOnceAlarm(11, 10, pendingIntent);

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

    @Override
    public void onClick(View view) {

        if(view == btn1){
            imgView.setBackgroundResource(R.drawable.sec_1);
        }else if(view == btn2){
            imgView.setBackgroundResource(R.drawable.sec_2);
        }else if(view == btn3){
            imgView.setBackgroundResource(R.drawable.sec_3);
        }else if(view == btn4){
            imgView.setBackgroundResource(R.drawable.sec_4);
        }else if(view == btn5){
            imgView.setBackgroundResource(R.drawable.sec_5);
        }else{
            imgView.setBackgroundResource(R.drawable.sec_6);
        }
    }
}

