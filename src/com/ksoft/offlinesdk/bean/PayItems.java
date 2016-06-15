package com.ksoft.offlinesdk.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付方式下的商品
 * @author new
 *
 */
@XStreamAlias("paygood")
public class PayItems {
	
	/**
	 * 商品id
	 */
	private String itemId;

	/**
	 * 商品价格
	 */
	private String money;

	/**
	 * 计费代码
	 */
	private String feeCode;

	/**
	 * 商品名称
	 */
	private String goodsName;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
//	/**
//	 * 折扣信息
//	 */
//	private String sellOff;
	
	
}
