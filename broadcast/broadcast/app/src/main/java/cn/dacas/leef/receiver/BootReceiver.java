package cn.dacas.leef.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lf on 2016/1/20.
 */
public class BootReceiver extends BroadcastReceiver{
    private static final String TAG = "BootReceiver";
    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)){
            Log.i(TAG, "boot...");
        }
    }
}
