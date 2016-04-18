package com.kongzhong.gw2.secondshotbg.domain;

import java.util.Date;

public class TermDetailed {
	
	private Integer termId;
	
	private Integer keyNumber;
	
	private String userName;
	
	private Integer count;
	
	private String orderId;
	
	private String ip;
	
	private Date createDateTime;
	
	private Integer itemId;
	
	private Integer limit;//要更新的数量

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	

	public Integer getKeyNumber() {
		return keyNumber;
	}

	public void setKeyNumber(Integer keyNumber) {
		this.keyNumber = keyNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
}
