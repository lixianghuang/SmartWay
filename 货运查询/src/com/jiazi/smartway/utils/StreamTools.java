package com.jiazi.smartway.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	public static String readInputStreaam(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			// 试着解析result里面的字符串
			String temp = new String(result);
			return temp;

		} catch (IOException e) {
			e.printStackTrace();
			return "获取失败";
		}
	}
}
