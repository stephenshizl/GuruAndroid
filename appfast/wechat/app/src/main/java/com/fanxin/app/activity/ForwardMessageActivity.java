package com.fanxin.app.activity;
/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;

import com.fanxin.app.domain.User;
import com.fanxin.app.fx.ChatActivity;

import cn.dcs.leef.wechat.R;

public class ForwardMessageActivity extends PickContactNoCheckboxActivity {
	private User selectUser;
	private String forward_msg_id;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		forward_msg_id = getIntent().getStringExtra("forward_msg_id");
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			try {
				ChatActivity.activityInstance.finish();
			} catch (Exception e) {
			}
			Intent intent = new Intent(this, ChatActivity.class);
			if (selectUser == null)
				return;
			startActivity(intent);
			finish();

		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
