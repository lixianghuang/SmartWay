package com.jiazi.smartway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiazi.smartway.AirliftMsgActivity;
import com.jiazi.smartway.FCLMsgActivity;
import com.jiazi.smartway.R;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentAirlift extends Fragment implements OnClickListener {
	private View tab_view;

	private TextView tv_start_port;
	private TextView tv_dist_port;
	private TextView tv_company;

	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tab_view = inflater.inflate(R.layout.frag_fcl, null);

		tv_start_port = (TextView) tab_view.findViewById(R.id.tv_start_port);
		tv_start_port.setText("起始机场");
		tv_dist_port = (TextView) tab_view.findViewById(R.id.tv_dist_port);
		tv_dist_port.setText("目的机场");
		tv_company = (TextView) tab_view.findViewById(R.id.tv_company);
		tv_company.setText("航空公司");

		tab_view.findViewById(R.id.btn_query).setOnClickListener(this);

		return tab_view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_query:
			intent = new Intent(getActivity(), AirliftMsgActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
