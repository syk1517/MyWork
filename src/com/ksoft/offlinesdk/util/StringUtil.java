package com.ksoft.offlinesdk.util;

public class StringUtil {

	/**
	 * 判断字符串的有效性
	 * @param str
	 * @return
	 */
	public static boolean isStrValid(String str) {
		if (str != null && str.trim().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
