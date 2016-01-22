package com.fanxin.app.fx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fanxin.app.DemoApplication;
import com.fanxin.app.activity.BaseActivity;
import com.fanxin.app.domain.User;
import com.fanxin.app.fx.others.LoadUserAvatar;
import com.fanxin.app.fx.others.TopUser;
import com.fanxin.app.fx.others.LoadUserAvatar.ImageDownloadedCallBack;

import cn.dcs.leef.wechat.R;

@SuppressLint({ "SimpleDateFormat", "SdCardPath" })
public class ChatSingleSettingActivity extends BaseActivity implements
        OnClickListener {
    // 、置顶、、、、
    private RelativeLayout rl_switch_chattotop;
    private RelativeLayout rl_switch_block_groupmsg;
    private RelativeLayout re_clear;

    // 状态变化
    private ImageView iv_switch_chattotop;
    private ImageView iv_switch_unchattotop;
    private ImageView iv_switch_block_groupmsg;
    private ImageView iv_switch_unblock_groupmsg;

    private String userId;
    private String userNick;
    private String avatar;
    String sex;
    private LoadUserAvatar avatarLoader;
    private List<String> blackList;
    // 置顶列表
    Map<String, TopUser> topMap = new HashMap<String, TopUser>();
    private ProgressDialog progressDialog;
    public static ChatSingleSettingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlechat_setting);
        avatarLoader = new LoadUserAvatar(this, "/sdcard/fanxin/");

        instance = this;
        // 获取传过来的userId
        userId = getIntent().getStringExtra("userId");
        // 资料错误则不显示

        //
        progressDialog = new ProgressDialog(this);
        initView();
        initData();

    }

    private void initView() {

        rl_switch_chattotop = (RelativeLayout) findViewById(R.id.rl_switch_chattotop);
        rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
        re_clear = (RelativeLayout) findViewById(R.id.re_clear);

        iv_switch_chattotop = (ImageView) findViewById(R.id.iv_switch_chattotop);
        iv_switch_unchattotop = (ImageView) findViewById(R.id.iv_switch_unchattotop);
        iv_switch_block_groupmsg = (ImageView) findViewById(R.id.iv_switch_block_groupmsg);
        iv_switch_unblock_groupmsg = (ImageView) findViewById(R.id.iv_switch_unblock_groupmsg);

        // 初始化置顶和免打扰的状态
        if (!blackList.contains(userId)) {
            iv_switch_block_groupmsg.setVisibility(View.INVISIBLE);
            iv_switch_unblock_groupmsg.setVisibility(View.VISIBLE);

        } else {

            iv_switch_block_groupmsg.setVisibility(View.VISIBLE);
            iv_switch_unblock_groupmsg.setVisibility(View.INVISIBLE);

        }
        if (!topMap.containsKey(userId)) {
            // 当前状态是w未置顶
            iv_switch_chattotop.setVisibility(View.INVISIBLE);
            iv_switch_unchattotop.setVisibility(View.VISIBLE);
        } else {
            // 当前状态是置顶
            iv_switch_chattotop.setVisibility(View.VISIBLE);
            iv_switch_unchattotop.setVisibility(View.INVISIBLE);
        }

    }

    private void initData() {

        rl_switch_chattotop.setOnClickListener(this);
        rl_switch_block_groupmsg.setOnClickListener(this);
        re_clear.setOnClickListener(this);

        ImageView iv_avatar = (ImageView) this.findViewById(R.id.iv_avatar);
        TextView tv_username = (TextView) this.findViewById(R.id.tv_username);
        tv_username.setText(userNick);
        iv_avatar.setImageResource(R.drawable.default_useravatar);
        iv_avatar.setTag(avatar);
        if (avatar != null && !avatar.equals("")) {
            Bitmap bitmap = avatarLoader.loadImage(iv_avatar, avatar,
                    new ImageDownloadedCallBack() {

                        @Override
                        public void onImageDownloaded(ImageView imageView,
                                Bitmap bitmap) {
                            if (imageView.getTag() == avatar) {
                                imageView.setImageBitmap(bitmap);

                            }
                        }

                    });

            if (bitmap != null) {

                iv_avatar.setImageBitmap(bitmap);

            }

        }
        iv_avatar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatSingleSettingActivity.this,
                        UserInfoActivity.class).putExtra("hxid", userId)
                        .putExtra("nick", userNick).putExtra("avatar", avatar)
                        .putExtra("sex", sex));

            }

        });
        ImageView iv_avatar2 = (ImageView) this.findViewById(R.id.iv_avatar2);
        iv_avatar2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatSingleSettingActivity.this,
                        CreatChatRoomActivity.class).putExtra("userId", userId));

            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.rl_switch_block_groupmsg: // 设置免打扰
            progressDialog.setMessage("正在设置免打扰...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if (iv_switch_block_groupmsg.getVisibility() == View.VISIBLE) {
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        progressDialog.dismiss();

                    }

                }, 2000);

            } else {
            }
            break;

        case R.id.re_clear: // 清空聊天记录
            progressDialog.setMessage("正在清空消息...");
            progressDialog.show();
            // 按照你们要求必须有个提示，防止记录太少，删得太快，不提示
            break;

        case R.id.rl_switch_chattotop:


            break;

        default:
            break;
        }

    }


    public void back(View v) {
        finish();
    }

}
