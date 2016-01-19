package cn.dcs.leef.local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
	private static final String TAG ="LocalService";

	/**
	 * 在 Local Service 中我们直接继承 Binder 而不是 IBinder,因为 Binder 实现了 IBinder 接口，这样我们可以少做很多工作。
	 * @author newcj
	 */
	public class SimpleBinder extends Binder{
		/**
		 * 获取 Service 实例
		 * @return
		 */
		public LocalService getService(){
			return LocalService.this;
		}

		public int add(int a, int b){
			return a + b;
		}
	}

	public SimpleBinder sBinder;

	@Override
	public void onCreate() {
		super.onCreate();
		// 创建 SimpleBinder
		Log.v(TAG,"onCreate");
		sBinder = new SimpleBinder();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 返回 SimpleBinder 对象
		Log.v(TAG,"onBind");
		return sBinder;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG,"onDestroy");
		super.onDestroy();
	}
}