package com.hello.redis;

public class RedisConstants {

	public static final byte[]userKey;
	
	public static final String activityCodeKey;
	
	public static final byte[]publishKey;
	
	public static final String publicOrderKey;
	
    //用户和留言的映射<email,publishId>
	public static final String emailPublishKey;
	
	static{
		userKey="userKey".getBytes();
		activityCodeKey="activityCodeKey";
		publishKey="publishKey".getBytes();
		emailPublishKey="emailPublishKey";
		publicOrderKey="publicOrderKey";
	}
}
