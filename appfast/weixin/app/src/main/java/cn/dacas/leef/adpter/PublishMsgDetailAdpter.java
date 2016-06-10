package cn.dacas.leef.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.dacas.leef.activity.WebViewActivity;
import cn.dacas.leef.common.Utils;
import cn.dacas.leef.common.ViewHolder;
import cn.dacas.leef.weixin.R;


//订阅号信息详情页面
public class PublishMsgDetailAdpter extends BaseAdapter {
	protected Context context;

	public PublishMsgDetailAdpter(Context ctx) {
		context = ctx;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (position == 0) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.layout_item_publishmsgdetail, parent, false);
				convertView.setOnClickListener(onclicklister);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.layout_item_publishmsgdetail2, parent, false);
				View layout_msg1 = ViewHolder
						.get(convertView, R.id.layout_msg1);
				View layout_msg2 = ViewHolder
						.get(convertView, R.id.layout_msg2);
				View layout_msg3 = ViewHolder
						.get(convertView, R.id.layout_msg3);
				layout_msg1.setOnClickListener(onclicklister);
				layout_msg2.setOnClickListener(onclicklister);
				layout_msg3.setOnClickListener(onclicklister);
			}
		}

		return convertView;
	}

	private OnClickListener onclicklister = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Utils.start_Activity(
					(Activity) context,
					WebViewActivity.class);
		}
	};
}
