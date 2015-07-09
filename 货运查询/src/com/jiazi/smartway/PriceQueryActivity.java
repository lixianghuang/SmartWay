package com.jiazi.smartway;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jiazi.smartway.fragment.FragmentAirlift;
import com.jiazi.smartway.fragment.FragmentFCL;
import com.jiazi.smartway.fragment.FragmentTrailer;

public class PriceQueryActivity extends FragmentActivity implements OnClickListener {

	private Intent intent;
	private Context context;

	private String[] query_type;

	private TextView tv_query_type;

	private FragmentManager fragmentManager;

	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {// 跳到主界面
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_price_query);

		context = this;

		Fragment fragment = new FragmentFCL();
		Bundle args = new Bundle();
		args.putInt("position", 0);
		fragment.setArguments(args);

		fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frag_prices, fragment).commit();

		query_type = getResources().getStringArray(R.array.query_type);

		tv_query_type = (TextView) findViewById(R.id.tv_query_type);
		tv_query_type.setOnClickListener(this);
		tv_query_type.setText(query_type[0]);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_query_type:
			showQueryTypeDialog();
			break;
		case R.id.ibtn_go_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void showQueryTypeDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setSingleChoiceItems(R.array.query_type, index, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				index = position;
				tv_query_type.setText(query_type[position]);

				Fragment fragment = null;
				switch (position) {
				case 0:
					fragment = new FragmentFCL();

					Bundle args = new Bundle();
					args.putInt("position", position);
					fragment.setArguments(args);
					break;
				case 1:
					fragment = new FragmentAirlift();
					break;
				default:
					break;
				}
				if (fragment != null) {
					fragmentManager.beginTransaction().replace(R.id.frag_prices, fragment).commit();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
