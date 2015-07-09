/**
 * Copyright (c) www.longdw.com
 */
package com.jiazi.smartway;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;

import com.jiazi.smartway.R;
import com.jiazi.smartway.adapter.TabFragmentAdapter;
import com.jiazi.smartway.fragment.FragmentRegister;
import com.jiazi.smartway.fragment.FragmentTrans;

public class ViewPagerActivity extends FragmentActivity {

	public ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_viewpager);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);

		ArrayList<Fragment> list = new ArrayList<Fragment>();
		list.add(new FragmentTrans());
		list.add(new FragmentRegister());
		list.add(new FragmentTrans());

		mViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), list));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setCurrentItem(1, true);
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {
		int onPageScrolled = -1;

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if ((onPageScrolled == 0 || onPageScrolled == 2) && arg0 == 0) {
				finish();
			}
		}

		// 当当前页面被滑动时调用
		// arg1:当前页面偏移的百分比
		// arg2:当前页面偏移的像素位置
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			onPageScrolled = arg0;
			// 0~255透明度值 ，0为完全透明，255为不透明
		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
		}
	}

}
