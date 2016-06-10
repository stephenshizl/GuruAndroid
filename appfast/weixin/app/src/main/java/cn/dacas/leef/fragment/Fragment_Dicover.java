package cn.dacas.leef.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.dacas.leef.activity.AlbumActivity;
import cn.dacas.leef.activity.PublicActivity;
import cn.dacas.leef.common.Utils;
import cn.dacas.leef.weixin.R;


public class Fragment_Dicover extends Fragment implements OnClickListener {
	// 发现
	private Activity ctx;
	private View layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_dicover,
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
		// TODO Auto-generated method stub

	}

	private void setOnListener() {
		// TODO Auto-generated method stub
		layout.findViewById(R.id.txt_pengyouquan).setOnClickListener(this);
		layout.findViewById(R.id.txt_saoyisao).setOnClickListener(this);
		layout.findViewById(R.id.txt_yaoyiyao).setOnClickListener(this);
		layout.findViewById(R.id.txt_nearby).setOnClickListener(this);
		layout.findViewById(R.id.txt_piaoliuping).setOnClickListener(this);
		layout.findViewById(R.id.txt_shop).setOnClickListener(this);
		layout.findViewById(R.id.txt_game).setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_pengyouquan:// 朋友圈
			Utils.start_Activity(getActivity(), AlbumActivity.class);
			break;
		case R.id.txt_saoyisao:// 扫一扫
			break;
		case R.id.txt_yaoyiyao:
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_nearby:
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_piaoliuping:
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_shop:
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		case R.id.txt_game:
			Utils.start_Activity(getActivity(), PublicActivity.class);
			break;
		default:
			break;
		}
	}
}
