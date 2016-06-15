package com.ksoft.offlinesdk.manager.unicom;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ksoft.offlinesdk.OfflineSdkImpl;
import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.OperatorManager;
import com.ksoft.offlinesdk.util.LogUtil;
import com.unicom.dcLoader.Utils;
import com.unicom.dcLoader.Utils.UnipayPayResultListener;
import com.unicom.shield.unipay;

/**
 * 联通门户
 * 
 * @author new
 * 
 */
public class UnicomManager extends OperatorManager {

	public static Application mApplication;
	public static String mApplicationName = "com.unicom.dcLoader.UnicomApplication";

	public void loadApplication(Context paramContext) {
		if (mApplication != null)
			return;
		try {
			ClassLoader localClassLoader = paramContext.getClassLoader();
			Class localClass = localClassLoader.loadClass(mApplicationName);
			mApplication = (Application) localClass.newInstance();
			Method localMethod = Application.class.getDeclaredMethod("attach",
					new Class[] { Context.class });

			localMethod.setAccessible(true);
			localMethod.invoke(mApplication, new Object[] { paramContext });
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	@Override
	public void init(Activity activity) {

	}

	@Override
	public void pay(Activity activity, PayItems payItem,
			final String orderNumber) {
		try {
			LogUtil.infoLog("Unicom 开始支付");
			Utils.getInstances().pay(activity, payItem.getFeeCode(),
					new UnipayPayResultListener() {

						@Override
						public void PayResult(String arg0, int arg1, int arg2,
								String arg3) {
							switch (arg1) {
							case 1:// success
									// 此处放置支付请求已提交的相关处理代码
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_SUCCESS, "支付成功",
										orderNumber);
								break;

							case 2:// fail
									// 此处放置支付请求失败的相关处理代码
								LogUtil.warningLog("联通支付失败，错误代码arg1：" + arg1
										+ " arg2:" + arg2);
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_FAILURE, "支付失败",
										orderNumber);
								break;

							case 3:// cancel
									// 此处放置支付请求被取消的相关处理代码
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_CANCEL, "支付取消",
										orderNumber);
								break;

							default:
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_FAILURE, "支付失败",
										orderNumber);
								break;
							}

						}
					});
		} catch (Exception e) {
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
		}

	}

	public void pay(Activity activity, String feeCode,
			final String orderNumber, String money) {
		try {
			LogUtil.infoLog("Unicom 开始支付");
			Utils.getInstances().pay(activity, feeCode,
					new UnipayPayResultListener() {

						@Override
						public void PayResult(String arg0, int arg1, int arg2,
								String arg3) {
							switch (arg1) {
							case 1:// success
									// 此处放置支付请求已提交的相关处理代码
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_SUCCESS, "支付成功",
										orderNumber);
								break;

							case 2:// fail
									// 此处放置支付请求失败的相关处理代码
								LogUtil.warningLog("联通支付失败，错误代码arg1：" + arg1
										+ " arg2:" + arg2);
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_FAILURE, "支付失败",
										orderNumber);
								break;

							case 3:// cancel
									// 此处放置支付请求被取消的相关处理代码
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_CANCEL, "支付取消",
										orderNumber);
								break;

							default:
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_FAILURE, "支付失败",
										orderNumber);
								break;
							}

						}
					});
		} catch (Exception e) {
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
		}

	}

//	@Override
//	public void attachBaseContext(Application app, Context paramContext) {
//		super.attachBaseContext(app,paramContext);
//
//		if (mApplication != null) {
//			return;
//		}
//		unipay.install(app, paramContext);
//		loadApplication(paramContext);
//	}

	@Override
	public void launch(Application context) {
		super.launch(context);
//		try {
//			LogUtil.infoLog("unicom launch begin");
////			Utils.getInstances().initSDK(context,
////					new UnipayPayResultListener() {
////
////						@Override
////						public void PayResult(String arg0, int arg1, int arg2,
////								String arg3) {
////							LogUtil.infoLog("unicom initSDK " + arg1 + " "
////									+ arg3);
////						}
////					});
//			 mApplication.onCreate();
//		} catch (Exception e) {
//			LogUtil.warningLog("unicom init catched exceptions:"
//					+ e.getMessage());
//		}

	}

	/**
	 * 
	 * @param activity
	 */
	public void onResume(Activity activity) {
		Utils.getInstances().onResume(activity);
	}

	/**
	 * 
	 * @param activity
	 */
	public void onPause(Activity activity) {
		Utils.getInstances().onPause(activity);
	}

}
