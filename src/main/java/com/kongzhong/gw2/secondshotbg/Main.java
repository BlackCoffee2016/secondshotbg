package com.kongzhong.gw2.secondshotbg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kongzhong.gw2.secondshotbg.service.MainService;

public class Main {

	private static long sleepTime=10000;//休眠10秒
	
	public static void main(String[] args) {
		if(args!=null && args.length==1){
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			MainService mainService = context.getBean(MainService.class);
			sleepTime=Long.parseLong(args[0]);
			mainService.service(sleepTime);
		}else{
			System.out.println("请输入服务的休眠时间....");
		}

	}

}
