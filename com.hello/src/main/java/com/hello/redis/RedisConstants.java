package com.hello.redis;

public class RedisConstants {

	public static final byte[]userKey;
	
	public static final String activityCodeKey;
	
	static{
		userKey="userKey".getBytes();
		activityCodeKey="activityCodeKey";
	}
}
