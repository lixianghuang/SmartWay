package com.jiazi.smartway;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.jiazi.smartway.utils.Constant;
import com.jiazi.smartway.utils.PreUtil;
import com.jiazi.smartway.utils.StreamTools;

public class LoginActivity extends Activity implements OnClickListener ,OnFocusChangeListener{

	private CheckBox cb_remember;
	private CheckBox cb_autologin;

	private EditText et_name;
	private EditText et_psw;

	private PreUtil preUtil;

	private Context context;

	private JsonAsyncTask jsonTask;
	private ProgressDialog proDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {//
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);// 跳进登陆界面

		context = this;

		cb_autologin = (CheckBox) findViewById(R.id.cb_autologin);// 寻找登陆界面的checkbox
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.btn_user_login).setOnClickListener(this);
		findViewById(R.id.tv_user_register).setOnClickListener(this);
		findViewById(R.id.tv_forget_psw).setOnClickListener(this);

		et_name = (EditText) findViewById(R.id.et_login_name);
		et_name.setOnFocusChangeListener(this);
		et_psw = (EditText) findViewById(R.id.et_login_psw);

		preUtil = PreUtil.getInstance(context, Constant.PRE_NAME);

		String name = preUtil.getString("user_name", "");
		et_name.setText(name);
		et_name.setSelection(name.length());

		String psw = preUtil.getString("user_psw", "");
		et_psw.setText(psw);
		et_psw.setSelection(psw.length());

		if (!psw.equals("")) {
			cb_remember.setChecked(true);
		}
	}

	private void login() {
		String name = et_name.getText().toString().trim();
		String psw = et_psw.getText().toString().trim();

		if (name == null || name.equals("")) {
			Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (psw == null || psw.equals("")) {
			Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		proDialog = new ProgressDialog(this);
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setCanceledOnTouchOutside(false);

		proDialog.setTitle("正在登录...");
		proDialog.show();

		jsonTask = new JsonAsyncTask(name, psw);
		jsonTask.execute(name, psw);// 传进去的参数类型必须一致，传进去的参数在doInBackground中的params数组中
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

		private String name;
		private String psw;

		public JsonAsyncTask(String name, String psw) {
			this.name = name;
			this.psw = psw;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if (proDialog != null) {
				proDialog.cancel();
				proDialog = null;
			}

			if (result == null) {
				Toast.makeText(context, "登录出错，请重试", 0).show();
				return;
			}

			// Toast.makeText(context, result, 1).show();
			// System.out.println(result);

			try {
				JSONObject json = new JSONObject(result);

				int code = json.getInt("code");
				if (code == 101) {
					Toast.makeText(context, "用户名和密码不匹配", 0).show();
				} else if (code == 100) {
					MainActivity.user_id = json.getString("user_id");
					MainActivity.user_type = Integer.parseInt(json.getString("access_num"));

					String user_des = json.getString("access_rmk");
					preUtil.setString("user_name", name);
					if (cb_remember.isChecked()) {
						preUtil.setString("user_psw", psw);
						if (cb_autologin.isChecked()) {
							preUtil.setString("user_des", user_des);
							preUtil.setString("user_id", MainActivity.user_id);
							preUtil.setInt("user_type", MainActivity.user_type);
							preUtil.setBoolean("auto_login", true);
						} else {
							preUtil.setBoolean("auto_login", false);
						}
					}

					Intent intent = new Intent();// 跳转到主界面
					intent.putExtra("user_des", user_des);
					setResult(1, intent);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected String doInBackground(String... params) {
			// 返回的类型跟onPostExecute中的参数必须一致
			return loginByPost(params[0], params[1]);
		}
	}

	/**
	 * 通过POST方式登录服务器
	 * 
	 * @param username
	 * @param password
	 * @return 返回null 登陆异常
	 */
	public String loginByPost(String username, String password) {
		// 提交数据到服务器
		// 拼装路径
		try {
			URL url = new URL(Constant.URL_LOGIN);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");

			// 准备数据
			String data = "username=" + username + "&password=" + password;
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
				System.out.println("***********code != 200********");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void register() {
		Intent intent = new Intent(LoginActivity.this, ViewPagerActivity.class);// 跳转到注册页面
		startActivityForResult(intent, 1);
	}

	private void forgetPassword() {

	}

//	public boolean onTouchEvent(MotionEvent event) {
//		// 点击输入框其他的地方就关闭输入法界面
//		View view = LoginActivity.this.getCurrentFocus();
//		if (view != null) {
//			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//		}
//		return super.onTouchEvent(event);
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			String username = intent.getStringExtra("name");
			String password = intent.getStringExtra("psw");

			et_name.setText(username);
			et_psw.setText(password);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.btn_user_login:
			login();
			break;
		case R.id.tv_user_register:
			register();
			break;
		case R.id.tv_forget_psw:
			forgetPassword();
			break;
		default:
			break;
		}

	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

}
