/**
 * Copyright (c) www.longdw.com
 */
package com.jiazi.smartway.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiazi.smartway.R;

public class FragmentTrans extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.frag_trans, container, false);
	}

}
