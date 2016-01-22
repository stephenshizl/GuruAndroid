package com.fanxin.app.fx.others;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dcs.leef.wechat.R;

@SuppressLint({ "SdCardPath", "InflateParams" })
public class ChatRoomAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;

    public ChatRoomAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        convertView = creatConvertView(1);
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_name;
        ImageView iv_avatar1;
        ImageView iv_avatar2;
        ImageView iv_avatar3;
        ImageView iv_avatar4;
        ImageView iv_avatar5;

    }

    private View creatConvertView( int size) {
        View convertView;
        switch (size) {
        case 1:
            convertView = inflater.inflate(R.layout.item_chatroom_1, null,
                    false);

            break;
        case 2:
            convertView = inflater.inflate(R.layout.item_chatroom_2, null,
                    false);
            break;
        case 3:
            convertView = inflater.inflate(R.layout.item_chatroom_3, null,
                    false);
            break;
        case 4:
            convertView = inflater.inflate(R.layout.item_chatroom_4, null,
                    false);
            break;
        case 5:
            convertView = inflater.inflate(R.layout.item_chatroom_5, null,
                    false);
        default:
            convertView = inflater.inflate(R.layout.item_chatroom_5, null,
                    false);
            break;

        }
        return convertView;
    }

}
