package com.ksoft.offlinesdk.manager;

import javax.security.auth.login.LoginException;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ExitResultBean;
import com.ksoft.offlinesdk.bean.InitResultBean;
import com.ksoft.offlinesdk.bean.LoginResultBean;
import com.ksoft.offlinesdk.bean.PayResultBean;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.util.LogUtil;
import com.ksoft.offlinesdk.util.Tools;

/**
 * 回调管理类
 * @author new
 *
 */
public class CallBackManager {

	/**
	 * 初始化回调
	 */
	private static OfflineCallback initCallBack;

	/**
	 * 登录
	 */
	private static OfflineCallback loginCallBack;

	/**
	 * 支付回调
	 */
	private static OfflineCallback payCallBack;

	/**
	 * 退出回调
	 */
	private static OfflineCallback exitCallBack;

	public static OfflineCallback getInitCallBack() {
		return initCallBack;
	}

	public static void setInitCallBack(OfflineCallback initCallBack) {
		CallBackManager.initCallBack = initCallBack;
	}

	public static OfflineCallback getLoginCallBack() {
		return loginCallBack;
	}

	public static void setLoginCallBack(OfflineCallback loginCallBack) {
		CallBackManager.loginCallBack = loginCallBack;
	}

	public static OfflineCallback getPayCallBack() {
		return payCallBack;
	}

	public static void setPayCallBack(OfflineCallback payCallBack) {
		CallBackManager.payCallBack = payCallBack;
	}

	public static OfflineCallback getExitCallBack() {
		return exitCallBack;
	}

	public static void setExitCallBack(OfflineCallback exitCallBack) {
		CallBackManager.exitCallBack = exitCallBack;
	}

	/**
	 * 
	 * @param eventCode 回调的类型
	 * @param code 返回码
	 * @param msg 提示信息 
	 * @param orderNumber 订单号（只在支付的时候传，其他的传null）
	 */
	public static void onCallBack(int eventCode, int code, String msg,
			String orderNumber) {
		LogUtil.infoLog("onCallBack eventCode:"+eventCode+" code:"+code);
		switch (eventCode) {
		case EventCode.INIT_EVENT_CODE:
			if(initCallBack != null){
				InitResultBean initResultBean = new InitResultBean();
				initResultBean.code = code;
				initCallBack.onResponse(eventCode, Tools.ToJson(initResultBean));
			}else{
				LogUtil.warningLog("initCallBack is null");
			}
			break;
		case EventCode.LOGIN_EVENT_CODE:
			if(loginCallBack != null){
				LoginResultBean loginResultBean = new LoginResultBean();
				loginResultBean.code = code;
				loginCallBack.onResponse(eventCode, Tools.ToJson(loginResultBean));
			}else{
				LogUtil.warningLog("loginCallBack is null");
			}
			break;
		case EventCode.PAY_EVENT_CODE:
			if(payCallBack != null && orderNumber != null ){
				if(msg == null){
					msg = "unknow";
				}
				PayResultBean payResultBean = new PayResultBean();
				payResultBean.code = code;
				payResultBean.msg = msg;
				payResultBean.orderId = orderNumber;
				payCallBack.onResponse(eventCode, Tools.ToJson(payResultBean));
			}else{
				LogUtil.warningLog("payCallBack or orderNumber is null");
			}
			break;
		case EventCode.EXIT_EVENT_CODE:
			if(exitCallBack != null){
				ExitResultBean exitResultBean = new ExitResultBean();
				exitResultBean.code = code;
				exitCallBack.onResponse(eventCode, Tools.ToJson(exitResultBean));
			}else{
				LogUtil.warningLog("exitCallBack is null");
			}
			break;

		default:
			break;
		}
	}

}
