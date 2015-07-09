package com.jiazi.smartway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jiazi.smartway.adapter.FileManagerAdapter;

public class FileManagerActivity extends Activity implements OnClickListener {

	private Context context;

	private ListView listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manager);

		context = this;

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.tv_upload_file).setOnClickListener(this);

		listView = (ListView) findViewById(R.id.lv_upload_file);
		listView.setAdapter(new FileManagerAdapter(context, null));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.tv_upload_file:
			Intent intent = new Intent(context, FileSelectActivity.class);
			startActivityForResult(intent, 1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			Toast.makeText(context, data.getStringExtra("str_path"), 0).show();
		}
	}
}
