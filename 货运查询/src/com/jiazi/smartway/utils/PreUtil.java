package com.jiazi.smartway.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreUtil {

	private static SharedPreferences preferences;

	// 使用工厂模式，对象单例化，并通过一个公开方法提供给外界访问。
	private static PreUtil instance = null;

	public static PreUtil getInstance(Context context, String name) {
		if (instance == null) {
			instance = new PreUtil();
			preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		}
		return instance;
	}

	public void save(String name, String psw) {

		Editor editor = preferences.edit();

		editor.putString(name, psw);

		editor.commit();
	}

	public boolean isExist(String str) {
		String result = preferences.getString(str, "");
		if (result == null || result == "") {
			return false;
		}

		return true;
	}

	public void setString(String key, String value) {
		Editor editor = preferences.edit();

		editor.putString(key, value);

		editor.commit();
	}

	public String getString(String key, String defalutValue) {
		return preferences.getString(key, defalutValue);
	}

	public void setInt(String key, int value) {
		Editor editor = preferences.edit();

		editor.putInt(key, value);

		editor.commit();
	}

	public int getInt(String key, int defalutValue) {
		return preferences.getInt(key, defalutValue);
	}

	public void setBoolean(String key, boolean value) {
		Editor editor = preferences.edit();

		editor.putBoolean(key, value);

		editor.commit();
	}

	public boolean isBoolean(String key) {
		return preferences.getBoolean(key, false);
	}

}
