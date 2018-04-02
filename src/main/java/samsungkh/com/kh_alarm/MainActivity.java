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

    public static final String IS_FIRST = "isFirst";
    public static final String PARAM_MORNING = "M";
    public static final String PARAM_AFTERNOON = "A";
    public static final int MONDAY = 2;
    public static final int FRIDAY = 6;
    public static final int MORNING_TIME = 11;
    public static final int AFTERNOON_TIME = 16;

    ComponentName comp;
    ImageView imgView;
    Button confirmBtn;
    private PendingIntent pendingIntent;
    private AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(this);

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

//       SharedPreferences pref = getSharedPreferences(IS_FIRST, Activity.MODE_PRIVATE);
//       boolean isFirst = pref.getBoolean(IS_FIRST, true);

//       if(isFirst){ //최초 실행시 true 저장
       AlarmManagerUtil.setAlarm (PARAM_MORNING, true);
       AlarmManagerUtil.setAlarm (PARAM_AFTERNOON, true);
//           pref.edit().putBoolean(IS_FIRST,false).apply();
//       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK);

        if(num >= MONDAY && num <= FRIDAY) {
            //요일별 이미지 선택
            switch(num){
                case 2: imgView.setBackgroundResource(R.drawable.sec_1); break;
                case 3: imgView.setBackgroundResource(R.drawable.sec_2); break;
                case 4: imgView.setBackgroundResource(R.drawable.sec_3); break;
                case 5: imgView.setBackgroundResource(R.drawable.sec_4); break;
                case 6: imgView.setBackgroundResource(R.drawable.sec_5); break;
                default:imgView.setBackgroundResource(R.drawable.sec_1); break;
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
        AlarmManagerUtil.setAlarm (PARAM_MORNING, true);
        AlarmManagerUtil.setAlarm (PARAM_AFTERNOON, true);
    }

    @Override
    public void onClick(View view) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int curHour = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);

        if(view == confirmBtn){

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, MainActivity.class); // <- 이 부분도 맞는지 모르겟음

            //cancel alarm
            if(curHour == MORNING_TIME){
                PendingIntent secondRingOfMorningIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent thirdRingOfMorningIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if(secondRingOfMorningIntent != null)
                {
                    manager.cancel(secondRingOfMorningIntent);
                    secondRingOfMorningIntent.cancel();
                }
                if(thirdRingOfMorningIntent != null)
                {
                    manager.cancel(thirdRingOfMorningIntent);
                    thirdRingOfMorningIntent.cancel();
                }

                Toast.makeText(this, "오전 보안알림 체크완료", Toast.LENGTH_SHORT).show();
                AlarmManagerUtil.setAlarm (PARAM_MORNING,false);

            }else if(curHour == AFTERNOON_TIME){
                PendingIntent secondRingOfAfternoonIntent = PendingIntent.getBroadcast(this, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent thirdRingOfAfternoonIntent = PendingIntent.getBroadcast(this, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if(secondRingOfAfternoonIntent != null)
                {
                    manager.cancel(secondRingOfAfternoonIntent);
                    secondRingOfAfternoonIntent.cancel();
                }
                if(thirdRingOfAfternoonIntent != null)
                {
                    manager.cancel(thirdRingOfAfternoonIntent);
                    thirdRingOfAfternoonIntent.cancel();
                }
                Toast.makeText(this, "오후 보안알림 체크완료", Toast.LENGTH_SHORT).show();
                AlarmManagerUtil.setAlarm (PARAM_AFTERNOON, false);
            }
            finish();
        }
    }
}

