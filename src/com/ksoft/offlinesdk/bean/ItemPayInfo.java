package com.ksoft.offlinesdk.bean;

/**
 * 商品的支付方式列表
 * @author new
 *
 */
public class ItemPayInfo {
	
	/**
	 * 商品信息
	 */
	private PayItems payItems;
	
	/**
	 * 支付类型信息
	 */
	private Paytype paytype;

	public PayItems getPayItems() {
		return payItems;
	}

	public void setPayItems(PayItems payItems) {
		this.payItems = payItems;
	}

	public Paytype getPaytype() {
		return paytype;
	}

	public void setPaytype(Paytype paytype) {
		this.paytype = paytype;
	} 
	
}
