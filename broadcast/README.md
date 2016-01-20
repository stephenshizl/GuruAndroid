#Android广播关键点解析

###静态注册
```java
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
<uses-permission android:name = "android.permission.RECEIVE_BOOT_COMPLETED" />
<receiver android:name="cn.dacas.leef.receiver.BootReceiver">
		<intent-filter>
			<action android:name="android.intent.action.BOOT_COMPLETED"> </action>
		</intent-filter>
</receiver>
```

###动态注册
```
You cannot receive this through components declared in manifests, only by explicitly registering for it with Context.registerReceiver().
ACTION_BATTERY_LOW, ACTION_BATTERY_OKAY, ACTION_POWER_CONNECTED, and ACTION_POWER_DISCONNECTED
ACTION_TIME_TICK, ACTION_CONFIGURATION_CHANGED, ACTION_SCREEN_OFF, ACTION_SCREEN_ON
```

```java
//短信发送状态广播
private static String SEND_ACTIOIN = "SMS_SEND_ACTION";  
private static String DELIVERED_ACTION = "SMS_DELIVERED_ACTION";

public class mServiceReceiver extends BroadcastReceiver {  
       @Override  
       public void onReceive(Context context, Intent intent) {  
           // TODO Auto-generated method stub  
           if (intent.getAction().equals(SEND_ACTIOIN)) {  
              try {  
                  switch (getResultCode()) {  
                  case Activity.RESULT_OK:  
                     break;  
                  case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
                     break;  
                  case SmsManager.RESULT_ERROR_RADIO_OFF:  
                     break;  
                  case SmsManager.RESULT_ERROR_NULL_PDU:  
                     break;  
                  }  
              } catch (Exception e) {  
                  e.getStackTrace();  
              }  
           } else if (intent.getAction().equals(DELIVERED_ACTION)) {  
              try {  
                  switch (getResultCode()) {  
                  case Activity.RESULT_OK:  
                     break;  
                  case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
                     break;  
                  case SmsManager.RESULT_ERROR_RADIO_OFF:  
                     break;  
                  case SmsManager.RESULT_ERROR_NULL_PDU:  
                     break;  
                  }  
              } catch (Exception e) {  
                  e.getStackTrace();  
              }  
           }  
       }  
    }  
}  	

```

###常见广播
<action android:name="android.provider.Telephony.SMS_RECEIVED"/>


###有序广播

```java
<receiver android:name="cn.dacas.leef.receiver.OrderReceiver1">
	<intent-filter android:priority="2500">
		<action android:name="cn.dcs.leef.receiver.order"> </action>
	</intent-filter>
</receiver>

<receiver android:name="cn.dacas.leef.receiver.OrderReceiver2">
	<intent-filter android:priority="3000">
		<action android:name="cn.dcs.leef.receiver.order"> </action>
	</intent-filter>
</receiver>


/**
* 使用发送有序广播方法，广播优先级才能生效
*/
void testOrderReceiver(){
	Intent intent = new Intent();
	intent.setAction("cn.dcs.leef.receiver.order");
	sendOrderedBroadcast(intent, null);//发送有序广播
}

```


###阶段广播
> abortBroadcast();


##高效框架
- https://github.com/greenrobot/EventBus
- https://github.com/android-cn/android-open-project-demo/tree/master/event-bus-demo
- https://github.com/square/otto
- https://github.com/android-cn/android-open-project-demo/tree/master/otto-demo


#参考
- http://www.jb51.net/article/51560.htm
- http://developer.android.com/reference/android/content/Intent.html#ACTION_BATTERY_CHANGED
- http://glblong.blog.51cto.com/3058613/1211541
- http://blog.csdn.net/woaieillen/article/details/7373090