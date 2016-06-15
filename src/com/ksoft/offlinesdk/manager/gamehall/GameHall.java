package com.ksoft.offlinesdk.manager.gamehall;

import com.cmnpay.api.Payment;
import com.cmnpay.api.PaymentCallback;
import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.util.LogUtil;

import android.app.Activity;
import android.widget.Toast;
import cn.cmgame.billing.api.BillingResult;
import cn.cmgame.billing.api.GameInterface;
import cn.cmgame.billing.api.GameInterface.GameExitCallback;

public class GameHall {

	private static boolean isInit = false;

	/**
	 * @param activity
	 */
	public static void doGameHallInit(Activity activity) {
		// 初始化SDK
		// GameInterface.initializeApp(activity);

	}

	public static void doGameHallPay(Activity activity, final String feeCode,
			final String orderNumber) {
		Class<?> payment = null;
		try {
			payment = Class.forName("com.cmnpay.api.Payment");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LogUtil.infoLog("没有找到payment的class");
		}
		if (!isInit) {
			if (payment != null && !PaymentManager.getPayment().equals("true")) {
				LogUtil.infoLog("使用payment初始化");
				Payment.init(activity);
			} else {
				LogUtil.infoLog("使用GameInterface初始化");
				GameInterface.initializeApp(activity);
			}
			isInit = true;
		}
		if (payment != null && !PaymentManager.getPayment().equals("true")) {
			LogUtil.infoLog("使用Payment支付");
			Payment.buy(feeCode, "1", new PaymentCallback() {

				@Override
				public void onProductOrderOK(String itemCode) {

				}

				@Override
				public void onProductOrderFail(String itemCode, int errCode,
						String errMsg) {

				}

				@Override
				public void onBuyProductOK(String itemCode) {
					String result = "";
					result = "购买道具：[" + feeCode + "] 成功！";
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_SUCCESS, result, orderNumber);
				}

				@Override
				public void onBuyProductFailed(String itemCode, int errCode,
						String errMsg) {
					String result = "";
					result = "购买道具：[" + feeCode + "] 失败！";
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_FAILURE, result, orderNumber);
				}
			});
		} else {
			LogUtil.infoLog("使用GameInterface支付");
			GameInterface.doBilling(activity, true, true, feeCode, null,
					new GameInterface.IPayCallback() {
						@Override
						public void onResult(int resultCode,
								String billingIndex, Object obj) {
							LogUtil.infoLog("doGameHallPay onResult resultCode:"
									+ resultCode);
							String result = "";
							switch (resultCode) {
							case BillingResult.SUCCESS:
								result = "购买道具：[" + billingIndex + "] 成功！";
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_SUCCESS, result,
										orderNumber);
								break;
							case BillingResult.FAILED:
								result = "购买道具：[" + billingIndex + "] 失败！";
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_FAILURE, result,
										orderNumber);
								break;
							default:
								result = "购买道具：[" + billingIndex + "] 取消！";
								CallBackManager.onCallBack(
										EventCode.PAY_EVENT_CODE,
										ReturnCode.PAY_MSG_CANCEL, result,
										orderNumber);
								break;
							}
						}
					});
		}

	}

	public static void exit(Activity context) throws ClassNotFoundException {
		Class<?> cmccClass = Class
				.forName("cn.cmgame.billing.api.GameInterface");
		if (cmccClass != null) {
			GameInterface.exit(context, new GameExitCallback() {

				@Override
				public void onConfirmExit() {
					CallBackManager.onCallBack(EventCode.EXIT_EVENT_CODE,
							ReturnCode.EXIT_MSG_SUCCESS, null, null);

				}

				@Override
				public void onCancelExit() {
					CallBackManager.onCallBack(EventCode.EXIT_EVENT_CODE,
							ReturnCode.EXIT_MSG_CANCEL, null, null);

				}
			});
		} else {
			LogUtil.warningLog("cmcc class is null");
		}

	}
}
