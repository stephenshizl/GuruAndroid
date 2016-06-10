package cn.dacas.leef.weixin;

import java.util.Timer;
import java.util.TimerTask;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.dacas.leef.activity.AddGroupChatActivity;
import cn.dacas.leef.activity.GetMoneyActivity;
import cn.dacas.leef.activity.PublicActivity;
import cn.dacas.leef.common.Utils;
import cn.dacas.leef.dialog.TitleMenu.ActionItem;
import cn.dacas.leef.dialog.TitleMenu.TitlePopup;
import cn.dacas.leef.dialog.WarnTipDialog;
import cn.dacas.leef.fragment.Fragment_Dicover;
import cn.dacas.leef.fragment.Fragment_Friends;
import cn.dacas.leef.fragment.Fragment_Msg;
import cn.dacas.leef.fragment.Fragment_Profile;


public class MainActivity extends FragmentActivity implements OnClickListener {
	private TextView txt_title;
	private ImageView img_right;
	private WarnTipDialog Tipdialog;
	protected static final String TAG = "MainActivity";
	private TitlePopup titlePopup;
	private TextView unreaMsgdLabel;// 未读消息textview
	private TextView unreadAddressLable;// 未读通讯录textview
	private TextView unreadFindLable;// 发现
	private Fragment[] fragments;
	public Fragment_Msg homefragment;
	private Fragment_Friends contactlistfragment;
	private Fragment_Dicover findfragment;
	private Fragment_Profile profilefragment;
	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private String connectMsg = "";;
	private int index;
	private int currentTabIndex;// 当前fragment的index

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initTabView();
		setOnListener();
		initPopWindow();
	}

	private void initTabView() {
		unreaMsgdLabel = (TextView) findViewById(R.id.unread_msg_number);
		unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
		unreadFindLable = (TextView) findViewById(R.id.unread_find_number);
		homefragment = new Fragment_Msg();
		contactlistfragment = new Fragment_Friends();
		findfragment = new Fragment_Dicover();
		profilefragment = new Fragment_Profile();
		fragments = new Fragment[] { homefragment, contactlistfragment,
				findfragment, profilefragment };
		imagebuttons = new ImageView[4];
		imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
		imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
		imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
		imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

		imagebuttons[0].setSelected(true);
		textviews = new TextView[4];
		textviews[0] = (TextView) findViewById(R.id.tv_weixin);
		textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
		textviews[2] = (TextView) findViewById(R.id.tv_find);
		textviews[3] = (TextView) findViewById(R.id.tv_profile);
		textviews[0].setTextColor(0xFF45C01A);
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homefragment)
				.add(R.id.fragment_container, contactlistfragment)
				.add(R.id.fragment_container, profilefragment)
				.add(R.id.fragment_container, findfragment)
				.hide(contactlistfragment).hide(profilefragment)
				.hide(findfragment).show(homefragment).commit();
	}

	public void onTabClicked(View view) {
		img_right.setVisibility(View.GONE);
		switch (view.getId()) {
		case R.id.re_weixin:
			img_right.setVisibility(View.VISIBLE);
			index = 0;
			if (homefragment != null) {
				homefragment.refresh();
			}
			txt_title.setText(R.string.app_name);
			img_right.setImageResource(R.drawable.icon_add);
			break;
		case R.id.re_contact_list:
			index = 1;
			txt_title.setText(R.string.contacts);
			img_right.setVisibility(View.VISIBLE);
			img_right.setImageResource(R.drawable.icon_titleaddfriend);
			break;
		case R.id.re_find:
			index = 2;
			txt_title.setText(R.string.discover);
			break;
		case R.id.re_profile:
			index = 3;
			txt_title.setText(R.string.me);
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		imagebuttons[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		imagebuttons[index].setSelected(true);
		textviews[currentTabIndex].setTextColor(0xFF999999);
		textviews[index].setTextColor(0xFF45C01A);
		currentTabIndex = index;
	}

	private void initPopWindow() {
		// 实例化标题栏弹窗
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		titlePopup.setItemOnClickListener(onitemClick);
		// 给标题栏弹窗添加子类
		titlePopup.addAction(new ActionItem(this, R.string.menu_groupchat,
				R.drawable.icon_menu_group));
		titlePopup.addAction(new ActionItem(this, R.string.menu_addfriend,
				R.drawable.icon_menu_addfriend));
		titlePopup.addAction(new ActionItem(this, R.string.menu_qrcode,
				R.drawable.icon_menu_sao));
		titlePopup.addAction(new ActionItem(this, R.string.menu_money,
				R.drawable.abv));
	}

	private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// 发起群聊
				Utils.start_Activity(MainActivity.this,
						AddGroupChatActivity.class);
				break;
			case 1:// 添加朋友
				Utils.start_Activity(MainActivity.this, PublicActivity.class);
				break;
			case 2:// 扫一扫
				break;
			case 3:// 收钱
				Utils.start_Activity(MainActivity.this, GetMoneyActivity.class);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	private void initViews() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		img_right = (ImageView) findViewById(R.id.img_right);
		// 设置消息页面为初始页面
		img_right.setVisibility(View.VISIBLE);
		img_right.setImageResource(R.drawable.icon_add);
	}

	private void setOnListener() {
		img_right.setOnClickListener(this);

	}

	private int keyBackClickCount = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (keyBackClickCount++) {
			case 0:
				Toast.makeText(this, "再次按返回键退出", Toast.LENGTH_SHORT).show();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						keyBackClickCount = 0;
					}
				}, 3000);
				break;
			case 1:
				finish();
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				break;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_right:
			if (index == 0) {
				titlePopup.show(findViewById(R.id.layout_bar));
			} else {
				Utils.start_Activity(MainActivity.this, PublicActivity.class);
			}
			break;
		default:
			break;
		}
	}

	private void initVersion() {
		// TODO 检查版本更新
		Tipdialog = new WarnTipDialog(this,
				"发现新版本：  1、更新啊阿三达到阿德阿   2、斯顿阿斯顿撒旦？");
		Tipdialog.setBtnOkLinstener(onclick);
		Tipdialog.show();
	}

	private DialogInterface.OnClickListener onclick = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Utils.showLongToast(MainActivity.this, "正在下载...");// TODO
			Tipdialog.dismiss();
		}
	};

}