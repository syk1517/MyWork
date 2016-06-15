package com.ksoft.offlinesdk.manager.channel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import cn.cmgame.billing.api.GameInterface;

import com.ksoft.offlinesdk.bean.OperatorType;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.OperatorManager;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.manager.egame.EgameManager;
import com.ksoft.offlinesdk.manager.gamehall.GameHallManager;
import com.ksoft.offlinesdk.manager.unicom.UnicomManager;
import com.ksoft.offlinesdk.util.ICCID;
import com.ksoft.offlinesdk.util.LogUtil;

/**
 * 三方计费的渠道计费管理类
 * 
 * @author new
 * 
 */
public class ChannelOperatorManager extends OperatorManager {

	/**
	 * 运营商支付
	 */
	private OperatorManager operatorManager;

	public ChannelOperatorManager() {
		super();
		initOperatorManagerByType(PaymentManager.getPayment().getOperator());
		// initOperatorManager();
	}

	/**
	 * 根据传入的运营商类型选择支付方式
	 * 
	 * @param operator
	 */
	public ChannelOperatorManager(String operator) {
		super();
		initOperatorManagerByType(operator);
	}

	/**
	 * 初始化operatorManager
	 */
	private void initOperatorManager() {
		if (operatorManager == null
				&& PaymentManager.getPhoneState().getOperator() != null) {
			// 根据手机的运营商选择
			if (PaymentManager.getPhoneState().getOperator()
					.equals(ICCID.OPERATOR_CHINAMOBILE)) {
				// 移动
				LogUtil.infoLog("当前的运营商是:移动");
				operatorManager = new GameHallManager();
			} else if (PaymentManager.getPhoneState().getOperator()
					.equals(ICCID.OPERATOR_CHINATELECOM)) {
				// 电信
				operatorManager = new EgameManager();
				LogUtil.infoLog("当前的运营商是:电信");
			} else if (PaymentManager.getPhoneState().getOperator()
					.equals(ICCID.OPERATOR_CHINAUNICOM)) {
				// 联通
				operatorManager = new UnicomManager();
				LogUtil.infoLog("当前的运营商是:联通");
			} else {
				// 都不是说明没有检测到运营商
				LogUtil.warningLog("no operator");
			}
		}
	}

	/**
	 * 初始化operatorManager
	 */
	private void initOperatorManagerByType(String operator) {
		LogUtil.infoLog("当前的运营商是:" + operator);
		if (operatorManager == null) {
			// 根据手机的运营商选择
			if (operator.equalsIgnoreCase(OperatorType.OPERATOR_CM)) {
				// 移动
				operatorManager = new GameHallManager();
			} else if (operator.equalsIgnoreCase(OperatorType.OPERATOR_CT)) {
				// 电信
				operatorManager = new EgameManager();
			} else if (operator.equalsIgnoreCase(OperatorType.OPERATOR_CU)) {
				// 联通
				operatorManager = new UnicomManager();
			} else {
				// 其他为渠道
				initOperatorManager();
			}
		}
	}

	@Override
	public void launch(Application context) {
		super.launch(context);
		if (operatorManager != null) {
			operatorManager.launch(context);
		} else {
			LogUtil.warningLog("operatorManager is null");
		}

	}
	
	
	
	@Override
	public void attachBaseContext(Application app,Context context) {
		// TODO Auto-generated method stub
		super.attachBaseContext(app,context);
		if (operatorManager != null) {
			operatorManager.attachBaseContext(app,context);
		} else {
			LogUtil.warningLog("operatorManager is null");
		}

	}

	@Override
	public void init(Activity activity) {
		if (operatorManager != null) {
			operatorManager.init(activity);
		} else {
			LogUtil.warningLog("operatorManager is null");
		}
	}

	@Override
	public void pay(Activity activity, PayItems payItems, String orderNumber) {
		if (operatorManager != null) {
			operatorManager.pay(activity, payItems, orderNumber);
		} else {
			LogUtil.warningLog("operatorManager is null");
		}
	}

	@Override
	public void exit(Activity context) {
		// 根据手机的运营商选择
		String operator = PaymentManager.getPayment().getOperator();
		if (operator == null
				|| operator.equalsIgnoreCase(OperatorType.OPERATOR_ALL)) {
			super.exit(context);
		} else {
			operatorManager.exit(context);
		}
	}

}
