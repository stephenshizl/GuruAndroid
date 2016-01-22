package com.fanxin.app.fx.others;

import java.util.Date;


import com.fanxin.app.Constant;
import com.fanxin.app.fx.ChatActivity;
import com.fanxin.app.fx.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

import cn.dcs.leef.wechat.R;

@SuppressLint("InflateParams")
public class ConversationAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private LoadUserAvatar avatarLoader;
    private Context context;

    public ConversationAdapter(Context context){
        this.context = context;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        ViewHolder holder = new ViewHolder();

        convertView = creatConvertView(1);

  /*
        // // 单聊对话membersNum
        // creatConvertView(convertView, parent, membersNum);
        // Log.e("membersNum",String.valueOf(membersNum));
        // 初始化控件
        // 昵称
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        // 未读消息
        holder.tv_unread = (TextView) convertView.findViewById(R.id.tv_unread);
        // 最近一条消息
        holder.tv_content = (TextView) convertView
                .findViewById(R.id.tv_content);
        // 时间
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        // 发送状态

        holder.msgState = (ImageView) convertView.findViewById(R.id.msg_state);

        // 显示昵称
        holder.tv_name.setText("lucy");
        // 显示头像
        showUserAvatar(holder.iv_avatar, avatar);

        holder.iv_avatar1 = (ImageView) convertView
                .findViewById(R.id.iv_avatar1);
        holder.iv_avatar2 = (ImageView) convertView
                .findViewById(R.id.iv_avatar2);
        holder.iv_avatar3 = (ImageView) convertView
                .findViewById(R.id.iv_avatar3);
        holder.iv_avatar4 = (ImageView) convertView
                .findViewById(R.id.iv_avatar4);
        holder.iv_avatar5 = (ImageView) convertView
                .findViewById(R.id.iv_avatar5);

        // 显示与此用户的消息未读数
        holder.tv_unread.setText("5");
        holder.tv_unread.setVisibility(View.VISIBLE);


        holder.tv_content.setText("content...");
        // SmileUtils.getSmiledText()

        holder.tv_time.setText("昨天 23:51");

        holder.msgState.setVisibility(View.VISIBLE);

*/
        RelativeLayout re_parent = (RelativeLayout) convertView
                .findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入聊天页面
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private View creatConvertView(int size) {
        View convertView;

        if (size == 0) {
            convertView = inflater.inflate(R.layout.item_conversation_single,
                    null, false);
        } else if (size == 1) {
            convertView = inflater.inflate(R.layout.item_conversation_group1,
                    null, false);

        } else if (size == 2) {
            convertView = inflater.inflate(R.layout.item_conversation_group2,
                    null, false);

        } else if (size == 3) {
            convertView = inflater.inflate(R.layout.item_conversation_group3,
                    null, false);

        } else if (size == 4) {
            convertView = inflater.inflate(R.layout.item_conversation_group4,
                    null, false);

        } else if (size > 4) {
            convertView = inflater.inflate(R.layout.item_conversation_group5,
                    null, false);

        } else {
            convertView = inflater.inflate(R.layout.item_conversation_group5,
                    null, false);

        }

        return convertView;
    }

    private static class ViewHolder {
        /** 和谁的聊天记录 */
        TextView tv_name;
        /** 消息未读数 */
        TextView tv_unread;
        /** 最后一条消息的内容 */
        TextView tv_content;
        /** 最后一条消息的时间 */
        TextView tv_time;
        /** 用户头像 */
        ImageView iv_avatar;
        ImageView iv_avatar1;
        ImageView iv_avatar2;
        ImageView iv_avatar3;
        ImageView iv_avatar4;
        ImageView iv_avatar5;
        ImageView msgState;

    }

    private void showUserAvatar(ImageView iamgeView, String avatar) {
        final String url_avatar = Constant.URL_Avatar + avatar;
        iamgeView.setTag(url_avatar);
        if (url_avatar != null && !url_avatar.equals("")) {
            Bitmap bitmap = avatarLoader.loadImage(iamgeView, url_avatar,
                    new ImageDownloadedCallBack() {

                        public void onImageDownloaded(ImageView imageView,
                                Bitmap bitmap) {
                            if (imageView.getTag() == url_avatar) {
                                imageView.setImageBitmap(bitmap);

                            }
                        }

                    });
            if (bitmap != null)
                iamgeView.setImageBitmap(bitmap);

        }
    }


    String getStrng(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    private void showMyDialog(String title) {

        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        Window window = dlg.getWindow();

        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);

        window.findViewById(R.id.ll_title).setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(title);

        TextView tv_content1 = (TextView) window.findViewById(R.id.tv_content1);
        final String username = "username";
        // 是否已经置顶
        tv_content1.setText("置顶聊天");
//        dlg.cancel();

    }

}
