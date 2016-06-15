package com.ksoft.offlinesdk;

import cn.cmgame.billing.api.GameInterface;
import cn.cmgame.billing.api.GameInterface.IPayCallback;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.LauchManager;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.manager.channel.ChannelOperatorManager;
import com.ksoft.offlinesdk.manager.gamehall.GameHallManager;
import com.ksoft.offlinesdk.util.LogUtil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OfflineSdkImpl {

	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 */
	public static void init(final Activity context) {
		LogUtil.infoLog("begin init");
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// 初始化SDK
				PaymentManager.init(context);
			}
		});

	}

	/**
	 * 调用支付功能 请处理一下回调消息： <br>
	 * 
	 * @param order
	 *            支付请求，是{@code JSONObject}的字符串形式，内容必须包含计费点和订单号 , 形如
	 *            {"payCode":"1","orderNumber":"001"}，不能为{@code null}
	 * @param activity
	 *            调用的{@link Activity}，不能为{@code null}
	 * @param callback
	 *            回调{@link IPayCallback}，不能为{@code null}
	 */
	public static void pay(final String payCode, final String orderNumber,
			final Activity activity, final OfflineCallback callback) {
		LogUtil.infoLog("begin pay payCode is:"+payCode);
		CallBackManager.setPayCallBack(callback);
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				PaymentManager.doPayMent(payCode, orderNumber, activity);
			}
		});

	}

	/**
	 * 
	 * @param context
	 * @param callback
	 */
	public static void exit(final Activity context,
			final OfflineCallback callback) {
		LogUtil.infoLog("do exit");
		CallBackManager.setExitCallBack(callback);
		context.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				PaymentManager.exit(context);
			}
		});
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onResume(Activity activity) {
		LogUtil.infoLog("begin onResume");
		PaymentManager.getChannelOpManager().onResume(activity);
		PaymentManager.getChannelThirdManager(activity).onResume(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onPause(Activity activity) {
		LogUtil.infoLog("begin onPause");
		PaymentManager.getChannelOpManager().onPause(activity);
		PaymentManager.getChannelThirdManager(activity).onPause(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onStop(Activity activity) {
		LogUtil.infoLog("begin onStop");
		PaymentManager.getChannelOpManager().onStop(activity);
		PaymentManager.getChannelThirdManager(activity).onStop(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onDestroy(Activity activity) {
		LogUtil.infoLog("begin onDestory");
		PaymentManager.getChannelOpManager().onDestroy(activity);
		PaymentManager.getChannelThirdManager(activity).onDestroy(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onRestart(Activity activity) {
		LogUtil.infoLog("begin onRestart");
		PaymentManager.getChannelOpManager().onRestart(activity);
		PaymentManager.getChannelThirdManager(activity).onRestart(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onStart(Activity activity) {
		LogUtil.infoLog("begin onStart");
		PaymentManager.getChannelOpManager().onStart(activity);
		PaymentManager.getChannelThirdManager(activity).onStart(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onNewIntent(Intent intent) {
		LogUtil.infoLog("begin onNewInent");
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onCreate(Activity activity) {
		LogUtil.infoLog("begin onCreate");
		PaymentManager.getChannelOpManager().onCreate(activity);
		PaymentManager.getChannelThirdManager(activity).onCreate(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		LogUtil.infoLog("begin onActivityResult");
	}

	/**
	 * 第三方用户登录接口
	 * 
	 * @param context
	 * @param loginCallback
	 */
	public static void doLogin(Activity context, OfflineCallback loginCallback) {
		LogUtil.infoLog("begin doLogin");
		CallBackManager.setLoginCallBack(loginCallback);
		CallBackManager.onCallBack(EventCode.LOGIN_EVENT_CODE,
				ReturnCode.LOGIN_MSG_SUCCESS, null, null);
	}

	/**
	 * 获取免责声明
	 */
	public static String getDeclareMsg() {
		LogUtil.infoLog("begin declare");
		if (PaymentManager.getPayment() == null
				|| PaymentManager.getPayment().getDeclare() == null) {
			return "";
		}
		return PaymentManager.getPayment().getDeclare();
	}

	/**
	 * 
	 * @param context
	 */
	public static void lauch(Application context) {
		LogUtil.infoLog("begin lauch");
		LauchManager.doLauch(context);

	}

	public static void attachBaseContext(Application app,Context context) {
		LogUtil.infoLog("begin attachBaseContext");
		LauchManager.doAattachBaseContext(app,context);
		
	}
}
