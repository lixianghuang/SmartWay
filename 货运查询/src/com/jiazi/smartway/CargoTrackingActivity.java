package com.jiazi.smartway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CargoTrackingActivity extends Activity implements OnClickListener {

	private Intent intent;
	private WebView wv;
	private ProgressBar pb;

	private TextView tv_title;

	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {//
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cargo_tracking);// 跳进登陆界面

		tv_title = (TextView) findViewById(R.id.tv_title);

		findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		findViewById(R.id.ibtn_refresh).setOnClickListener(this);
		pb = (ProgressBar) findViewById(R.id.pb);
		wv = (WebView) findViewById(R.id.wv);

		WebSettings settings = wv.getSettings();
		settings.setPluginState(WebSettings.PluginState.ON);

		settings.setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		wv.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				pb.setProgress(newProgress);
				if (newProgress == 100) {
					pb.setVisibility(View.INVISIBLE);
				} else {
					pb.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				tv_title.setText(title);
			}
		});

		settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		settings.setLoadWithOverviewMode(true);

		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);

		wv.requestFocusFromTouch();
		wv.loadUrl("http://www.schednet.com/home/index.asp");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
			wv.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			finish();
			break;
		case R.id.ibtn_refresh:
			wv.reload();
			break;
		default:
			break;
		}
	}

}
