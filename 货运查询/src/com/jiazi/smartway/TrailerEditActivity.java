package com.jiazi.smartway;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiazi.smartway.adapter.MyArrayListAdapter;
import com.jiazi.smartway.bean.TrailerInfo;
import com.jiazi.smartway.utils.Constant;
import com.jiazi.smartway.utils.StreamTools;
import com.jiazi.smartway.utils.TimeFormatUtil;
import com.jiazi.smartway.view.ClearEditText;

public class TrailerEditActivity extends Activity implements OnClickListener, OnFocusChangeListener {

	private Context context;
	private PopupWindow popupWindow;

	private Button btn_driver_phone;

	private TextView tv_date;
	private TextView tv_cabinet_type;

	private EditText et_factory_name;
	private EditText et_invoice_num;
	private EditText et_cabin_num;
	private EditText et_cabinet_num;

	private EditText et_seal_num;
	private EditText et_plate_num;
	private ClearEditText et_driver_phone;

	private LinearLayout ll_cabinet_num_type;

	private String[] strs;

	private ProgressDialog proDialog = null;
	private CommitAsyncTask commitTask;

	private static final int TYPE_COMMIT = 1;
	private static final int TYPE_QUERY = 2;

	private String id = "";
	private boolean isUpdate = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {// 跳到注册界面
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trailer_edit);

		context = this;

		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_date.setOnClickListener(this);

		et_factory_name = (EditText) findViewById(R.id.et_factory_name);
		et_invoice_num = (EditText) findViewById(R.id.et_invoice_num);
		et_cabin_num = (EditText) findViewById(R.id.et_cabin_num);
		et_cabin_num.setOnFocusChangeListener(this);

		et_cabinet_num = (EditText) findViewById(R.id.et_cabinet_num);

		et_seal_num = (EditText) findViewById(R.id.et_seal_num);
		et_plate_num = (EditText) findViewById(R.id.et_plate_num);
		et_driver_phone = (ClearEditText) findViewById(R.id.et_driver_phone);

		tv_cabinet_type = (TextView) findViewById(R.id.tv_cabinet_type);
		// tv_cabinet_type.setText("");
		tv_cabinet_type.setOnClickListener(this);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.btn_commit).setOnClickListener(this);
		findViewById(R.id.ibtn_cabinet_num_add).setOnClickListener(this);
		findViewById(R.id.btn_driver_phone).setOnClickListener(this);

		ll_cabinet_num_type = (LinearLayout) findViewById(R.id.ll_cabinet_num_type);

	}

	public boolean onTouchEvent(MotionEvent event) {
		// 点击输入框其他的地方就关闭输入法界面
		View view = TrailerEditActivity.this.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				Toast.makeText(context, "view 为空", 0).show();
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: // 监控/拦截/屏蔽返回键:
			if (proDialog != null && proDialog.isShowing()) {
				commitTask.cancel(true);
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

	public void popupDialog(final View view, int type) {

		LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.popupwindow_listview, null);
		ListView list = (ListView) layout.findViewById(R.id.listView);

		switch (type) {
		case Constant.ARRAY_TYPE_CABINETTYPE:
			strs = getResources().getStringArray(R.array.cabinet_type);
			break;
		case Constant.ARRAY_TYPE_DRIVER_PHONE:
			strs = getResources().getStringArray(R.array.driver_phone);
			break;
		default:
			break;
		}

		MyArrayListAdapter adapter = new MyArrayListAdapter(context, type, strs);
		list.setAdapter(adapter);

		popupWindow = new PopupWindow(view);
		// 设置弹框的宽度为布局文件的宽
		popupWindow.setWidth(view.getWidth());
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击弹框外部，弹框消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);

		popupWindow.setContentView(layout);
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
		popupWindow.showAsDropDown(view, 0, 0);
		// listView的item点击事件
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				if (view instanceof EditText) {
					EditText et = (EditText) view;
					et.setText(strs[position]);
					et.setSelection(strs[position].length());
				} else if (view instanceof TextView) {
					TextView tv = (TextView) view;
					tv.setText(strs[position]);
				} else if (view instanceof LinearLayout) {
					LinearLayout ll = (LinearLayout) view;
					EditText et = (EditText) ll.getChildAt(0);
					et.setText(strs[position]);
					et.setSelection(strs[position].length());
				}
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}

	private void pickDateDialog(View view) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String temp = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
				tv_date.setText(temp);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	private void commit(View view) {

		// new JsonAsyncTask().execute("");

		TrailerInfo info = new TrailerInfo();
		info.date = tv_date.getText().toString().trim();
		if (info.date == null || info.date.equals("")) {
			Toast.makeText(context, "装柜日期不能为空", 0).show();
			return;
		}

		info.factory_name = et_factory_name.getText().toString().trim();
		if (info.factory_name == null || info.factory_name.equals("")) {
			Toast.makeText(context, "工厂名称不能为空", 0).show();
			return;
		}

		info.invoice_num = et_invoice_num.getText().toString().trim();
		if (info.invoice_num == null || info.invoice_num.equals("")) {
			Toast.makeText(context, "发票号不能为空", 0).show();
			return;
		}

		info.cabin_num = et_cabin_num.getText().toString().trim();
		if (info.cabin_num == null || info.cabin_num.equals("")) {
			Toast.makeText(context, "订舱号不能为空", 0).show();
			return;
		}

		String str = "";
		info.cabinet_num_type = new ArrayList<String>();
		for (int i = 0; i < cab_count; i++) {
			LinearLayout layout = (LinearLayout) ll_cabinet_num_type.getChildAt(i);
			str = ((EditText) layout.getChildAt(0)).getText().toString().trim();
			if (str == null || str.equals("")) {
				Toast.makeText(context, "第" + (i + 1) + "个柜号不能为空", 0).show();
				return;
			}

			str = str + "-" + ((TextView) layout.getChildAt(1)).getText().toString().trim();
			if (str == null || str.equals("")) {
				Toast.makeText(context, "第" + (i + 1) + "个柜型未选择", 0).show();
				return;
			}
			info.cabinet_num_type.add(str);
		}

		info.seal_num = et_seal_num.getText().toString();
		if (info.seal_num == null || info.seal_num.equals("")) {
			Toast.makeText(context, "封条号不能为空", 0).show();
			return;
		}

		info.plate_num = et_plate_num.getText().toString();
		if (info.plate_num == null || info.plate_num.equals("")) {
			Toast.makeText(context, "车牌号不能为空", 0).show();
			return;
		}

		info.driver_phone = et_driver_phone.getText().toString();
		if (info.driver_phone == null || info.driver_phone.equals("")) {
			Toast.makeText(context, "司机号码不能为空", 0).show();
			return;
		}

		JSONObject json = new JSONObject();

		try {
			if (isUpdate) {
				json.put("id", id);
			}

			json.put("uid", MainActivity.user_id);
			json.put("date", TimeFormatUtil.getTimeStamp(info.date));
			json.put("factory_name", info.factory_name);
			json.put("booking_no", info.cabin_num);
			json.put("invoice_no", info.invoice_num);
			json.put("plate_no", info.plate_num);
			json.put("seal_no", info.seal_num);
			json.put("driver_phone", info.driver_phone);

			String temp = info.cabinet_num_type.get(0);
			for (int i = 1; i < cab_count; i++) {
				temp = temp + "," + info.cabinet_num_type.get(i);
			}

			json.put("container_no_type", temp);

			String str_temp = json.toString();

			// Toast.makeText(context, str_temp, 0).show();
			System.out.println(str_temp);

			proDialog = new ProgressDialog(this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog.setCanceledOnTouchOutside(false);

			proDialog.setTitle("正在提交数据...");
			proDialog.show();

			commitTask = new CommitAsyncTask(TYPE_COMMIT, info);
			commitTask.execute(str_temp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class CommitAsyncTask extends AsyncTask<String, Object, String> {
		private TrailerInfo info;
		private int type;

		public CommitAsyncTask(int type, TrailerInfo info) {
			this.info = info;
			this.type = type;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (proDialog != null) {
				proDialog.cancel();
				proDialog = null;
			}

			if (result == null) {
				Toast.makeText(context, "请求出错，请稍后重试", 0).show();
				return;
			}

			// Toast.makeText(context, result, 1).show();
			System.out.println(result);

			try {
				JSONObject json = new JSONObject(result);

				int code = json.getInt("code");
				switch (code) {
				case 201:
					Toast.makeText(context, "提交失败", 0).show();
					break;
				case 200:
					Toast.makeText(context, "提交成功", 0).show();
					Intent intent = new Intent(context, TrailerResultActivity.class);
					// 跳转到拖车信息页面
					Bundle bundle = new Bundle();
					bundle.putSerializable("trailer", info);
					intent.putExtras(bundle);
					intent.putExtra("where", 1);
					startActivity(intent);
					finish();
					break;
				case 202:
					Toast.makeText(context, "该订舱号已存在，请根据需要编辑", 0).show();

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(TrailerEditActivity.this.getCurrentFocus().getWindowToken(),
							InputMethodManager.RESULT_UNCHANGED_SHOWN);

					isUpdate = true;
					// Toast.makeText(context, result, 1).show();
					JSONObject data = json.getJSONArray("data").getJSONObject(0);

					id = data.getString("id");
					et_cabin_num.setText(data.getString("booking_no"));
					tv_date.setText(TimeFormatUtil.getStrTime(data.getString("date")));
					et_driver_phone.setText(data.getString("driver_phone"));
					et_factory_name.setText(data.getString("factory_name"));
					et_invoice_num.setText(data.getString("invoice_no"));
					et_plate_num.setText(data.getString("plate_no"));
					et_seal_num.setText(data.getString("seal_no"));

					String[] str_num_type = data.getString("container_no_type").split(",");
					String[] strs = str_num_type[0].split("-");
					et_cabinet_num.setText(strs[0]);
					tv_cabinet_type.setText(strs[1]);

					int child_count = ll_cabinet_num_type.getChildCount();
					for (int i = 1; i < child_count; i++) {
						ll_cabinet_num_type.removeViewAt(i);
						cab_count--;
					}

					for (int i = 1; i < str_num_type.length; i++) {
						strs = str_num_type[i].split("-");

						cab_count++;
						final LinearLayout layout = (LinearLayout) View.inflate(context,
								R.layout.ll_item_cabinet_num_edit, null);

						EditText et = (EditText) layout.findViewById(R.id.et_cabinet_num);
						et.setText(strs[0]);

						TextView tv = (TextView) layout.findViewById(R.id.tv_cabinet_type);
						tv.setText(strs[1]);
						tv.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								popupDialog(view, Constant.ARRAY_TYPE_CABINETTYPE);
							}
						});

						ImageButton ibtn = (ImageButton) layout.findViewById(R.id.btn_cabinet_num_delete);
						ibtn.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								cab_count--;
								ll_cabinet_num_type.removeView(layout);
							}
						});
						ll_cabinet_num_type.addView(layout);
					}
					break;
				case 203:
					// Toast.makeText(context, "查询失败" + result, 1).show();
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				switch (type) {
				case TYPE_COMMIT:
					return commitByPost(params[0]);
				case TYPE_QUERY:
					return commitByGet(params[0], params[1]);
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "fail";// 返回的类型跟onPostExecute中的参数必须一致
		}
	}

	/**
	 * 通过POST方式提交数据到服务器
	 * 
	 * @param json
	 * @return 返回null 登陆异常
	 */
	public String commitByPost(String json) {
		// 提交数据到服务器
		// 拼装路径
		try {
			URL url = new URL(Constant.URL_TRAILER_SAVE);
			// System.out.println(json);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");

			// 准备数据
			String data = "trailer_info=" + json;
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length() + "");

			// post 的方式实际上是浏览器把数据写给了浏览器
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());

			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
				InputStream is = conn.getInputStream();
				return StreamTools.readInputStreaam(is);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String commitByGet(String key, String value) {
		// 提交数据到服务器
		// 拼装路径
		try {
			String path = Constant.URL_TRAILER_QUERY_SW + "&" + key + "=" + value;

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

	private int cab_count = 1;

	private void addCabinetNum(View view) {
		cab_count++;
		final LinearLayout layout = (LinearLayout) View.inflate(context, R.layout.ll_item_cabinet_num_edit, null);

		TextView tv = (TextView) layout.findViewById(R.id.tv_cabinet_type);
		tv.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				popupDialog(view, Constant.ARRAY_TYPE_CABINETTYPE);
			}
		});

		ImageButton ibtn = (ImageButton) layout.findViewById(R.id.btn_cabinet_num_delete);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cab_count--;
				ll_cabinet_num_type.removeView(layout);
			}
		});
		ll_cabinet_num_type.addView(layout);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.tv_date:
			pickDateDialog(view);
			break;
		case R.id.ibtn_cabinet_num_add:
			addCabinetNum(view);
			break;
		case R.id.tv_cabinet_type:
			popupDialog(view, Constant.ARRAY_TYPE_CABINETTYPE);
			break;
		case R.id.btn_driver_phone:
			LinearLayout ll = (LinearLayout) view.getParent();
			popupDialog(ll, Constant.ARRAY_TYPE_DRIVER_PHONE);
			break;
		case R.id.btn_commit:
			commit(view);
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View view, boolean isFoucus) {
		switch (view.getId()) {
		case R.id.et_cabin_num:
			if (!isFoucus) {
				String str = et_cabin_num.getText().toString().trim();
				if (!str.equals("")) {
					commitTask = new CommitAsyncTask(TYPE_QUERY, null);
					commitTask.execute("booking_no", str);
				}
			}
			break;
		default:
			break;
		}
	}

}
