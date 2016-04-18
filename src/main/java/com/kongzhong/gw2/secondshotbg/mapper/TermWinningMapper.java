package com.kongzhong.gw2.secondshotbg.mapper;

import java.util.List;
import java.util.Map;

import com.kongzhong.gw2.secondshotbg.domain.Winning;

public interface TermWinningMapper {
	void addTermWinning(Winning winning);//插入到获奖表中去。
	
	Integer getIsWinning(Map<String, Object> map);//是否获过某一个商品的CDKEY
	
	List<String> getNoCdkey();//查询出没有获取到CDKEY的订单号
	
	void updateCdkey(Map<String, Object> map);//根据订单号来更新CDKEY
}
