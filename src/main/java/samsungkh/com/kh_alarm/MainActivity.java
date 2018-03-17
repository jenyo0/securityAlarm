package samsungkh.com.kh_alarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ComponentName comp;
    ImageView imgView;
    Button confirmBtn;
    private PendingIntent pendingIntent;
//    private PendingIntent[] sender;

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        comp = new ComponentName(this, UninstallDeviceAdminReceiver.class);

       setContentView(R.layout.activity_main);
       imgView = (ImageView) findViewById(R.id.main_img);
       imgView.setBackgroundResource(R.drawable.sec_1);

       confirmBtn = (Button) findViewById(R.id.button_confirm);
       confirmBtn.setOnClickListener(this);

//        if (!deviceMgr.isAdminActive(comp)) {
//            Log.d("jojo", "Main :admin is false");
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp);
//            intent.putExtra(DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL, comp);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.devicePolicyManagerMessage));
//            startActivityForResult(intent, 0);
//        } else {
//            Log.d("jojo", "Main : admin is true");
//        }

       setAlarm("M", true);
       setAlarm("A", true);

//       setAlarm1();
//       setAlarm2();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("jojo", "Main :onResume()");

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

        Log.d("jojo", "Main :onActivityResult()");

        setAlarm("M", true);
        setAlarm("A", true);
    }

    private void setAlarm(String gubun, boolean atOnce){

        Log.d("jojo", "IN setAlarm()");

        Intent myIntent;
        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
        myIntent = new Intent(MainActivity.this, BroadCaseD.class);
//        sender = new PendingIntent[6];

        int randomMin = (int)(Math.random()*10 + 1);
        int startCount,endCount,hour;

        if("M".equals(gubun)){
            Log.d("jojo", "Main :setAlarm() : M");
            startCount = 0;
            endCount = 3;
            hour = 11;
        }else{
            Log.d("jojo", "Main :setAlarm() : A");
            startCount = 3;
            endCount = 6;
            hour = 16;
        }

//        //test
//        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
//        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
//        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);

        for (int count = startCount ; count < endCount ;count++){
            pendingIntent = PendingIntent.getBroadcast(this, count, myIntent, 0);
            AlarmManagerUtil.setOnceAlarm(hour,randomMin + (count%3)*10, pendingIntent, atOnce);
            //test용
//            if("M".equals(gubun)){
//                alarmManagerUtil.setOnceAlarm(curHour,curMin+1 + (count%3)*1, pendingIntent);
//            }else{
//                alarmManagerUtil.setOnceAlarm(curHour,curMin+5 + (count%3)*1, pendingIntent);
//            }
        }
    }

    @Override
    public void onClick(View view) {

        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);

        if(view == confirmBtn){

            if (curHour == 11 || curHour == 16) {
                //cancel alarm
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
                Toast.makeText(this, "보안알림 체크완료", Toast.LENGTH_SHORT).show();

                //reset alarm
                setAlarm("M", false);
                setAlarm("A", false);
            }
            finish();
        }
    }
}

