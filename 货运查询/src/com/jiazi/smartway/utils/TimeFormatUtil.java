package com.jiazi.smartway.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtil {
	public static String getTimeStamp(String user_time) {
		// Date或者String转化为时间戳
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sdf.parse(user_time + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		re_time = String.valueOf(l / 1000);// 得到秒为单位的
		return re_time;
	}

	public static String getStrTime(String user_time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = Long.parseLong(user_time) * 1000;
		String d = sdf.format(time);
		return d.split(" ")[0];
	}
}
