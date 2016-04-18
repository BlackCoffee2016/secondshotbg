package com.kongzhong.gw2.secondshotbg.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.kongzhong.external.webservice.WebserviceLogStorage;
import com.kongzhong.external.webservice.WebserviceLogType;
import com.kongzhong.gw2.secondshotbg.domain.WebServiceLog;
import com.kongzhong.gw2.secondshotbg.mapper.WebserviceLogMapper;
/**
 * 插入webservice日志
 * @author yangcan
 *
 */
public class LogWebService implements WebserviceLogStorage{

	
	@Autowired
	private WebserviceLogMapper webserviceLogMapper;
	
	@Override
	public void save(String username, String orderId, String webserviceInput,String webserviceOutput, WebserviceLogType type) {
		WebServiceLog webServiceLog=new WebServiceLog();
		webServiceLog.setMethod(type.toString());
		webServiceLog.setOrderId(orderId);
		webServiceLog.setParams(webserviceInput);
		webServiceLog.setResult(webserviceOutput);
		webServiceLog.setUserName(username);
		webserviceLogMapper.saveWebServiceLog(webServiceLog);
	}
	
}
