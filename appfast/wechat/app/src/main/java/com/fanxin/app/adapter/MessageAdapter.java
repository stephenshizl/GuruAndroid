package com.fanxin.app.adapter;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import cn.dcs.leef.wechat.R;


@SuppressLint({ "SdCardPath", "InflateParams" })
public class MessageAdapter extends BaseAdapter {

    private final static String TAG = "msg";

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 12;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 13;

    public static final String IMAGE_DIR = "chat/image/";
    public static final String VOICE_DIR = "chat/audio/";
    public static final String VIDEO_DIR = "chat/video";

    private String username;
    private LayoutInflater inflater;
    private Activity activity;

    private Context context;

    private Map<String, Timer> timers = new Hashtable<String, Timer>();

    public MessageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (Activity) context;
    }

    /**
     * 获取item数
     */
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        notifyDataSetChanged();
    }


    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("InflateParams")
    private View createViewByMessage(int position) {
        View convertView = null;
        if(position == 0) {
            convertView = inflater.inflate(R.layout.row_received_location, null);
        }else{
            convertView = inflater.inflate(R.layout.row_sent_picture, null);
        }
//        inflater.inflate(R.layout.row_sent_location, null);
//        inflater.inflate(R.layout.row_received_picture, null);
//        inflater.inflate(R.layout.row_sent_picture, null);inflater
//                    .inflate(R.layout.row_received_voice, null); inflater
//                    .inflate(R.layout.row_sent_voice, null);inflater
//                    .inflate(R.layout.row_received_video, null);inflater
//                    .inflate(R.layout.row_sent_video, null); inflater
//                    .inflate(R.layout.row_received_file, null); inflater
//                    .inflate(R.layout.row_sent_file, null);inflater
//                        .inflate(R.layout.row_received_voice_call, null)
//                        ;inflater.inflate(R.layout.row_sent_voice_call, null); inflater
//                    .inflate(R.layout.row_received_message, null); inflater
//                    .inflate(R.layout.row_sent_message, null);
        return convertView;
    }

    @SuppressLint("NewApi")
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = createViewByMessage(position);
        return convertView;
    }

    public static class ViewHolder {
        ImageView iv;
        TextView tv;
        ProgressBar pb;
        ImageView staus_iv;
        ImageView head_iv;
        TextView tv_userId;
        ImageView playBtn;
        TextView timeLength;
        TextView size;
        LinearLayout container_status_btn;
        LinearLayout ll_container;
        ImageView iv_read_status;
        // 显示已读回执状态
        TextView tv_ack;
        // 显示送达回执状态
        TextView tv_delivered;

        TextView tv_file_name;
        TextView tv_file_size;
        TextView tv_file_download_state;
    }


}