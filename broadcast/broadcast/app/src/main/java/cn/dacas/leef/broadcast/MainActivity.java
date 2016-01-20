package cn.dacas.leef.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ScreenBroadcastReceiver screenBroadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenBroadcastReceiver =  new ScreenBroadcastReceiver();
        registerScreenBroadcastReceiver();

        findViewById(R.id.order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testOrderReceiver();
            }
        });

        findViewById(R.id.battery1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBatteryStatus();
            }
        });
    }

    /*
    You cannot receive this through components declared in manifests, only by explicitly registering for it with Context.registerReceiver().
    ACTION_BATTERY_LOW, ACTION_BATTERY_OKAY, ACTION_POWER_CONNECTED, and ACTION_POWER_DISCONNECTED
    http://developer.android.com/reference/android/content/Intent.html#ACTION_BATTERY_CHANGED
     */
    void testBatteryStatus(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        //你可以读到充电状态,如果在充电，可以读到是usb还是交流电

        // 是否在充电
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        // 怎么充
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        Log.i(TAG, "isCharging=" + isCharging + " ,usbCharge=" + usbCharge + ", acCharge=" + acCharge);
        Toast.makeText(this,"isCharging="+isCharging+" ,usbCharge="+usbCharge+", acCharge="+acCharge, Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用发送有序广播方法，广播优先级才能生效
     */
    void testOrderReceiver(){
        Intent intent = new Intent();
        intent.setAction("cn.dcs.leef.receiver.order");
        sendOrderedBroadcast(intent, null);//发送有序广播
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenBroadcastReceiver);
        Log.i("screenBR", "screenBroadcastReceiver取消注册了");
    }

    private void registerScreenBroadcastReceiver() {
        screenBroadcastReceiver = new ScreenBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//当屏幕锁屏的时候触发
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);//当屏幕解锁的时候触发
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);//当用户重新唤醒手持设备时触发
        registerReceiver(screenBroadcastReceiver, intentFilter);
        Log.i("screenBR", "screenBroadcastReceiver注册了");
    }
    //重写广播
    class ScreenBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strAction = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(strAction)){
                //屏幕锁屏
                Log.i("screenBR", "屏幕锁屏：ACTION_SCREEN_OFF触发");
            }else if (Intent.ACTION_SCREEN_ON.equals(strAction)){
                //屏幕解锁(实际测试效果，不能用这个来判断解锁屏幕事件)
                //【因为这个是解锁的时候触发，而解锁的时候广播还未注册】
                Log.i("screenBR", "屏幕解锁：ACTION_SCREEN_ON触发");
            }else if (Intent.ACTION_USER_PRESENT.equals(strAction)){
                //屏幕解锁(该Action可以通过静态注册的方法注册)
                //在解锁之后触发的，广播已注册
                Log.i("screenBR", "屏幕解锁：ACTION_USER_PRESENT触发");
            }else{
                //nothing
            }
        }
    }

}
