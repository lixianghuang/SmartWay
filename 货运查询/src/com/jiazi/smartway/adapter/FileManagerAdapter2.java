package com.jiazi.smartway.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jiazi.smartway.R;

public class FileManagerAdapter2 extends BaseAdapter {
	private Context context;
	private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private int remark_index = -1;

	private int height = 0;

	/**
	 * 新建GridView的适配器
	 * 
	 * @param context传入上下文
	 */
	public FileManagerAdapter2(Context context, ArrayList<Map<String, String>> list) {
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

		final FileManagerHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.lv_item_file_mgr, null);
			holder = new FileManagerHolder();
			holder.iv_file_type = (ImageView) convertView.findViewById(R.id.iv_file_type);
			holder.tv_file_name = (TextView) convertView.findViewById(R.id.tv_file_name);
			holder.tv_upload_date = (TextView) convertView.findViewById(R.id.tv_upload_date);
			holder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
			holder.cb_remark = (CheckBox) convertView.findViewById(R.id.cb_remark);
			holder.btn_download_file = (Button) convertView.findViewById(R.id.btn_download_file);

			if (height == 0) {
				height = holder.tv_remark.getHeight();
			}

			convertView.setTag(holder);
		} else {
			holder = (FileManagerHolder) convertView.getTag();
		}

		holder.iv_file_type.setImageResource(R.drawable.iv_file);
		holder.tv_file_name.setText(list.get(position).get("name"));
		holder.tv_upload_date.setText(list.get(position).get("date"));

		if (remark_index == position) {
			holder.cb_remark.setChecked(true);
			holder.tv_remark.setHeight(height);
		} else {
			holder.cb_remark.setChecked(false);
			holder.tv_remark.setHeight(1);
		}

		holder.cb_remark.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean isCheck) {
				if (!holder.cb_remark.isPressed()) {
					return;
				}
				if (isCheck) {
					remark_index = position;
				} else {
					remark_index = -1;
				}
				 notifyDataSetChanged();

				// holder.tv_remark.clearAnimation();
				final int deltaValue;
				int durationMillis = 500;
				if (isCheck) {
					remark_index = position;
					deltaValue = height;
				} else {
					remark_index = -1;
					deltaValue = -height;
				}
				Animation animation = new Animation() {
					protected void applyTransformation(float interpolatedTime, Transformation t) {
						holder.tv_remark.setHeight((int) (height + 1 + deltaValue * interpolatedTime));
					}
				};
				animation.setDuration(durationMillis);
				holder.tv_remark.startAnimation(animation);
				notifyDataSetChanged();
			}
		});

		holder.btn_download_file.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(context, "aaa", 0).show();
			}
		});

		return convertView;
	}

	private class FileManagerHolder {
		public ImageView iv_file_type;
		public TextView tv_file_name;
		public TextView tv_upload_date;
		public TextView tv_remark;
		public CheckBox cb_remark;
		public Button btn_download_file;
	}

}