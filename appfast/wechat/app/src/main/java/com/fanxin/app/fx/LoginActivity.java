 
package com.fanxin.app.fx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 









import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.activity.BaseActivity;
import com.fanxin.app.domain.User;
import com.fanxin.app.fx.others.LoadDataFromServer;
import com.fanxin.app.fx.others.LocalUserInfo;
import com.fanxin.app.fx.others.LoadDataFromServer.DataCallBack;

import cn.dcs.leef.wechat.R;


/**
 * 登陆页面
 * 
 */
public class LoginActivity extends BaseActivity {
    private EditText et_usertel;
    private EditText et_password;
    private Button btn_login;
    private Button btn_qtlogin;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(LoginActivity.this);
        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_qtlogin = (Button) findViewById(R.id.btn_qtlogin);
        // 监听多个输入框

        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());

        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.setMessage("正在登录...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                final String password = et_password.getText().toString().trim();
                String usertel = et_usertel.getText().toString().trim();
                Map<String, String> map = new HashMap<String, String>();

                map.put("usertel", usertel);
                map.put("password", password);
                LoadDataFromServer task = new LoadDataFromServer(
                        LoginActivity.this, Constant.URL_Login, map);

                task.getData(new DataCallBack() {

                    @Override
                    public void onDataCallBack(JSONObject data) {
                        try {
                            int code = data.getInteger("code");
                            if (code == 1) {

                                JSONObject json = data.getJSONObject("user");
                            } else if (code == 2) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "账号或密码错误...", Toast.LENGTH_SHORT)
                                        .show();
                            } else if (code == 3) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "服务器端注册失败...", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,
                                        "服务器繁忙请重试...", Toast.LENGTH_SHORT)
                                        .show();
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "数据解析错误...",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
        btn_qtlogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }

        });
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {

            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;

            if (Sign2 & Sign3) {
                btn_login.setTextColor(0xFFFFFFFF);
                btn_login.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_login.setTextColor(0xFFD0EFC6);
                btn_login.setEnabled(false);
            }
        }

    }

    private void saveMyInfo(JSONObject json) {

        try {
            String hxid = json.getString("hxid");
            String fxid = json.getString("fxid");
            String nick = json.getString("nick");
            String avatar = json.getString("avatar");
            String password = json.getString("password");
            String sex = json.getString("sex");
            String region = json.getString("region");
            String sign = json.getString("sign");
            String tel = json.getString("tel");

            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo(
                    "password", password);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("hxid",
                    hxid);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("fxid",
                    fxid);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("nick",
                    nick);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("avatar",
                    avatar);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("sex",
                    sex);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("region",
                    region);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("sign",
                    sign);
            LocalUserInfo.getInstance(LoginActivity.this).setUserInfo("tel",
                    tel);
        } catch (JSONException e) {

            e.printStackTrace();
            dialog.dismiss();
            return;
        }

    }

}
