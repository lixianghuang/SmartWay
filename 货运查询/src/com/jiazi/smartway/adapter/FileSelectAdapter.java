package com.jiazi.smartway.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiazi.smartway.R;
import com.jiazi.smartway.bean.FileSelectHolder;

public class FileSelectAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list = new ArrayList<String>();
	private int dirsSize = 0;

	/**
	 * 新建GridView的适配器
	 * 
	 * @param context传入上下文
	 */
	public FileSelectAdapter(Context context, String path) {
		this.context = context;
		getFileList(path);
	}

	public void refresh(String path) {
		getFileList(path);
		this.notifyDataSetChanged();
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

		FileSelectHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.lv_item_select_file, null);
			holder = new FileSelectHolder();
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_file_icon);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_file_name);
			convertView.setTag(holder);
		} else {
			holder = (FileSelectHolder) convertView.getTag();
		}

		if (position < dirsSize) {
			holder.iv.setImageResource(R.drawable.iv_filepath);
		} else {
			holder.iv.setImageResource(R.drawable.iv_file);
		}
		holder.tv.setText(list.get(position));

		return convertView;
	}

	/**
	 * @param path
	 * @param fileList
	 *            注意的是并不是所有的文件夹都可以进行读取的，权限问题
	 */
	private void getFileList(String path) {
		list.clear();

		ArrayList<String> listFiles = new ArrayList<String>();
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		// 返回文件夹中有的数据
		File[] files = file.listFiles();
		// 先判断下有没有权限，如果没有权限的话，就不执行了
		if (null == files)
			return;

		for (int i = 0; i < files.length; i++) {
			file = files[i];
			// 如果是文件夹的话
			if (file.isDirectory()) {
				list.add(file.getName());
			} else if (file.isFile()) {
				listFiles.add(file.getName());
			}
		}
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER); // 排序
		Collections.sort(listFiles, String.CASE_INSENSITIVE_ORDER); // 排序
		dirsSize = list.size();
		list.addAll(listFiles);

	}
}