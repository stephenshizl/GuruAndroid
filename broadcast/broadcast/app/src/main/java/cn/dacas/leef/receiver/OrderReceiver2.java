package cn.dacas.leef.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lf on 2016/1/20.
 */
public class OrderReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("OrderReceiver2","receiver");
        Toast.makeText(context, "OrderReceiver2", Toast.LENGTH_SHORT).show();
    }
}
