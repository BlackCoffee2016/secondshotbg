package com.kongzhong.gw2.secondshotbg.service;

import java.util.Map;

import com.kongzhong.gw2.secondshotbg.domain.Cdkey;

/**
 * 获取批次号ID和校验码
 * @author yangcan
 *
 */
public class CdkeyTypeService {
	
	private Map<Integer,Cdkey> map;

	public Map<Integer, Cdkey> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Cdkey> map) {
		this.map = map;
	}
	
	
}
