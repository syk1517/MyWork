package com.ksoft.offlinesdk.manager;

import cn.cmgame.billing.api.GameInterface;

import com.ksoft.offlinesdk.bean.Payment;
import com.ksoft.offlinesdk.util.DeviceUtil;
import com.ksoft.offlinesdk.util.LogUtil;
import com.ksoft.offlinesdk.util.PaymentParser;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 
 * @author kk
 * 
 */
public class LauchManager {

	/**
	 * 是否已经初始化
	 */
	private static boolean isInit = false;

	/**
	 * 执行初始化
	 * 
	 * @param context
	 */
	public static void doLauch(Application context) {
		if (!isInit) {
			String curProcess = "";
			try {
				curProcess = DeviceUtil.getCurProcessName(context
						.getApplicationContext());
			} catch (Exception e) {
				LogUtil.warningLog("获取curProcess失败:" + e.getMessage());
			}
			System.out.println("getCurProcessName:" + curProcess);
			System.out.println("getPackageName:" + context.getPackageName());
			if (curProcess != null && curProcess.length() > 0
					&& curProcess.equalsIgnoreCase(context.getPackageName())) {
				onLauch(context);
				isInit = true;
			}
		}
	}

	/**
	 * 执行初始化，解析配置文件等
	 * 
	 * @param context
	 */
	public static void doAattachBaseContext(Application app,Context context) {
		if (!isInit) {
			// String curProcess = "";
			// try {
			// curProcess = DeviceUtil.getCurProcessName(context
			// .getApplicationContext());
			// } catch (Exception e) {
			// LogUtil.warningLog("获取curProcess失败:"+e.getMessage());
			// }
			// System.out.println("getCurProcessName:" + curProcess);
			// System.out.println("getPackageName:"
			// + context.getPackageName());
			// if (curProcess != null
			// && curProcess.length() > 0
			// && curProcess.equalsIgnoreCase(context.getPackageName())) {
			// onLauch(context);
			// isInit = true;
			// }
			attachBaseContext(app,context);
		}
	}

	/**
	 * 开始启动的工作
	 * 
	 * @param context
	 */
	private static void onLauch(Application context) {
		// 第一步解析xml
		Payment payment = PaymentParser.getPaymentFromXML(context);
		if (payment != null) {
			PaymentManager.setPayment(payment);
			PaymentManager.setProvinceAndOperators(context);

			// 执行各支付方式的初始化
			PaymentManager.getChannelOpManager().launch(context);
			// 执行各支付方式的初始化
			PaymentManager.getChannelThirdManager(context).launch(context);
		}
//		if (PaymentManager.getPayment() != null) {
//			// 执行各支付方式的初始化
//			PaymentManager.getChannelOpManager().launch(context);
//			// 执行各支付方式的初始化
//			PaymentManager.getChannelThirdManager(context).launch(context);
//		}else{
//			LogUtil.warningLog("payment为null");
//		}
			

	}

	/**
	 * 开始启动的工作
	 * 
	 * @param context
	 */
	private static void attachBaseContext(Application app,Context context) {
		// 第一步解析xml
		Payment payment = PaymentParser.getPaymentFromXML(context);
		if (payment != null) {
			PaymentManager.setPayment(payment);
			PaymentManager.setProvinceAndOperators(context);
			// 执行各支付方式的初始化
			PaymentManager.getChannelOpManager().attachBaseContext(app,context);
		} else {
			LogUtil.warningLog("解析xml文件失败");
		}

	}

}
