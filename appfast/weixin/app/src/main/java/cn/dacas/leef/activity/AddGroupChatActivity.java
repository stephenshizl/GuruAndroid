package cn.dacas.leef.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import cn.dacas.leef.weixin.R;

public class AddGroupChatActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	private ImageView iv_search, img_back;
	private TextView tv_header, txt_title, txt_right;;
	private ListView listView;
	private EditText et_search;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	/** 是否为一个新建的群组 */
	protected boolean isCreatingNewGroup;
	/** 是否为单选 */
	private boolean isSignleChecked;
	/** group中一开始就有的成员 */
	private List<String> exitingMembers = new ArrayList<String>();
	// 可滑动的显示选中用户的View
	private LinearLayout menuLinerLayout;

	// 选中用户总数,右上角显示
	int total = 0;
	private String userId = null;
	private String groupId = null;
	private String groupname;
	// 添加的列表
	private List<String> addList = new ArrayList<String>();
	private String hxid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_chatroom);
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
	protected void onDestroy() {
		super.onDestroy();
		mWindowManager.removeView(mDialogText);
	}


	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
