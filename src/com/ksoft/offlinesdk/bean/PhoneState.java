package com.ksoft.offlinesdk.bean;

/**
 * sim卡相关讯息
 * @author syk
 *
 */
public class PhoneState {
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 运营商
	 */
	private String operator;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
