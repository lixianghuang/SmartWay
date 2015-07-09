package com.jiazi.smartway;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiazi.smartway.utils.Constant;
import com.jiazi.smartway.utils.StreamTools;

public class TrailerQueryActivity extends Activity implements OnClickListener {

	private Intent intent;

	private Context context;

	private TextView tv_new_trailer;
	private TextView tv_invoice_num;
	private TextView tv_date;
	private TextView tv_factory_name;
	private TextView tv_cabinet_num;

	private EditText et_cabin_num;
	private EditText et_invoice_num;
	private EditText et_date;
	private EditText et_factory_name;
	private EditText et_cabinet_num;

	private ProgressDialog proDialog;
	private JsonAsyncTask jsonTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer_query);

		context = this;

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.btn_query).setOnClickListener(this);

		tv_invoice_num = (TextView) findViewById(R.id.tv_invoice_num);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_factory_name = (TextView) findViewById(R.id.tv_factory_name);
		tv_cabinet_num = (TextView) findViewById(R.id.tv_cabinet_num);

		et_cabin_num = (EditText) findViewById(R.id.et_cabin_num);
		et_invoice_num = (EditText) findViewById(R.id.et_invoice_num);
		et_date = (EditText) findViewById(R.id.et_date);
		et_factory_name = (EditText) findViewById(R.id.et_factory_name);
		et_cabinet_num = (EditText) findViewById(R.id.et_cabinet_num);

		switch (MainActivity.user_type) {
		case Constant.USER_TYPE_TRAILER:
		case Constant.USER_TYPE_SMARTWAY:
			tv_date.setVisibility(View.VISIBLE);
			et_date.setVisibility(View.VISIBLE);
			tv_factory_name.setVisibility(View.VISIBLE);
			et_factory_name.setVisibility(View.VISIBLE);

			tv_new_trailer = (TextView) findViewById(R.id.tv_new_trailer);
			tv_new_trailer.setVisibility(View.VISIBLE);
			tv_new_trailer.setOnClickListener(this);
			break;
		case Constant.USER_TYPE_CUSTOMER:
			tv_invoice_num.setVisibility(View.VISIBLE);
			et_invoice_num.setVisibility(View.VISIBLE);
			break;
		case Constant.USER_TYPE_CUSTOMS_BROKER:
			tv_invoice_num.setVisibility(View.VISIBLE);
			et_invoice_num.setVisibility(View.VISIBLE);
			tv_cabinet_num.setVisibility(View.VISIBLE);
			et_cabinet_num.setVisibility(View.VISIBLE);
			break;
		default:
			Toast.makeText(context, "用户类型不存在!", 0).show();
			finish();
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_new_trailer:
			intent = new Intent(context, TrailerEditActivity.class);// 跳转到拖车编辑页面
			startActivity(intent);
			break;
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.btn_query:
			commit(view);
			break;
		default:
			break;
		}
	}

	private void commit(View view) {

		String str_cabin_num = et_cabin_num.getText().toString().trim();
		String str_cabinet_num = et_cabinet_num.getText().toString().trim();
		String str_date = et_date.getText().toString().trim();
		String str_factory_name = et_factory_name.getText().toString().trim();
		String str_invoice_num = et_invoice_num.getText().toString().trim();

		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();

		if (str_cabin_num != null && !str_cabin_num.equals("")) {
			key.add("booking_no");
			value.add(str_cabin_num);
		}

		if (str_cabinet_num != null && !str_cabinet_num.equals("")) {
			key.add("container_no");
			value.add(str_cabinet_num);
		}

		if (str_date != null && !str_date.equals("")) {
			key.add("date");
			value.add(str_date);
		}

		if (str_factory_name != null && !str_factory_name.equals("")) {
			key.add("factory_name");
			value.add(str_factory_name);
		}

		if (str_invoice_num != null && !str_invoice_num.equals("")) {
			key.add("invoice_no");
			value.add(str_invoice_num);
		}

		if (key.size() == 0) {
			Toast.makeText(context, "请输入要查询的内容", 0).show();
			return;
		}

		proDialog = new ProgressDialog(this);
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setCanceledOnTouchOutside(false);

		proDialog.setTitle("正在查询...");
		proDialog.show();

		jsonTask = new JsonAsyncTask(key, value);
		jsonTask.execute();// 传进去的参数类型必须一致，传进去的参数在doInBackground中的params数组中
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: // 监控/拦截/屏蔽返回键:
			if (proDialog != null && proDialog.isShowing()) {
				jsonTask.cancel(true);
				proDialog.cancel();
				proDialog = null;
				return true;
			}
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class JsonAsyncTask extends AsyncTask<String, Object, String> {

		private List<String> key;
		private List<String> value;

		public JsonAsyncTask(List<String> key, List<String> value) {
			this.key = key;
			this.value = value;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (proDialog != null) {
				proDialog.cancel();
				proDialog = null;
			}

			if (result == null) {
				Toast.makeText(context, "查询出错，请重试", 0).show();
				return;
			}

			// Toast.makeText(context, result, 1).show();
			System.out.println(result);

			try {
				JSONObject json = new JSONObject(result);

				int code = json.getInt("code");

				// intent = new Intent(context, TrailerResultActivity.class);//
				// 跳转到显示拖车查询结果
				// startActivity(intent);
				// finish();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected String doInBackground(String... params) {
			// 返回的类型跟onPostExecute中的参数必须一致
			return commitByGet(key, value);
		}
	}

	public String commitByGet(List<String> key, List<String> value) {
		// 提交数据到服务器
		// 拼装路径
		try {
			String path = "";
			switch (MainActivity.user_type) {
			case Constant.USER_TYPE_SMARTWAY:
			case Constant.USER_TYPE_TRAILER:
				path = Constant.URL_TRAILER_QUERY_SW;
				break;
			case Constant.USER_TYPE_CUSTOMER:
				path = Constant.URL_TRAILER_QUERY_CS;
				break;
			case Constant.USER_TYPE_CUSTOMS_BROKER:
				path = Constant.URL_TRAILER_QUERY_CT;
				break;
			default:
				break;
			}
			for (int i = 0; i < key.size(); i++) {
				path = path + "&" + key + "=" + value;
			}

			path = path + "&user_id=" + MainActivity.user_id + "&access_id=" + MainActivity.user_type;

			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");

			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStreaam(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
