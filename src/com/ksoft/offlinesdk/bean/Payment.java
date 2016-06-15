package com.ksoft.offlinesdk.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 支付配置文件
 * @author kk
 *
 */
@XStreamAlias("Payment")
public class Payment {

	/**
	 * 游戏id
	 */
	@XStreamAlias("applicationId")
	private String applicationId;

	/**
	 * 游戏名称
	 */
	@XStreamAlias("applicationName")
	private String applicationName;

	/**
	 * 渠道id
	 */
	@XStreamAlias("channelId")
	private String channelId;

	/**
	 * 运营商，ALL为多渠道，其他为门户包
	 */
	@XStreamAlias("operator")
	private String operator;

	/**
	 * 免责声明，电信门户需要
	 */
	@XStreamAlias("declare")
	private String declare;

	/**
	 * 应用版本号
	 */
	@XStreamAlias("versionCode")
	private String versionCode;
//
//	/**
//	 * 应用版本号
//	 */
//	@XStreamAlias("onLine")
//	private String onLine;
	
	/**
	 * 第三方支付实现类
	 */
	@XStreamAlias("thirdClass")
	private String thirdClass;
	

	/**
	 * 第三方支付实现类
	 */
	@XStreamAlias("thirdAppId")
	private String thirdAppId;
	

	/**
	 * 第三方支付实现类
	 */
	@XStreamAlias("thirdAppKey")
	private String thirdAppKey;
	

	/**
	 * 第三方支付实现类
	 */
	@XStreamAlias("thirdAppSecret")
	private String thirdAppSecret;
	
	/**
	 * 咪咕是否打开
	 */
	@XStreamAlias("cmPayOpen")
	private String cmPayOpen;

	/**
	 * 是否横屏
	 */
	@XStreamAlias("screenLanscape")
	private String screenLanscape;
	
	

	public String getCmPayOpen() {
		return cmPayOpen;
	}

	public void setCmPayOpen(String cmPayOpen) {
		this.cmPayOpen = cmPayOpen;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	/**
	 * 支付方式列表
	 */
	@XStreamImplicit  
	private List<Paytype> payTypes;

	public List<Paytype> getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(List<Paytype> payTypes) {
		this.payTypes = payTypes;
	}

	public String getDeclare() {
		return declare;
	}

	public void setDeclare(String declare) {
		this.declare = declare;
	}

	public String getThirdClass() {
		return thirdClass;
	}

	public void setThirdClass(String thirdClass) {
		this.thirdClass = thirdClass;
	}

	public String getThirdAppId() {
		return thirdAppId;
	}

	public void setThirdAppId(String thirdAppId) {
		this.thirdAppId = thirdAppId;
	}

	public String getThirdAppKey() {
		return thirdAppKey;
	}

	public void setThirdAppKey(String thirdAppKey) {
		this.thirdAppKey = thirdAppKey;
	}

	public String getThirdAppSecret() {
		return thirdAppSecret;
	}

	public void setThirdAppSecret(String thirdAppSecret) {
		this.thirdAppSecret = thirdAppSecret;
	}

	public String getScreenLanscape() {
		return screenLanscape;
	}

	public void setScreenLanscape(String screenLanscape) {
		this.screenLanscape = screenLanscape;
	}
	
}
