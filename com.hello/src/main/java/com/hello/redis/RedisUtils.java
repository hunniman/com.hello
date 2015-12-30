package com.hello.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisUtils {

	private RedisUtils(){}
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	
//	private static RedisUtils instance=new RedisUtils();
//	
//	public static RedisUtils getInstance(){
//		return instance;
//	}
	
	private JedisPool jedisPool;
	
	public JedisPool getJedisPool(){
		if(jedisPool==null){
			System.err.println("+++++++++++++++++++++++"+(jedisPoolConfig==null));
			jedisPool= new JedisPool(jedisPoolConfig, "localhost");
		}
		return jedisPool;
	}
}
