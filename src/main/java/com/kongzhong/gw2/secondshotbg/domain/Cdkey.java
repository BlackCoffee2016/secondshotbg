package com.kongzhong.gw2.secondshotbg.domain;
/**
 * cdkey
 * @author yangcan
 *
 */
public class Cdkey {
	
	private Integer batchId;//cdkey批次号ID
	
	private String batchCode;//校验码

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	
	
}
