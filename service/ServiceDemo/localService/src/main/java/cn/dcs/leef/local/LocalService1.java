package cn.dcs.leef.local;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalService1 extends Service {
	private static final String TAG ="LocalService1";

	/**
	 * onBind 是 Service 的虚方法，因此我们不得不实现它。
	 * 返回 null，表示客服端不能建立到此服务的连接。
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}
}

