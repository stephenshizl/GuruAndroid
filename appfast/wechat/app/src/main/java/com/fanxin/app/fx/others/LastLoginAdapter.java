package com.fanxin.app.fx.others;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fanxin.app.Constant;
import com.fanxin.app.fx.others.LoadUserAvatar.ImageDownloadedCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dcs.leef.wechat.R;

@SuppressLint("SdCardPath")
public class LastLoginAdapter extends BaseAdapter {
    List<JSONObject> list;
    Context context;
    private LoadUserAvatar avatarLoader;
    String time;

    @SuppressLint("SdCardPath")
    public LastLoginAdapter(List<JSONObject> list, Context context, String time) {
        this.context = context;
        this.list = list;
        this.time = time;
        avatarLoader = new LoadUserAvatar(context, "/sdcard/fanxin/");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_lasterloginuser, null);
        }
        return convertView;
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

    class ViewHolder {

        ImageView img_face;

        TextView tv_time;

        TextView tv_name;

    }

    protected void resetViewHolder(ViewHolder p_ViewHolder) {

        p_ViewHolder.tv_time.setText(null);

        p_ViewHolder.img_face.setImageResource(R.drawable.default_useravatar);
        p_ViewHolder.tv_name.setText(null);

    }

}
