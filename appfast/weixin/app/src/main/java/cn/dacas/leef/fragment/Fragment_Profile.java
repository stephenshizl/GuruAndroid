package cn.dacas.leef.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.dacas.leef.activity.MyCodeActivity;
import cn.dacas.leef.activity.PublicActivity;
import cn.dacas.leef.activity.SettingActivity;
import cn.dacas.leef.common.Utils;
import cn.dacas.leef.weixin.R;


//我
public class Fragment_Profile extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private TextView tvname, tv_accout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_profile,
					null);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	private void initViews() {
		tvname = (TextView) layout.findViewById(R.id.tvname);
		tv_accout = (TextView) layout.findViewById(R.id.tvmsg);
		String id = "100";
		tv_accout.setText(getString(R.string.wechat_id) + "：" + id);
		tvname.setText("name");

	}

	private void setOnListener() {
		layout.findViewById(R.id.view_user).setOnClickListener(this);
		layout.findViewById(R.id.txt_album).setOnClickListener(this);
		layout.findViewById(R.id.txt_collect).setOnClickListener(this);
		layout.findViewById(R.id.txt_money).setOnClickListener(this);
		layout.findViewById(R.id.txt_card).setOnClickListener(this);
		layout.findViewById(R.id.txt_smail).setOnClickListener(this);
		layout.findViewById(R.id.txt_setting).setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			Utils.start_Activity(getActivity(), MyCodeActivity.class);
			break;
		case R.id.txt_album:// 相册
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_collect:// 收藏
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_money:// 钱包
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_card:// 相册
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_smail:// 表情
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_setting:// 设置
			Utils.start_Activity(getActivity(), SettingActivity.class);
			break;
		default:
			break;
		}
	}

}