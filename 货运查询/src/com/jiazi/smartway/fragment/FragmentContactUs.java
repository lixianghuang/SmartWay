package com.jiazi.smartway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiazi.smartway.R;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentContactUs extends Fragment implements OnClickListener {
	private View tab_view;

	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tab_view = inflater.inflate(R.layout.frag_con_us, null);

		return tab_view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}

}
