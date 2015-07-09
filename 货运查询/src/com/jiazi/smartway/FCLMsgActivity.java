package com.jiazi.smartway;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.jiazi.smartway.adapter.FCLMsgListAdapter;
import com.jiazi.smartway.adapter.MyArrayListAdapter;
import com.jiazi.smartway.utils.Constant;

public class FCLMsgActivity extends FragmentActivity implements OnClickListener, OnCheckedChangeListener {

	private Intent intent;
	private Context context;

	private ListView listView;
	private FCLMsgListAdapter adapter;
	private ArrayList<String> list;

	private CheckBox cb_ship_company;

	private boolean isLCL = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {// 跳到主界面
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fcl_result);

		context = this;
		intent = getIntent();
		isLCL = intent.getBooleanExtra("islcl", false);

		listView = (ListView) findViewById(R.id.lv_query_result);

		list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");

		adapter = new FCLMsgListAdapter(context, list);
		listView.setAdapter(adapter);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.btn_week).setOnClickListener(this);

		cb_ship_company = (CheckBox) findViewById(R.id.cb_ship_company);
		cb_ship_company.setOnCheckedChangeListener(this);
		if (isLCL) {
			cb_ship_company.setCompoundDrawables(null, null, null, null);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.btn_week:
			showWeekDialog(view);
			break;
		default:
			break;
		}
	}

	public void showWeekDialog(View view) {

		LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.popupwindow_listview, null);
		ListView list = (ListView) layout.findViewById(R.id.listView);
		String[] strs = context.getResources().getStringArray(R.array.weeks);
		MyArrayListAdapter adapter = new MyArrayListAdapter(context, Constant.ARRAY_TYPE_WEEKS, strs);
		list.setAdapter(adapter);

		PopupWindow popupWindow = new PopupWindow(view);
		// 设置弹框的宽度为布局文件的宽
		popupWindow.setWidth(view.getWidth());
		popupWindow.setHeight(view.getHeight() * 2);

		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击弹框外部，弹框消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
		popupWindow.showAsDropDown(view, 0, 0);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {

			}

		});
		// listView的item点击事件
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

	}

}
