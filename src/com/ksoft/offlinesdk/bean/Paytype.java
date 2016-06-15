package com.ksoft.offlinesdk.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 支付方式对象
 * @author kk
 *
 */
@XStreamAlias("paytype")
public class Paytype {
	
	/**
	 * 支付类型ID
	 */
	private String id;

	/**
	 * 支付类型名称
	 */
	private String name;
	
	/**
	 * 运营商区分
	 */
	private String operators;

	/**
	 * 省份
	 */
	private String province;
	
//
//	/**
//	 * 支付方式的appId
//	 */
//	private String appId;
//	
//	/**
//	 * 支付方式的appKey
//	 */
//	private String appKey;
//
//	/**
//	 * 预留字段
//	 */
//	private String other;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOperators() {
		return operators;
	}


	public void setOperators(String operators) {
		this.operators = operators;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public List<PayItems> getPayItems() {
		return payItems;
	}


	public void setPayItems(List<PayItems> payItems) {
		this.payItems = payItems;
	}


	/**
	 * 商品列表
	 */
	@XStreamImplicit
	private List<PayItems> payItems;
	
	
	
}
