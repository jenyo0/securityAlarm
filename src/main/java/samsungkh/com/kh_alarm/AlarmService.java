package samsungkh.com.kh_alarm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by user on 2017-12-06.
 */

public class AlarmService extends IntentService {
    public AlarmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("jojo", "received!!!!");

    }
}
