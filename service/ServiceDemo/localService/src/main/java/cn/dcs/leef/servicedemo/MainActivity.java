package cn.dcs.leef.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.dcs.leef.local.Constants;
import cn.dcs.leef.local.ForegroundService;
import cn.dcs.leef.local.LocalService;
import cn.dcs.leef.local.LocalService1;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "SERVICE_TEST";
    private ServiceConnection sc;
    private boolean isBind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sc = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v(TAG,"disconnect");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalService.SimpleBinder sBinder = (LocalService.SimpleBinder)service;
                Log.v(TAG, "3 + 5 = " + sBinder.add(3, 5));
                Log.v(TAG, sBinder.getService().toString());
            }
        };
        findViewById(R.id.btnBind).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bindService(new Intent(MainActivity.this, LocalService.class), sc, Context.BIND_AUTO_CREATE);
                isBind = true;
            }
        });
        findViewById(R.id.btnUnbind).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isBind){
                    unbindService(sc);
                    isBind = false;
                }
            }
        });
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startService(new Intent(MainActivity.this, LocalService1.class));
            }
        });
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopService(new Intent(MainActivity.this, LocalService1.class));
            }
        });
        findViewById(R.id.btnStartFore).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, ForegroundService.class);
                intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(intent);
            }
        });
        findViewById(R.id.btnStopFore).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                stopService(new Intent(MainActivity.this, ForegroundService.class));
                Intent intent = new Intent(MainActivity.this, ForegroundService.class);
                intent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(intent);
            }
        });
    }
}
