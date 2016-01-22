package com.fanxin.app.fx;

import java.util.List;

import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.activity.BaseActivity;
import com.fanxin.app.domain.InviteMessage;
import com.fanxin.app.domain.User;
import com.fanxin.app.fx.others.NewFriendsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import cn.dcs.leef.wechat.R;

/**
 * 申请与通知
 * 
 */
public class NewFriendsActivity extends BaseActivity {
    private ListView listView;
    List<InviteMessage> msgs;
    NewFriendsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriendsmsg);
        // DemoApplication.getInstance().addActivity(this);

        listView = (ListView) findViewById(R.id.listview);
        TextView et_search = (TextView) findViewById(R.id.et_search);
        TextView tv_add = (TextView) findViewById(R.id.tv_add);
        et_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendsActivity.this,
                        AddFriendsTwoActivity.class));
            }

        });
        tv_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendsActivity.this,
                        AddFriendsOneActivity.class));
            }

        });


        // 设置adapter
        adapter = new NewFriendsAdapter(this, msgs);
        listView.setAdapter(adapter);
    }

   
    public void back(View v) {
        finish();
    }

}
