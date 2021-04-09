package com.chirag.licensedemo.util;

public class StringUtils {

	public static boolean isNotBlank(String data) {
		return data != null && !data.isEmpty();
	}
	
	public static boolean isBlank(String data) {
		return !isNotBlank(data);
	}
}
