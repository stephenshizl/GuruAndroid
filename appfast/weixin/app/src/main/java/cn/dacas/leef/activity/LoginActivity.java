package cn.dacas.leef.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dacas.leef.common.Utils;
import cn.dacas.leef.weixin.R;


//登陆
public class LoginActivity extends BaseActivity implements OnClickListener {
	private TextView txt_title;
	private ImageView img_back;
	private Button btn_login, btn_register;
	private EditText et_usertel, et_password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initControl() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("登陆");
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setVisibility(View.VISIBLE);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_qtlogin);
		et_usertel = (EditText) findViewById(R.id.et_usertel);
		et_password = (EditText) findViewById(R.id.et_password);
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
	}

	@Override
	protected void setListener() {
		img_back.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		findViewById(R.id.tv_wenti).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.img_back:
				Utils.finish(LoginActivity.this);
				break;
			case R.id.tv_wenti:
				Utils.start_Activity(LoginActivity.this, WebViewActivity.class);
				break;
			case R.id.btn_qtlogin:
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				break;
			case R.id.btn_login:
				break;
			default:
				break;
		}
	}







}
