package com.kongzhong.gw2.secondshotbg.mapper;

import java.util.Map;

import com.kongzhong.gw2.secondshotbg.domain.Order;

public interface OrderMapper {
	
	Order getOrderByOrderId(String orderId);//通过订单号来获取一条订单信息
	
	void updateOrder(Map<String, Object> map);//根据订单号修改订单的状态和支付类型
	
}
