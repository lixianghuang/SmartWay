/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneMainActivity.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:39:59 
 * @version: V1.0   
 */
package com.jiazi.smartway;

import com.jiazi.smartway.utils.Constant;
import com.jiazi.smartway.utils.PreUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: SlidingPaneMainActivity
 * @Description: TODO
 * @author: raot 719055805@qq.com
 * @date: 2014年9月5日 上午10:39:59
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private Context context;
	private Intent intent;

	private PreUtil preUtil;

	private TextView tv_user_des;
	private ImageView iv_user_face;

	public static int user_type = 0;
	public static String user_id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;

		tv_user_des = (TextView) findViewById(R.id.tv_user_des);
		iv_user_face = (ImageView) findViewById(R.id.iv_user_face);

		findViewById(R.id.ll_login_reg).setOnClickListener(this);
		findViewById(R.id.ll_trailer_msg).setOnClickListener(this);
		findViewById(R.id.ll_freight_rates).setOnClickListener(this);
		findViewById(R.id.ll_about_us).setOnClickListener(this);
		findViewById(R.id.ll_cargo_tracking).setOnClickListener(this);

		findViewById(R.id.ibtn_more_fun).setOnClickListener(this);

		preUtil = PreUtil.getInstance(context, Constant.PRE_NAME);
		if (preUtil.isBoolean("auto_login")) {
			tv_user_des.setText(preUtil.getString("user_des", "登陆"));

			user_type = preUtil.getInt("user_type", 0);
			user_id = preUtil.getString("user_id", "");
		} else {
			user_id = "";
			user_type = 0;
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.ll_login_reg:
			if (user_type == 0) {
				intent = new Intent(context, LoginActivity.class);// 跳转到登录页面
				startActivityForResult(intent, 1);
			} else {
				preUtil.setInt("user_type", user_type);
				preUtil.setString("user_id", user_id);
				preUtil.setBoolean("auto_login", false);
			}
			break;
		case R.id.ll_trailer_msg:
			if (user_type == 0) {
				Toast.makeText(context, "请先登录，再使用此功能。", 0).show();
				intent = new Intent(context, LoginActivity.class);// 跳转到登录页面
				startActivityForResult(intent, 2);
			} else {
				intent = new Intent(context, TrailerQueryActivity.class);// 跳转到拖车信息页面
				startActivity(intent);
			}
			break;
		case R.id.ll_freight_rates:
			intent = new Intent(context, PriceQueryActivity.class);// 跳转到海运价格查询页面
			startActivity(intent);
			break;
		case R.id.ll_cargo_tracking:
			intent = new Intent(context, CargoTrackingActivity.class);// 跳转到货物跟踪页面
			startActivity(intent);
			break;
		case R.id.ll_about_us:
			intent = new Intent(context, AboutActivity.class);// 跳转到关于我们页面
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			tv_user_des.setText(intent.getStringExtra("user_des"));// 获取传递过来的用户名
			switch (requestCode) {
			case 2:
				intent = new Intent(context, TrailerQueryActivity.class);// 跳转到拖车信息页面
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

}
