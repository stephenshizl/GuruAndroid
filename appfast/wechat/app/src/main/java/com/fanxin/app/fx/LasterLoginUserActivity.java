package com.fanxin.app.fx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
 
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
 




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.activity.BaseActivity;
import com.fanxin.app.fx.others.AutoListView;
import com.fanxin.app.fx.others.LoadDataFromServer;
import com.fanxin.app.fx.others.AutoListView.OnLoadListener;
import com.fanxin.app.fx.others.AutoListView.OnRefreshListener;
import com.fanxin.app.fx.others.LastLoginAdapter;
import com.fanxin.app.fx.others.LoadDataFromServer.DataCallBack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cn.dcs.leef.wechat.R;

public class LasterLoginUserActivity extends BaseActivity{

    AutoListView autoListView;
    LastLoginAdapter adapter;
 
    String time ="0";
    List<JSONObject> list= new ArrayList<JSONObject>();
    
    int page=0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            List<JSONObject> result = (List<JSONObject>) msg.obj;
            switch (msg.what) {
            case AutoListView.REFRESH:
                autoListView.onRefreshComplete();
                list.clear();
                list.addAll(result);
         
             
                break;
            case AutoListView.LOAD:
                autoListView.onLoadComplete();
                list.addAll(result);
            
                break;
            }
             
            autoListView.setResultSize(result.size());
            adapter.setTime(time);
            DemoApplication.last_time=time;
            DemoApplication.page=page; 
            adapter.notifyDataSetChanged();
        };
    };
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lasterloginuser);
       
        autoListView=(AutoListView)findViewById(R.id.listview);
        
        page=DemoApplication.page;
        time=DemoApplication.last_time;
        adapter=new LastLoginAdapter(list,LasterLoginUserActivity.this,time);     
        autoListView.setAdapter(adapter);

        autoListView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                  if(view.getTag()=="HEADER"||view.getTag().equals("HEADER")||view.getTag()=="FOOTER"||view.getTag().equals("FOOTER")){
                        return;
                    }
                  JSONObject json = list.get(position-1);
                  
                    String hxid=json.getString("hxid");
                    String nick=json.getString("nick");
                    String avatar=json.getString("avatar");
                    
                    String sex=json.getString("sex");
                    
                    Intent intent =new Intent();
                    intent.putExtra("hxid", hxid);
                    intent.putExtra("nick", nick);
                    intent.putExtra("avatar", avatar);
                    intent.putExtra("sex", sex);
            
                    
                    intent.setClass(LasterLoginUserActivity.this, UserInfoActivity.class);
                    startActivity(intent);

            }
            
            
        });
        
        
         
    }

}
