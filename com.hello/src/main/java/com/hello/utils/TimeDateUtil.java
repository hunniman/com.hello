package com.hello.utils;

public class TimeDateUtil {

	/**
	 * 获取当前时间
	 * @name getCurrentTime
	 * @return 当前以int类型返回的数值
	 */
	public static int getCurrentTime(){
		return (int) (System.currentTimeMillis()/1000);
	}
	
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrentTime());
		System.out.println((int)System.currentTimeMillis());
	}
	
}
