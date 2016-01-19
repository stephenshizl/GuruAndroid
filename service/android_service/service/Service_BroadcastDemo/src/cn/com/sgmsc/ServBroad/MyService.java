package cn.com.sgmsc.ServBroad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service{
	MyThread myThread;//线程的引用
	@Override
	public IBinder onBind(Intent intent) {//重写的onBind方法
		return null;
	}
	@Override
	public void onDestroy() {   //重写的onDestroy方法
		myThread.flag = false;  //停止线程运行
		super.onDestroy();
	}
	@Override
	public void onStart(Intent intent, int startId) {//重写onStart方法
		myThread = new MyThread() ;  //初始化线程
		myThread.start();            //启动线程
		super.onStart(intent, startId);
	}	
	
	//定义线程类
	class MyThread extends Thread{  
		boolean flag = true;        //循环标志位
		int c = 0;                  //其值为发送的消息
		@Override
		public void run() {
			while(flag){
				Intent i = new Intent("cn.com.sgmsc.ServBroad.myThread");//创建Intent
				i.putExtra("myThread", c);      //放入数据
				sendBroadcast(i);               //发送广播
				c++;
				try{
					Thread.sleep(1000);        //睡眠指定毫秒数
				}catch(Exception e){           //捕获异常
					e.printStackTrace();       //打印异常
				}
			}
		}
	};
}
