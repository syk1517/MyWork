package com.ksoft.offlinesdk.manager.gamehall;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import cn.cmgame.billing.api.BillingResult;
import cn.cmgame.billing.api.GameInterface;
import cn.cmgame.billing.api.GameInterface.GameExitCallback;
import cn.cmgame.billing.api.GameInterface.IPayCallback;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.OperatorManager;
import com.ksoft.offlinesdk.util.LogUtil;

/**
 * 移动基地门户
 * 
 * @author new
 * 
 */
public class GameHallManager extends OperatorManager {

	@Override
	public void launch(Application context) {
		try {
			System.loadLibrary("megjb");
			LogUtil.infoLog("gamehall loadLibrary 成功");
		} catch (Exception e) {
			LogUtil.warningLog("GameHallManager loadLibrary catched Exception ");
		}
	}

	@Override
	public void exit(Activity context) {
		try {
			GameHall.exit(context);
		} catch (Exception e) {
			super.exit(context);
			LogUtil.warningLog("GameInterface init catched exceptions:"
					+ e.getMessage());
		}

	}

	@Override
	public void init(Activity activity) {
		// 初始化SDK
		try {
			Class<?> cmccClass = Class
					.forName("cn.cmgame.billing.api.GameInterface");
			if (cmccClass != null) {
				// 初始化SDK
				GameHall.doGameHallInit(activity);
				// GameInterface.initializeApp(activity);
				LogUtil.infoLog("gamehall初始化成功");
			} else {
				LogUtil.warningLog("cmcc class is null");
			}

		} catch (Exception e) {
			LogUtil.warningLog("GameInterface init catched exceptions:"
					+ e.getMessage());
		}

		// try {
		// Class<?> cmccClass = Class
		// .forName("cn.cmgame.billing.api.GameInterface");
		// if (cmccClass != null) {
		// Method m = cmccClass.getMethod("initializeApp", new Class[] {
		// Activity.class});
		// m.invoke(cmccClass, activity);
		// LogUtil.infoLog("gamehall初始化成功");
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void pay(Activity activity, String feeCode, final String orderNumber,String money) {
		try {
			LogUtil.infoLog("gamehall开始支付");
			Class<?> cmccClass = Class
					.forName("cn.cmgame.billing.api.GameInterface");
			if (cmccClass != null) {
				GameHall.doGameHallPay(activity, feeCode, orderNumber);
			} else {
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
				LogUtil.warningLog("cmcc class is null");
			}

		} catch (Exception e) {
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
			LogUtil.warningLog("GameInterface pay catched exceptions:"
					+ e.getMessage());
		}

	}

	@Override
	public void pay(Activity activity, PayItems payItem, String orderNumber) {
		try {
			LogUtil.infoLog("gamehall开始支付");
			Class<?> cmccClass = Class
					.forName("cn.cmgame.billing.api.GameInterface");
			if (cmccClass != null) {
				GameHall.doGameHallPay(activity, payItem.getFeeCode(), orderNumber);
			} else {
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
				LogUtil.warningLog("cmcc class is null");
			}

		} catch (Exception e) {
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "支付失败", orderNumber);
			LogUtil.warningLog("GameInterface pay catched exceptions:"
					+ e.getMessage());
		}
		
	}
	
	
}
