package cn.dacas.leef.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.dacas.leef.weixin.R;


//群设置
public class GroupSettingActivity extends BaseActivity implements
		OnClickListener {
	private ImageView img_back;
	private TextView tv_groupname;
	private TextView txt_title;// 标题，成员总数
	int m_total = 0;// 成员总数
	// 修改群名称、置顶、、、、
	private RelativeLayout re_change_groupname;
	private RelativeLayout rl_switch_chattotop;
	private RelativeLayout rl_switch_block_groupmsg;
	private RelativeLayout re_clear;

	// 状态变化
	private CheckBox check_top, check_closetip;
	// 删除并退出

	private Button exitBtn;
	private String hxid;
	private String group_name;// 群名称
	boolean is_admin = false;// 是否是管理员
	String longClickUsername = null;

	private String groupId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_groupsetting);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initControl() {

	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void setListener() {

	}

	@Override
	public void onClick(View v) {

	}
}
