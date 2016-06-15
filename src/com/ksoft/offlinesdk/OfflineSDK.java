package com.ksoft.offlinesdk;



import java.util.List;

import com.ksoft.offlinesdk.callback.OfflineCallback;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class OfflineSDK {
	
	/**
	 * 初始化方法,在application中调用，也可以使用SDK中自定义的application
	 * 
	 * @param context
	 *            上下文
	 */
	public static void lauch(Application context) {
		OfflineSdkImpl.lauch(context);

	}


	/**
	 * 初始化方法,在application中调用，也可以使用SDK中自定义的application
	 * 
	 * @param context
	 *            上下文
	 */
	public static void attachBaseContext(Application app,Context context) {
		OfflineSdkImpl.attachBaseContext(app,context);

	}
	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 */
	public static void init(Activity context) {
		OfflineSdkImpl.init(context);

	}

	/**
	 * 第三方用户登录接口
	 * @param context
	 * @param loginCallback
	 */
	public static void login(Activity context,OfflineCallback loginCallback) {
		OfflineSdkImpl.doLogin(context,loginCallback);
	}

	/**
	 * 
	 * @param payCode 商品id
	 * @param orderNumber 订单号，由游戏方生成，保证在游戏中唯一
	 * @param activity 游戏主Activity
	 * @param callback 支付回调
	 */
	public static void pay(String payCode, String orderNumber,Activity activity,
			OfflineCallback callback) {
		OfflineSdkImpl.pay(payCode,orderNumber, activity, callback);
	}

	/**
	 * 退出接口
	 * @param context
	 * @param callback
	 */
	public static void exit(Activity context,
			OfflineCallback callback) {
		OfflineSdkImpl.exit(context, callback);
	}
	
	/**
	 * 
	 * @param activity
	 */
	public static void onResume(Activity activity) {
		OfflineSdkImpl.onResume(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onPause(Activity activity) {
		OfflineSdkImpl.onPause(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onStop(Activity activity) {
		OfflineSdkImpl.onStop(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onDestroy(Activity activity) {
		OfflineSdkImpl.onDestroy(activity);
	}
	
	/**
	 * 
	 * @param activity
	 */
	public static void onRestart(Activity activity) {
		OfflineSdkImpl.onRestart(activity);
	}
	
	/**
	 * 
	 * @param activity
	 */
	public static void onStart(Activity activity) {
		OfflineSdkImpl.onStart(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onCreate(Activity activity) {
		OfflineSdkImpl.onCreate(activity);
	}
	
	/**
	 * 
	 * @param activity
	 */
	public static void onActivityResult(int requestCode,int resultCode,Intent intent) {
		OfflineSdkImpl.onActivityResult(requestCode,resultCode,intent);
	}
	
	/**
	 * 
	 * @param activity
	 */
	public static void onNewIntent(Intent intent) {
		OfflineSdkImpl.onNewIntent(intent);
	}
	
	/**
	 * 获取免责声明
	 * 返回JsonArray [{"type":"1","msg":"test"},{"type":"2","msg":"oher"}]
	 */
	/**
	 * 
	 * @return
	 */
	public static String getDeclareMsg() {
		return OfflineSdkImpl.getDeclareMsg();
	}

}
