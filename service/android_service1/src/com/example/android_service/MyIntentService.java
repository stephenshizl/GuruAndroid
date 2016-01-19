package com.example.android_service;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
	private static String TAG = "MusicService";

	public MyIntentService() {
		super("MyIntentServiceName");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker
		// thread(线程)来处理队列中的Intent
		// 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
		try {
			int time = 20000;
			Log.e(TAG, "begin sleep" + time);
			Thread.sleep(time);
			Log.e(TAG, "after sleep" + time);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}