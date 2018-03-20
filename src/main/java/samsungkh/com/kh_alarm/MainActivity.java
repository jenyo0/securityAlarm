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

       setAlarm("M", true);
       setAlarm("A", true);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("jojo", "onActiveResult-OK");
        }
        setAlarm("M", true);
        setAlarm("A", true);
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

                if(curHour == 11){
                    setAlarm("M",false);
                }else if(curHour == 16){
                    setAlarm("A", false);
                }
            }
            finish();
        }
    }
    private void setAlarm(String gubun, boolean atOnce){

        Intent myIntent;
        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);
        myIntent = new Intent(MainActivity.this, BroadCaseD.class);

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

//        //test
//        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
//        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
//        int curMin = currentCalendar.get(GregorianCalendar.MINUTE);

        for (int count = startCount ; count < endCount ;count++){
            pendingIntent = PendingIntent.getBroadcast(this, count, myIntent, 0);
            AlarmManagerUtil.setOnceAlarm(hour,randomMin + (count%3)*10, pendingIntent, atOnce);
            //test용
//            if("M".equals(gubun)){
//                alarmManagerUtil.setOnceAlarm(curHour,curMin+1 + (count%3)*1, pendingIntent, atOnce);
//            }else{
//                alarmManagerUtil.setOnceAlarm(curHour,curMin+6 + (count%3)*1, pendingIntent, atOnce);
//            }
        }
    }
}

