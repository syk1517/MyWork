package com.ksoft.offlinesdk.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ItemPayInfo;
import com.ksoft.offlinesdk.bean.OperatorType;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.bean.Payment;
import com.ksoft.offlinesdk.bean.Paytype;
import com.ksoft.offlinesdk.bean.PhoneState;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.channel.ChannelOperatorManager;
import com.ksoft.offlinesdk.manager.channel.ChannelThirdPayManager;
import com.ksoft.offlinesdk.ui.MultiPayDialog;
import com.ksoft.offlinesdk.util.DeviceUtil;
import com.ksoft.offlinesdk.util.ICCID;
import com.ksoft.offlinesdk.util.LogUtil;
import com.ksoft.offlinesdk.util.StringUtil;

public class PaymentManager {

	/**
	 * 存储全局支付配置文件
	 */
	private static Payment payment;

	/**
	 * 存储全局配置文件
	 */
	private static PhoneState phoneState;

	/**
	 * 存储全局配置文件
	 */
	public static String THIRD_CHANNEL_OP = "99";

	/**
	 * 渠道运营商业务逻辑处理
	 */
	private static ChannelOperatorManager channelOpManager;

	/**
	 * 渠道第三方支付业务逻辑处理
	 */
	private static ChannelThirdPayManager channelThirdManager;

	/**
	 * 正在支付
	 */
	private static boolean isPaying = false;

	public static boolean isPaying() {
		return isPaying;
	}

	public static void setPaying(boolean isPaying) {
		PaymentManager.isPaying = isPaying;
	}

	public static Payment getPayment() {
		return payment;
	}

	public static void setPayment(Payment payment) {
		PaymentManager.payment = payment;
	}

	public static PhoneState getPhoneState() {
		return phoneState;
	}

	public static void setPhoneState(PhoneState phoneState) {
		PaymentManager.phoneState = phoneState;
	}

	/**
	 * 获取省份和运营商
	 * 
	 * @param mContext
	 */
	public static void setProvinceAndOperators(Context mContext) {
		// 1,获取iccid
		ICCID iccid = new ICCID(mContext);
		phoneState = new PhoneState();
		phoneState.setProvince(iccid.getArea());
		phoneState.setOperator(iccid.getOperator());
		LogUtil.infoLog("phoneState.getOperator:" + phoneState.getOperator());
		LogUtil.infoLog("phoneState.getArea:" + phoneState.getProvince());
		// 2,获取imsi
		if (iccid.getIccid() == null || iccid.getIccid().length() <= 6
				|| iccid.getOperator() == null) {
			String str = DeviceUtil.getOperatorCode(mContext);
			phoneState.setOperator(str);
		}
	}

	/**
	 * 初始化运营商管理类
	 * 
	 * @param mContext
	 */
	public static ChannelOperatorManager getChannelOpManager() {
		if (channelOpManager == null) {
			channelOpManager = new ChannelOperatorManager();
		}
		return channelOpManager;
	}

	/**
	 * 初始化第三方支付管理类
	 * 
	 * @param mContext
	 */
	public static ChannelThirdPayManager getChannelThirdManager(Context context) {
		if (channelThirdManager == null) {
			channelThirdManager = new ChannelThirdPayManager(context);
		}
		return channelThirdManager;
	}

	private static String transPayType(String payTypeId) {
		if (payTypeId == null) {
			return null;
		}
		if (payTypeId.equals("100001")) {
			return ICCID.OPERATOR_CHINAMOBILE;
		} else if (payTypeId.equals("100002")) {
			return ICCID.OPERATOR_CHINAUNICOM;
		} else if (payTypeId.equals("100003")) {
			return ICCID.OPERATOR_CHINATELECOM;
		} else {
			return null;
		}

	}

	/**
	 * 根据payCode获取对应运营商的计费代码
	 * 
	 * @param payCode
	 * @return
	 */
	private static String getFeeCodeByPayCode(String payCode) {
		if (payment == null || payment.getPayTypes().size() < 1) {
			LogUtil.warningLog("payment is not vailed");
			return null;
		} else {
			for (Paytype payType : payment.getPayTypes()) {
				String operator = transPayType(payType.getId());
				if (operator == null) {
					LogUtil.warningLog("operator is null");
					return null;
				}
				// 如果当前的运营商跟支付列表的一致
				if (operator.equals(phoneState.getOperator())) {
					for (PayItems payItem : payType.getPayItems()) {
						if (payCode.equals(payItem.getItemId())) {
							return payItem.getFeeCode();
						}
					}
					LogUtil.warningLog("feeCode is null");
					return null;
				}
			}
			LogUtil.warningLog("operator is null");
			return null;
		}

	}

	/**
	 * 根据payCode获取对应运营商的计费代码
	 * 
	 * @param payCode
	 * @return
	 */
	private static String getFeeCodeByPayCode(String payCode,
			String operatorType) {
		if (payment == null || payment.getPayTypes().size() < 1) {
			LogUtil.warningLog("payment is not vailed");
			return null;
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CM)) {
			return getFeeCode(payCode, ICCID.OPERATOR_CHINAMOBILE);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CT)) {
			return getFeeCode(payCode, ICCID.OPERATOR_CHINATELECOM);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CU)) {
			return getFeeCode(payCode, ICCID.OPERATOR_CHINAUNICOM);
		} else {
			return getFeeCode(payCode, phoneState.getOperator());
		}

	}

	/**
	 * 根据payCode获取对应运营商的计费代码
	 * 
	 * @param payCode
	 * @return
	 */
	private static PayItems getPayItemByPayCode(String payCode,
			String operatorType) {
		if (payment == null || payment.getPayTypes().size() < 1) {
			LogUtil.warningLog("payment is not vailed");
			return null;
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CM)) {
			return getPayItems(payCode, ICCID.OPERATOR_CHINAMOBILE);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CT)) {
			return getPayItems(payCode, ICCID.OPERATOR_CHINATELECOM);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CU)) {
			return getPayItems(payCode, ICCID.OPERATOR_CHINAUNICOM);
		} else {
			return getPayItems(payCode, phoneState.getOperator());
		}

	}

	/**
	 * 根据payCode获取对应运营商的计费代码
	 * 
	 * @param payCode
	 * @return
	 */
	private static List<ItemPayInfo> getPayItemListByPayCode(String payCode,
			String operatorType) {
		if (payment == null || payment.getPayTypes().size() < 1) {
			LogUtil.warningLog("payment is not vailed");
			return null;
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CM)) {
			return getPayItemList(payCode, ICCID.OPERATOR_CHINAMOBILE);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CT)) {
			return getPayItemList(payCode, ICCID.OPERATOR_CHINATELECOM);
		} else if (operatorType.equalsIgnoreCase(OperatorType.OPERATOR_CU)) {
			return getPayItemList(payCode, ICCID.OPERATOR_CHINAUNICOM);
		} else {
			return getPayItemList(payCode, phoneState.getOperator());
		}

	}

	/**
	 * 
	 * @param feeCode
	 * @param operator
	 * @return
	 */
	private static String getFeeCode(String payCode, String type) {
		for (Paytype payType : payment.getPayTypes()) {
			String operator = transPayType(payType.getId());
			if (operator == null) {
				LogUtil.warningLog("operator is null");
				return null;
			}
			// 如果当前的运营商跟支付列表的一致
			if (operator.equals(type) && payType.getPayItems() != null) {
				for (PayItems payItem : payType.getPayItems()) {
					if (payCode.equals(payItem.getItemId())) {
						return payItem.getFeeCode();
					}
				}
				LogUtil.warningLog("feeCode is null");
				return null;
			}
		}
		LogUtil.warningLog("operator is null");
		return null;
	}

	/**
	 * 
	 * @param feeCode
	 * @param operator
	 * @return
	 */
	private static PayItems getPayItems(String payCode, String type) {
		for (Paytype payType : payment.getPayTypes()) {
			String operator = transPayType(payType.getId());
			if (operator == null) {
				LogUtil.warningLog("operator is null");
				return null;
			}
			// 如果当前的运营商跟支付列表的一致
			if (operator.equals(type) && payType.getPayItems() != null) {
				for (PayItems payItem : payType.getPayItems()) {
					if (payCode.equals(payItem.getItemId())) {
						return payItem;
					}
				}
				LogUtil.warningLog("feeCode is null");
				return null;
			}
		}
		LogUtil.warningLog("operator is null");
		return null;
	}

	/**
	 * 
	 * @param feeCode
	 * @param operator
	 * @return
	 */
	private static List<ItemPayInfo> getPayItemList(String payCode, String type) {
		List<ItemPayInfo> payList = new ArrayList<ItemPayInfo>();
		for (Paytype payType : payment.getPayTypes()) {
			String operator = payType.getOperators();
			if (operator == null) {
				LogUtil.warningLog("operator is null");
				return null;
			}
			// 如果当前的运营商跟支付列表的一致
			if (operator.equals(type) || operator.equals(THIRD_CHANNEL_OP)
					&& payType.getPayItems() != null) {
				for (PayItems payItem : payType.getPayItems()) {
					if (payCode.equals(payItem.getItemId())) {
						ItemPayInfo info = new ItemPayInfo();
						info.setPayItems(payItem);
						info.setPaytype(payType);
						payList.add(info);
					}
				}
			}
		}
		LogUtil.warningLog("operator is null");
		return payList;
	}

	/**
	 * 进行支付
	 * 
	 * @param payCode
	 * @param orderNumber
	 * @param activity
	 * @param callback
	 */
	public static void doPayMent(String payCode, String orderNumber,
			Activity activity) {
		if (StringUtil.isStrValid(payCode)
				&& StringUtil.isStrValid(orderNumber) && activity != null) {
			// 渠道包根据手机卡选择运营商
			List<ItemPayInfo> payItems = getPayItemListByPayCode(payCode,
					payment.getOperator());
			if (payItems == null || payItems.size() == 0) {
				LogUtil.warningLog("payItem is null");
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_FAILURE, "payItem is null",
						orderNumber);
				return;
			}
			// 如果payItems.size大于1，则弹出支付选择界面
			if (payItems.size() > 1) {
				MultiPayDialog.getMultiPayDialog(activity).doPayDialog(
						orderNumber, payItems);
			} else if (payItems.size() == 1) {
				doPayMent(orderNumber, activity, payItems.get(0));
			} else {
				LogUtil.warningLog("payItems.size 为0");
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_FAILURE, "payItems.size is 0",
						orderNumber);
			}
		} else {
			LogUtil.warningLog("doPayMent bad argument");
			if (orderNumber == null) {
				orderNumber = "orderNumber";
			}
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "doPayMent bad argument",
					orderNumber);
		}

	}

	/**
	 * 根据支付类型进行支付
	 * 
	 * @param payCode
	 * @param orderNumber
	 * @param activity
	 * @param callback
	 */
	public static void doPayMent(String orderNumber, Activity activity,
			ItemPayInfo itemPayInfo) {
		if (StringUtil.isStrValid(orderNumber) && activity != null) {
			// 渠道包根据手机卡选择运营商
			if (itemPayInfo == null) {
				LogUtil.warningLog("payItem is null");
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_FAILURE, "feeCode is null",
						orderNumber);
				return;
			}
			// 第三方支付
			if (itemPayInfo.getPaytype().getOperators()
					.equals(THIRD_CHANNEL_OP)) {
				channelThirdManager.pay(activity, itemPayInfo.getPayItems(),
						orderNumber);
			} else {
				String feeCode = itemPayInfo.getPayItems().getFeeCode();
				if (feeCode == null) {
					CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
							ReturnCode.PAY_MSG_FAILURE, "feeCode is null",
							orderNumber);
					return;
					// feeCode = payCode.substring(payCode.length()-3,
					// payCode.length());
					// LogUtil.infoLog("feeCode:"+feeCode);
				}
				channelOpManager.pay(activity, itemPayInfo.getPayItems(),
						orderNumber);
			}
		} else {
			LogUtil.warningLog("doPayMent bad argument");
			if (orderNumber == null) {
				orderNumber = "orderNumber";
			}
			CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
					ReturnCode.PAY_MSG_FAILURE, "doPayMent bad argument",
					orderNumber);
		}

	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void init(Activity context) {
		// 初始化SDK
		PaymentManager.getChannelOpManager().init(context);
		// 初始化SDK
		PaymentManager.getChannelThirdManager(context).init(context);
	}

	/**
	 * 退出
	 */
	public static void exit(Activity context) {
		String operator = PaymentManager.getPayment().getOperator();
		if (operator == null
				|| operator.equalsIgnoreCase(OperatorType.OPERATOR_ALL)) {
			PaymentManager.getChannelThirdManager(context).exit(context);
		} else {
			PaymentManager.getChannelOpManager().exit(context);
		}
		
	}

}
