package com.ksoft.offlinesdk.util;

import android.util.Log;

public class LogUtil {

	private static String TAG = "OfflineSDK";

	public static void warningLog(String msg) {
		Log.w(TAG, msg);
	}

	public static void infoLog(String msg) {
		Log.i(TAG, msg);
	}

}
