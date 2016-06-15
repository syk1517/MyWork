package com.ksoft.offlinesdk.bean;

/**
 * 返回值
 * @author Administrator
 *
 */
public class ReturnCode {

	/**
	 * 支付成功
	 */
	public static final int PAY_MSG_SUCCESS = 9999;

	/**
	 * 支付失败
	 */
	public static final int PAY_MSG_FAILURE = 1000;

	/**
	 * 支付取消
	 */
	public static final int PAY_MSG_CANCEL = 2000;
	
	/**
	 * 登录成功
	 */
	public static final int LOGIN_MSG_SUCCESS = 3000;
	
	/**
	 * 其他异常
	 */
	public static final int LOGIN_MSG_FAILURE = 3001;
	
	/**
	 * 登录取消
	 */
	public static final int LOGIN_MSG_CANCEL = 3002;
	
	
	/**
	 * 初始化成功
	 */
	public static final int INIT_MSG_SUCCESS = 4000;
	
	/**
	 * 初始化失败
	 */
	public static final int INIT_MSG_FAILURE = 4001;

	/**
	 * 退出成功
	 */
	public static final int EXIT_MSG_SUCCESS = 5000;
	
	/**
	 * 退出取消
	 */
	public static final int EXIT_MSG_CANCEL = 5001;
}
