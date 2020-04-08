package com.chasepay.utilities.util;

import java.text.SimpleDateFormat;

public class CommonUtil {
	
	public static String getTimeString()
	{
		long currentTimeMillis = System.currentTimeMillis();
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = simpleDateFormat.format(currentTimeMillis);
		return timeStr;
	}
	
	
	public static String getCurrentTimeMillis()
	{
		Long currentTimeMillis = System.currentTimeMillis();
		return currentTimeMillis.toString();
	}

}
