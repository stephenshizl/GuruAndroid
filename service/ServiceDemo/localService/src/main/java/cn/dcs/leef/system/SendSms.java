package cn.dcs.leef.system;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.dcs.leef.servicedemo.R;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SendSms extends Activity
{
	String number, content;
	Button send;
	SmsManager sManager;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取SmsManager
		sManager = SmsManager.getDefault();
		// 获取程序界面上的两个文本框和按钮
		number = "15511112222";
		content = "hello world";

		// 创建一个PendingIntent对象
		PendingIntent pi = PendingIntent.getActivity(
				SendSms.this, 0, new Intent(), 0);
		// 发送短信
		sManager.sendTextMessage(number,
				null, content, pi, null);

		// 提示短信发送完成
		Toast.makeText(SendSms.this, "短信发送完成", Toast.LENGTH_SHORT).show();
	}
}