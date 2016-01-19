package com.example.android_service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_service.MusicService.MyBinder;

public class MainActivity extends Activity implements OnClickListener {

	private static String TAG = "MusicService";
	MusicService bindService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 输出Toast消息和日志记录
		Toast.makeText(this, "MusicServiceActivity", Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicServiceActivity");

		Button btnStart = (Button) findViewById(R.id.startMusic);
		Button btnStop = (Button) findViewById(R.id.stopMusic);
		Button btnBind = (Button) findViewById(R.id.bindMusic);
		Button btnUnbind = (Button) findViewById(R.id.unbindMusic);
		Button pauseOrStart = (Button) findViewById(R.id.pauseOrStart);
	
		Button starIntentService = (Button) findViewById(R.id.starIntentService);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnBind.setOnClickListener(this);
		btnUnbind.setOnClickListener(this);
		pauseOrStart.setOnClickListener(this);
		starIntentService.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, MusicService.class);
		switch (v.getId()) {
		case R.id.startMusic:
			// 开始服务
			startService(intent);
			break;
		case R.id.stopMusic:
			// 停止服务
			stopService(intent);
			break;
		case R.id.bindMusic:
			// 绑定服务
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
			break;
		case R.id.unbindMusic:
			// 解绑服务
			unbindService(conn);
			break;
		case R.id.pauseOrStart:
			// 通过IBinder对象与服务组件进行交互
			if (bindService!=null){
			 bindService.MyMethod();
			}
			break;
		case R.id.starIntentService:
			startService(new Intent(this,MyIntentService.class));
			break;
		}

	}

	// 定义服务链接对象
	final ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(MainActivity.this,
					"MusicServiceActivity onSeviceDisconnected",
					Toast.LENGTH_SHORT).show();
			Log.e(TAG, "MusicServiceActivity onSeviceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(MainActivity.this,
					"MusicServiceActivity onServiceConnected",
					Toast.LENGTH_SHORT).show();
			MyBinder binder = (MyBinder) service;
			bindService = binder.getService();
			//bindService.MyMethod();
			Log.e(TAG, "MusicServiceActivity onServiceConnected");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
