package cn.dacas.leef.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dacas.leef.common.Utils;
import cn.dacas.leef.weixin.Constants;
import cn.dacas.leef.weixin.R;


//好友详情
public class FriendMsgActivity extends BaseActivity implements OnClickListener {
	private TextView txt_title, tv_name, tv_accout;
	private ImageView img_back, img_right;
	private String Name, UserId;
	private Button btn_sendmsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_friendmsg);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initControl() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("详细资料");
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		img_right = (ImageView) findViewById(R.id.img_right);
		img_right.setImageResource(R.drawable.icon_more);
		img_right.setVisibility(View.VISIBLE);
		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
		btn_sendmsg.setTag("1");
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_accout = (TextView) findViewById(R.id.tv_accout);
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		UserId = "100";
		Name = "NAME";
		if (TextUtils.isEmpty(UserId))
			finish();
		else {
			tv_name.setText("user");
			tv_accout.setText("微信号：" + UserId);
		}
	}

	@Override
	protected void setListener() {
		img_back.setOnClickListener(this);
		img_right.setOnClickListener(this);
		btn_sendmsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			Utils.finish(FriendMsgActivity.this);
			break;
		case R.id.img_right:

			break;
		case R.id.btn_sendmsg:
//			Intent intent = new Intent(this, ChatActivity.class);
//			intent.putExtra(Constants.NAME, Name);
//			intent.putExtra(Constants.TYPE, ChatActivity.CHATTYPE_SINGLE);
//			intent.putExtra(Constants.User_ID, UserId);
//			startActivity(intent);
//			overridePendingTransition(R.anim.push_left_in,
//					R.anim.push_left_out);
			break;
		default:
			break;
		}
	}

}
