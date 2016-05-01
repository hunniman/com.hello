package com.hello.redis;

public class RedisConstants {

	public static final byte[]userKey;
	
	public static final String activityCodeKey;
	
	public static final byte[]publishKey;
	
	public static final String publicOrderKey;
	
    //用户和发布的房子信息的映射<email,publishId>
	public static final String userIdPublishKey;
	
	//用户和发布的留言信息的映射<publishId,backId>
	public static final String publicIdBackIdKey="publicIdBackIdKey";
	
	static{
		userKey="userKey".getBytes();
		activityCodeKey="activityCodeKey";
		publishKey="publishKey".getBytes();
		userIdPublishKey="userIdPublishKey";
		publicOrderKey="publicOrderKey";
	}
}
