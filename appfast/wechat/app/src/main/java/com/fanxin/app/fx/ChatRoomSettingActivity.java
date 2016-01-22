package com.fanxin.app.fx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.activity.FXAlertDialog;
import com.fanxin.app.activity.BaseActivity;
import com.fanxin.app.domain.User;
import com.fanxin.app.fx.others.LoadDataFromServer;
import com.fanxin.app.fx.others.LoadUserAvatar;
import com.fanxin.app.fx.others.LocalUserInfo;
import com.fanxin.app.fx.others.TopUser;
import com.fanxin.app.fx.others.LoadDataFromServer.DataCallBack;
import com.fanxin.app.fx.others.LoadUserAvatar.ImageDownloadedCallBack;
import com.fanxin.app.widget.ExpandGridView;

import cn.dcs.leef.wechat.R;


@SuppressLint({ "SimpleDateFormat", "SdCardPath", "ClickableViewAccessibility",
        "InflateParams" })
public class ChatRoomSettingActivity extends BaseActivity implements
        OnClickListener {
    private TextView tv_groupname;
    // 成员总数

    private TextView tv_m_total;
    // 成员总数
    int m_total = 0;
    // 成员列表
    private ExpandGridView gridview;
    // 修改群名称、置顶、、、、
    private RelativeLayout re_change_groupname;
    private RelativeLayout rl_switch_chattotop;
    private RelativeLayout rl_switch_block_groupmsg;
    private RelativeLayout re_clear;

    // 状态变化
    private ImageView iv_switch_chattotop;
    private ImageView iv_switch_unchattotop;
    private ImageView iv_switch_block_groupmsg;
    private ImageView iv_switch_unblock_groupmsg;
    // 删除并退出

    private Button exitBtn;

    private String hxid;
    // 群名称
    private String group_name;
    // 是否是管理员
    boolean is_admin = false;
    List<User> members = new ArrayList<User>();
    String longClickUsername = null;

    private String groupId;

    private GridAdapter adapter;

    public static ChatRoomSettingActivity instance;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject;
    private JSONArray jsonarray;

    // 置顶列表
    private Map<String, TopUser> topMap = new HashMap<String, TopUser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_groupchatsetting_activity);
        instance = this;
        hxid = LocalUserInfo.getInstance(ChatRoomSettingActivity.this)
                .getUserInfo("hxid");
        initView();
        initData();
    }

    private void initView() {
        progressDialog = new ProgressDialog(ChatRoomSettingActivity.this);
        tv_groupname = (TextView) findViewById(R.id.tv_groupname);

        tv_m_total = (TextView) findViewById(R.id.tv_m_total);

        gridview = (ExpandGridView) findViewById(R.id.gridview);

        re_change_groupname = (RelativeLayout) findViewById(R.id.re_change_groupname);
        rl_switch_chattotop = (RelativeLayout) findViewById(R.id.rl_switch_chattotop);
        rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
        re_clear = (RelativeLayout) findViewById(R.id.re_clear);

        iv_switch_chattotop = (ImageView) findViewById(R.id.iv_switch_chattotop);
        iv_switch_unchattotop = (ImageView) findViewById(R.id.iv_switch_unchattotop);
        iv_switch_block_groupmsg = (ImageView) findViewById(R.id.iv_switch_block_groupmsg);
        iv_switch_unblock_groupmsg = (ImageView) findViewById(R.id.iv_switch_unblock_groupmsg);
        exitBtn = (Button) findViewById(R.id.btn_exit_grp);

    }

    private void initData() {

    }

    // 显示群成员头像昵称的gridview
    @SuppressLint("ClickableViewAccessibility")
    private void showMembers(List<User> members) {
        adapter = new GridAdapter(this, members);
        gridview.setAdapter(adapter);

        // 设置OnTouchListener,为了让群主方便地推出删除模》
        gridview.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (adapter.isInDeleteMode) {
                        adapter.isInDeleteMode = false;
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                    break;
                default:
                    break;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.rl_switch_block_groupmsg: // 屏蔽群组

            break;

        case R.id.re_clear: // 清空聊天记录
            progressDialog.setMessage("正在清空群消息...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            // 按照你们要求必须有个提示，防止记录太少，删得太快，不提示
            break;
        case R.id.re_change_groupname:
            break;
        case R.id.rl_switch_chattotop:
            // 当前状态是已经置顶,点击后取消置顶
            break;

        case R.id.btn_exit_grp:
            break;

        default:
            break;
        }

    }


    /**
     * 群组成员gridadapter
     * 
     * @author admin_new
     * 
     */
    private class GridAdapter extends BaseAdapter {

        public boolean isInDeleteMode;
        private List<User> objects;
        Context context;
        private LoadUserAvatar avatarLoader;

        public GridAdapter(Context context, List<User> objects) {

            this.objects = objects;
            this.context = context;
            isInDeleteMode = false;
        }

        @Override
        public View getView(final int position, View convertView,
                final ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.social_chatsetting_gridview_item, null);
            return convertView;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }



    private void updateGroupName(String groupId, String updateStr) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("groupId", groupId);
        map.put("groupName", updateStr);
        LoadDataFromServer task = new LoadDataFromServer(
                ChatRoomSettingActivity.this, Constant.URL_UPDATE_Groupnanme,
                map);

        task.getData(new DataCallBack() {

            @Override
            public void onDataCallBack(JSONObject data) {
                if (data != null) {
                    int code = data.getInteger("code");

                    if (code != 1) {
                        // 通知管理员。。。

                    }

                }
            }
        });

    }

    public void back(View view) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

}
