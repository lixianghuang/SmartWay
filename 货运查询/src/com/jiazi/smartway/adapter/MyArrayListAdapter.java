package com.jiazi.smartway.adapter;

import android.R.string;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jiazi.smartway.R;
import com.jiazi.smartway.utils.Constant;

public class MyArrayListAdapter extends BaseAdapter {
	private Context context;
	private int type = 0;
	private String[] strs = null;

	/**
	 * 新建GridView的适配器
	 * 
	 * @param context传入上下文
	 */

	public MyArrayListAdapter(Context context, int type, String[] strs) {
		this.context = context;
		this.type = type;
		this.strs = strs;
	}

	@Override
	public int getCount() {
		return strs.length;
	}

	@Override
	public String getItem(int position) {
		return strs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		switch (type) {
		case Constant.ARRAY_TYPE_WEEKS:
			convertView = View.inflate(context, R.layout.lv_item_weeks, null);

			CheckBox cb_week = (CheckBox) convertView.findViewById(R.id.cb_week);

			cb_week.setText(strs[position]);
			break;
		case Constant.ARRAY_TYPE_USER:
		case Constant.ARRAY_TYPE_CABINETTYPE:
		case Constant.ARRAY_TYPE_DRIVER_PHONE:
			convertView = View.inflate(context, R.layout.lv_item_user_type, null);

			TextView tv_user_type = (TextView) convertView.findViewById(R.id.tv_user_type);

			tv_user_type.setText(strs[position]);
			break;
		default:
			break;
		}
		return convertView;
	}

	public String[] getStrs() {
		return strs;
	}

}