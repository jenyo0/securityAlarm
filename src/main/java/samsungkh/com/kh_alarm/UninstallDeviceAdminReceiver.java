package samsungkh.com.kh_alarm;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sangwon on 2017-11-30.
 */

public class UninstallDeviceAdminReceiver extends DeviceAdminReceiver {

    ComponentName comp;

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, R.string.deviceAdminEnabled, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d("jojo", "onDisabled");
        super.onDisabled(context, intent);

        super.onEnabled(context, intent);
//        Toast.makeText(context, R.string.disableDeviceAdmin, Toast.LENGTH_LONG).show();
//        Uri uri = Uri.fromParts("package", "samsungkh.com.kh_alarm", null);
//        Intent i = new Intent(Intent.ACTION_DELETE,uri );
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
    }
}
