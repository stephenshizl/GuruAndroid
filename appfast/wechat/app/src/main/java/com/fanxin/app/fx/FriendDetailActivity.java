package com.fanxin.app.fx;

import com.fanxin.app.DemoApplication;
import com.fanxin.app.domain.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dcs.leef.wechat.R;

public class FriendDetailActivity extends Activity {

    String hxid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfrienddetail);
    }
    public void back(View view) {
        finish();
    }

}
