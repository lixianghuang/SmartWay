package com.jiazi.smartway;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.jiazi.smartway.adapter.FileSelectAdapter;
import com.jiazi.smartway.bean.FileSelectHolder;

public class FileSelectActivity extends Activity implements OnItemClickListener, OnClickListener {

	private Context context;

	private ListView list;
	private TextView tv_path_go_up;

	private String str_path = "";

	private FileSelectAdapter fileAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_file);

		context = this;

		str_path = getSharedPreferences("config", Context.MODE_PRIVATE).getString("select_path",
				Environment.getExternalStorageDirectory().toString());

		tv_path_go_up = (TextView) findViewById(R.id.tv_path_go_up);
		tv_path_go_up.setText(str_path);
		tv_path_go_up.setOnClickListener(this);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);

		list = (ListView) findViewById(R.id.lv_file_list);
		list.setOnItemClickListener(this);

		fileAdapter = new FileSelectAdapter(context, str_path);

		list.setAdapter(fileAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		FileSelectHolder holder = (FileSelectHolder) view.getTag();
		String str_temp = "";
		if (str_path.equals("/")) {
			str_temp = str_path + holder.tv.getText().toString();
		} else {
			str_temp = str_path + File.separator + holder.tv.getText().toString();
		}
		File file = new File(str_temp);
		if (file.isFile()) {
			Intent intent = new Intent();
			intent.putExtra("str_path", str_temp);
			setResult(1, intent);
			finish();
			return;
		}
		str_path = str_temp;
		tv_path_go_up.setText(str_path);
		fileAdapter.refresh(str_path);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_path_go_up:
			int index = str_path.lastIndexOf(File.separator);
			str_path = str_path.substring(0, index > 0 ? index : 1);
			tv_path_go_up.setText(str_path);
			fileAdapter.refresh(str_path);
			break;
		case R.id.ibtn_go_back:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Editor editor = getSharedPreferences("config", Context.MODE_PRIVATE).edit();
		editor.putString("select_path", str_path);
		editor.commit();
	}
}
