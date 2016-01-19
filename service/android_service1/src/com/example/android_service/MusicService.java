package com.example.android_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
	// 为日志工具设置标签
	private static String TAG = "MusicService";
	// 定义音乐播放器变量
	private MediaPlayer mPlayer;

	private MyBinder myBinder = new MyBinder();
	

	// 该服务不存在需要被创建时被调用，不管startService()还是bindService()都会启动时调用该方法
	@Override
	public void onCreate() {
		Toast.makeText(this, "MusicSevice onCreate()", Toast.LENGTH_SHORT)
				.show();
		Log.e(TAG, "MusicSerice onCreate()");

		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
		// 设置可以重复播放
		mPlayer.setLooping(true);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "MusicSevice onStart()", Toast.LENGTH_SHORT)
				.show();
		Log.e(TAG, "MusicSerice onStartCommand()");

		mPlayer.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "MusicSevice onDestroy()", Toast.LENGTH_SHORT)
				.show();
		Log.e(TAG, "MusicSerice onDestroy()");

		mPlayer.stop();

		super.onDestroy();
	}

	// 其他对象通过bindService 方法通知该Service时该方法被调用
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MusicSevice onBind()", Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onBind()");

		mPlayer.start();
		// return null;
		return myBinder;
	}

	public class MyBinder extends Binder {

		public MusicService getService() {
			return MusicService.this;
		}
	}

	// 其它对象通过unbindService方法通知该Service时该方法被调用
	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MusicSevice onUnbind()", Toast.LENGTH_SHORT)
				.show();
		Log.e(TAG, "MusicSerice onUnbind()");
		mPlayer.stop();
		return super.onUnbind(intent);
	}

	public void MyMethod() {
		if(mPlayer.isPlaying()){
			mPlayer.pause();
			Toast.makeText(this, "mPlayer.pause", Toast.LENGTH_SHORT)
			.show();
		}else{
			mPlayer.start();
			Toast.makeText(this, "mPlayer.start", Toast.LENGTH_SHORT)
			.show();
		}
		Log.i(TAG, "BindService-->MyMethod()");
	}
}
