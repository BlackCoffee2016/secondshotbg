package com.kongzhong.gw2.secondshotbg.domain;

import java.util.Date;

/**
 * 订单对象
 * @author yangcan
 *
 */
public class Order {
	private String orderId;//订单号
	
	private String account;//账号
	
	private Date createDate;//创建时间
	
	private Integer price;//价格
	
	private Integer termId;//某一个商品对应的期数ID
	
	private Integer count;//秒杀的数量
	
	private Integer status;//订单状态
	
	private String ip;//ip地址
	
	private Date lastUpdateTime;//最后修改时间
	
	private Integer cdkeyType;//cdkey类型，和商品字典表中ID对应
	
	private Integer payType;//支付类型

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getCdkeyType() {
		return cdkeyType;
	}

	public void setCdkeyType(Integer cdkeyType) {
		this.cdkeyType = cdkeyType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
