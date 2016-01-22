package com.fanxin.app.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;


import com.fanxin.app.fx.MainActivity;
import com.fanxin.app.utils.CommonUtils;

public class BaseActivity extends FragmentActivity {
    private static final int notifiId = 11;
    protected NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
    }

    @Override
    protected void onStart() {
        super.onStart();
        

    }

    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }

}
