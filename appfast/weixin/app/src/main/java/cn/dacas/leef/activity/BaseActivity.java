package cn.dacas.leef.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import cn.dacas.leef.common.Utils;
import cn.dacas.leef.dialog.FlippingLoadingDialog;


public abstract class BaseActivity extends Activity {
	protected Activity context;
	protected FlippingLoadingDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		initControl();
		initView();
		initData();
		setListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Utils.finish(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 绑定控件id
	 */
	protected abstract void initControl();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 打开 Activity
	 * 
	 * @param activity
	 * @param cls
	 */
	public void start_Activity(Activity activity, Class<?> cls) {
		Utils.start_Activity(activity, cls);
	}

	/**
	 * 关闭 Activity
	 * 
	 * @param activity
	 */
	public void finish(Activity activity) {
		Utils.finish(activity);
	}

	/**
	 * 判断是否有网络连接
	 */
	public boolean isNetworkAvailable(Context context) {
		return Utils.isNetworkAvailable(context);
	}

	public FlippingLoadingDialog getLoadingDialog(String msg) {
		if (mLoadingDialog == null)
			mLoadingDialog = new FlippingLoadingDialog(this, msg);
		return mLoadingDialog;
	}
}