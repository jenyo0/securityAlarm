package samsungkh.com.kh_alarm;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sangwon on 2017-11-30.
 */

public class RemoveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jojo", "RemoveActivity oncreate.");
        ComponentName cmpName = new ComponentName(this, UninstallDeviceAdminReceiver.class);
        DevicePolicyManager deviceMgr = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(deviceMgr.isAdminActive(cmpName)){
            Toast.makeText(this, R.string.tryAdminInactive, Toast.LENGTH_LONG).show();
            Log.d("jojo", "Rmove :admin is true");
            deviceMgr.removeActiveAdmin(cmpName);
        }else{
            Log.d("jojo", "Remove admin is false");
            Uri uri = Uri.fromParts("package", "samsungkh.com.kh_alarm", null);
            Intent i = new Intent(Intent.ACTION_DELETE,uri );
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(i);

        }
        finish();
    }
}