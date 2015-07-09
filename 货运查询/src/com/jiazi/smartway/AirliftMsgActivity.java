package com.jiazi.smartway;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.jiazi.smartway.adapter.AirliftMsgListAdapter;
import com.jiazi.smartway.adapter.FCLMsgListAdapter;

public class AirliftMsgActivity extends FragmentActivity implements OnClickListener, OnCheckedChangeListener {

	private Intent intent;
	private Context context;

	private ListView listView;
	private AirliftMsgListAdapter adapter;
	private ArrayList<String> list;

	private CheckBox cb_airways;

	@Override
	protected void onCreate(Bundle savedInstanceState) {// 跳到主界面
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_airlift_result);

		context = this;

		listView = (ListView) findViewById(R.id.lv_query_result);

		list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");

		adapter = new AirliftMsgListAdapter(context, list);
		listView.setAdapter(adapter);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);

		cb_airways = (CheckBox) findViewById(R.id.cb_airways);
		cb_airways.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

	}

}
