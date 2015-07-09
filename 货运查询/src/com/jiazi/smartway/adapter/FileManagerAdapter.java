package com.jiazi.smartway.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiazi.smartway.R;

public class FileManagerAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private int remark_index = -1;

	/**
	 * 新建GridView的适配器
	 * 
	 * @param context传入上下文
	 */
	public FileManagerAdapter(Context context, ArrayList<Map<String, String>> list) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "需求文档.doc");
		map.put("date", "2015-7-1 12:00");

		this.list.add(map);
		this.list.add(map);
		this.list.add(map);
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Map<String, String> getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = View.inflate(context, R.layout.lv_item_file_mgr, null);
		ImageView iv_file_type = (ImageView) view.findViewById(R.id.iv_file_type);
		TextView tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
		TextView tv_upload_date = (TextView) view.findViewById(R.id.tv_upload_date);
		TextView tv_remark = (TextView) view.findViewById(R.id.tv_remark);
		CheckBox cb_remark = (CheckBox) view.findViewById(R.id.cb_remark);
		Button btn_download_file = (Button) view.findViewById(R.id.btn_download_file);

		iv_file_type.setImageResource(R.drawable.iv_file);
		tv_file_name.setText(list.get(position).get("name"));
		tv_upload_date.setText(list.get(position).get("date"));

		if (remark_index == position) {
			cb_remark.setChecked(true);
			tv_remark.setVisibility(View.VISIBLE);
		} else {
			cb_remark.setChecked(false);
			tv_remark.setVisibility(View.GONE);
		}

		cb_remark.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean isCheck) {
				if (!button.isPressed()) {
					return;
				}
				if (isCheck) {
					remark_index = position;
				} else {
					remark_index = -1;
				}
				notifyDataSetChanged();
			}
		});

		btn_download_file.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(context, "aaa", 0).show();
			}
		});

		return view;
	}
}