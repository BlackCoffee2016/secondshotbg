package com.kongzhong.gw2.secondshotbg.domain;
/**
 * webservice 调用日志 实体
 * @author yangcan
 *
 */
public class WebServiceLog {
	private String userName;//空中网账号
	
	private String method;//查余额,扣点,道具发送
	
	private String params;
	
	private String result;
	
	private String orderId;//订单号

	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
