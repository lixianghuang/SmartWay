package com.jiazi.smartway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiazi.smartway.FCLMsgActivity;
import com.jiazi.smartway.R;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFCL extends Fragment implements OnClickListener {
	private View tab_view;

	private Intent intent;
	private boolean isLCL = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tab_view = inflater.inflate(R.layout.frag_fcl, null);

		Bundle bundle = getArguments();
		int position = bundle.getInt("position");
		if (position == 1) {
			isLCL = true;
			tab_view.findViewById(R.id.ll_ship_company).setVisibility(View.GONE);
		}

		tab_view.findViewById(R.id.btn_query).setOnClickListener(this);

		return tab_view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_query:
			intent = new Intent(getActivity(), FCLMsgActivity.class);
			intent.putExtra("islcl", isLCL);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
