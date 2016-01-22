package com.fanxin.app.fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.fx.others.ConversationAdapter;
import com.fanxin.app.fx.others.TopUser;

import cn.dcs.leef.wechat.R;

public class FragmentCoversation extends Fragment {

    private boolean hidden;
    private ListView listView;
    private ConversationAdapter adapter;

    private Map<String, TopUser> topMap;
    public RelativeLayout errorItem;
    public TextView errorText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.getBoolean("isConflict", false))
            return;
        errorItem = (RelativeLayout) getView().findViewById(R.id.rl_error_item);
        errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);

        listView = (ListView) getView().findViewById(R.id.list);
        adapter = new ConversationAdapter(getActivity());
        // 设置adapter
        listView.setAdapter(adapter);

    }

    /**
     * 刷新页面
     */
    public void refresh() {
        adapter = new ConversationAdapter(getActivity());
        listView.setAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }

   

}
