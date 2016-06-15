package com.ksoft.offlinesdk.manager.egame;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import cn.egame.terminal.paysdk.EgameExitListener;
import cn.egame.terminal.paysdk.EgamePay;
import cn.egame.terminal.paysdk.EgamePayListener;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.OperatorManager;
import com.ksoft.offlinesdk.util.LogUtil;

/**
 * 电信门户
 * 
 * @author new
 * 
 */
public class EgameManager extends OperatorManager {

	@Override
	public void init(Activity activity) {
		try {
			EgamePay.init(activity);
			LogUtil.infoLog("EgamePay 初始化 成功");
		} catch (Exception e) {
			LogUtil.warningLog("EgamePay init catched exceptions:"
					+ e.getMessage());
		}

	}
	
	@Override
	public void pay(Activity activity, PayItems payItem, final String orderNumber) {
		LogUtil.infoLog("EgamePay 开始支付");
		HashMap<String, String> payParams = new HashMap<String, String>();
		// 电信单机
		 payParams.put(EgamePay.PAY_PARAMS_KEY_TOOLS_ALIAS, payItem.getFeeCode());
		 payParams.put(EgamePay.PAY_PARAMS_KEY_PRIORITY, "sms");

		// 电信网游
//		int price = (int) Float.parseFloat(payItem.getMoney());
//		if (price < 1) {
//			price = 1;
//		}
//		LogUtil.infoLog("egame price:"+price);
//		payParams.put(EgamePay.PAY_PARAMS_KEY_TOOLS_PRICE, price + "");
//		payParams.put(EgamePay.PAY_PARAMS_KEY_CP_PARAMS, orderNumber);
		try {
			EgamePay.pay(activity, payParams, new EgamePayListener() {
				@Override
				public void paySuccess(Map<String, String> params) {
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_SUCCESS, "支付成功", orderNumber);
				}

				@Override
				public void payFailed(Map<String, String> params, int errorInt) {
					LogUtil.warningLog("电信支付失败，错误代码：" + errorInt);
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_FAILURE, "支付失败 ", orderNumber);
				}

				@Override
				public void payCancel(Map<String, String> params) {
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_CANCEL, "支付取消", orderNumber);
				}
			});
		} catch (Exception e) {
			LogUtil.warningLog("EgamePay pay catched exceptions:"
					+ e.getMessage());
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
		}
		
	}



	public void pay(Activity activity, String feeCode,
			final String orderNumber, String money) {
		LogUtil.infoLog("EgamePay 开始支付");
		HashMap<String, String> payParams = new HashMap<String, String>();
		// 电信单机
		// payParams.put(EgamePay.PAY_PARAMS_KEY_TOOLS_ALIAS, feeCode);
		// payParams.put(EgamePay.PAY_PARAMS_KEY_PRIORITY, "sms");

		// 电信网游
		int price = (int) Float.parseFloat(money);
		if (price < 1) {
			price = 1;
		}
		LogUtil.infoLog("egame price:"+price);
		payParams.put(EgamePay.PAY_PARAMS_KEY_TOOLS_PRICE, price + "");
		payParams.put(EgamePay.PAY_PARAMS_KEY_CP_PARAMS, orderNumber);
		try {
			EgamePay.pay(activity, payParams, new EgamePayListener() {
				@Override
				public void paySuccess(Map<String, String> params) {
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_SUCCESS, "支付成功", orderNumber);
				}

				@Override
				public void payFailed(Map<String, String> params, int errorInt) {
					LogUtil.warningLog("电信支付失败，错误代码：" + errorInt);
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_FAILURE, "支付失败 ", orderNumber);
				}

				@Override
				public void payCancel(Map<String, String> params) {
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_CANCEL, "支付取消", orderNumber);
				}
			});
		} catch (Exception e) {
			LogUtil.warningLog("EgamePay pay catched exceptions:"
					+ e.getMessage());
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
		}

	}

	@Override
	public void exit(Activity context) {
		try {
			EgamePay.exit(context, new EgameExitListener() {
				public void exit() {
					CallBackManager.onCallBack(EventCode.EXIT_EVENT_CODE,
							ReturnCode.EXIT_MSG_SUCCESS, null, null);
				}

				public void cancel() {
					CallBackManager.onCallBack(EventCode.EXIT_EVENT_CODE,
							ReturnCode.EXIT_MSG_CANCEL, null, null);
				}
			});
		} catch (Exception e) {
			super.exit(context);
			LogUtil.warningLog("EgamePay exit catched exceptions:"
					+ e.getMessage());
		}

	}

}
