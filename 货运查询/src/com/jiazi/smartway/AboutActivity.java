package com.jiazi.smartway;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiazi.smartway.adapter.TabFragmentAdapter;
import com.jiazi.smartway.fragment.FragmentCompanyDes;
import com.jiazi.smartway.fragment.FragmentContactUs;

public class AboutActivity extends FragmentActivity implements OnClickListener {

	private RadioGroup radioGroup;

	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	private ViewPager mPager;// 页卡内容

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {// 跳到主界面
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_about);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);

		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
		radioGroup.check(R.id.rb_1);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (group.getCheckedRadioButtonId()) {
				case R.id.rb_1:
					mPager.setCurrentItem(0);
					break;
				case R.id.rb_2:
					mPager.setCurrentItem(1);
					break;
				default:
					break;
				}
			}
		});

		InitViewPager();
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.viewpager);

		ArrayList<Fragment> list = new ArrayList<Fragment>();
		list.add(new FragmentCompanyDes());
		list.add(new FragmentContactUs());

		mPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), list));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		default:
			break;
		}

	}
}
