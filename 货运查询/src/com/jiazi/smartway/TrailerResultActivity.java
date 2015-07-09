package com.jiazi.smartway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiazi.smartway.bean.TrailerInfo;

public class TrailerResultActivity extends Activity implements OnClickListener {

	private Context context;

	private Intent intent;

	private TrailerInfo info;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer_result);

		context = this;

		info = (TrailerInfo) getIntent().getSerializableExtra("trailer");

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.tv_attach_file).setOnClickListener(this);
		findViewById(R.id.ibtn_file_edit).setOnClickListener(this);

		LinearLayout ll_cabinet_num_type = (LinearLayout) findViewById(R.id.ll_cabinet_num_type);
		ll_cabinet_num_type.removeAllViews();

		if (info != null) {
			((TextView) findViewById(R.id.tv_factory_name)).setText("工厂名:  " + info.factory_name);
			((TextView) findViewById(R.id.tv_invoice_num)).setText("发票号:  " + info.invoice_num);
			((TextView) findViewById(R.id.tv_cabin_num)).setText("订舱号:  " + info.cabin_num);
			((TextView) findViewById(R.id.tv_seal_num)).setText("封条号:  " + info.seal_num);
			((TextView) findViewById(R.id.tv_plate_num)).setText("车牌号:  " + info.plate_num);
			((TextView) findViewById(R.id.tv_driver_phone)).setText("联系人:  " + info.driver_phone);
			((TextView) findViewById(R.id.tv_date)).setText(info.date);

			for (int i = 0; i < info.cabinet_num_type.size(); i++) {
				String[] strs = info.cabinet_num_type.get(i).split("-");

				LinearLayout layout = (LinearLayout) View.inflate(context, R.layout.ll_item_cabinet_num_type, null);
				TextView tv = (TextView) layout.findViewById(R.id.tv_cabinet_num);

				tv.setText(strs[0]);
				tv = (TextView) layout.findViewById(R.id.tv_cabinet_type);
				tv.setText(strs[1]);

				ll_cabinet_num_type.addView(layout);
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.ibtn_file_edit:
			intent = new Intent(context, TrailerEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("trailer", info);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			break;
		case R.id.tv_attach_file:
			intent = new Intent(context, FileManagerActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
