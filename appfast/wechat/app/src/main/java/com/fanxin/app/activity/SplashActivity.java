package com.fanxin.app.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;

import com.fanxin.app.fx.LoginActivity;
import com.fanxin.app.fx.MainActivity;
import com.zdp.aseo.content.AseoZdpAseo;

import cn.dcs.leef.wechat.R;

/**
 * 开屏页
 *
 */
public class SplashActivity extends BaseActivity {
 
//	private TextView versionText;
	
	private static final int sleepTime = 2000;

	@Override
	protected void onCreate(Bundle arg0) {
	    final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		super.onCreate(arg0);
		initFile() ;
		 
//		versionText = (TextView) findViewById(R.id.tv_version);

//		versionText.setText(getVersion());

	}

	@Override
	protected void onStart() {
		super.onStart();
//		startActivity(new Intent(SplashActivity.this, LoginActivity.class));
		startActivity(new Intent(SplashActivity.this, MainActivity.class));
		finish();

	}
	
	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号错误";
		}
	}
	 @SuppressLint("SdCardPath")
     public void initFile() {
	  AseoZdpAseo.initBan(this, findViewById(R.id.aseo_brand));
      File dir = new File("/sdcard/fanxin");
         if (!dir.exists()) {
             dir.mkdirs();
         }
     }
}
