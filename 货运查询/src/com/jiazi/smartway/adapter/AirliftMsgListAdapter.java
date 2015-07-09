package com.jiazi.smartway.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jiazi.smartway.R;

public class AirliftMsgListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list = new ArrayList<String>();
	private int dirsSize = 0;

	private HashMap<Integer, String> map = new HashMap<Integer, String>();

	/**
	 * 新建GridView的适配器
	 * 
	 * @param context传入上下文
	 */
	public AirliftMsgListAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ShipMsgHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.lv_item_airlift_result, null);

			holder = new ShipMsgHolder();

			holder.tv_ship_company = (TextView) convertView.findViewById(R.id.tv_ship_company);
			holder.tv_size1 = (TextView) convertView.findViewById(R.id.tv_size1);
			holder.tv_size2 = (TextView) convertView.findViewById(R.id.tv_size2);
			holder.tv_size3 = (TextView) convertView.findViewById(R.id.tv_size3);
			holder.tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
			holder.tv_price2 = (TextView) convertView.findViewById(R.id.tv_price2);
			holder.tv_price3 = (TextView) convertView.findViewById(R.id.tv_price3);

			convertView.setTag(holder);
		} else {
			holder = (ShipMsgHolder) convertView.getTag();
		}

		return convertView;
	}

	private class ShipMsgHolder {
		public TextView tv_ship_company;
		public TextView tv_days;
		public TextView tv_size1;
		public TextView tv_size2;
		public TextView tv_size3;
		public TextView tv_price1;
		public TextView tv_price2;
		public TextView tv_price3;
	}

}