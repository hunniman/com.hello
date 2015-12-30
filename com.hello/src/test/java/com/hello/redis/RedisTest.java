package com.hello.redis;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })  
public class RedisTest {

	@Test
    public void testSave() {
		System.out.println("foobar");
		assertEquals("bar", "bar");  
    }
	
	@Resource
	private JedisPoolConfig jedisPoolConfig;
	
//	@Test
//	public void testConnection(){
//		JedisPool pool = new JedisPool(jedisPoolConfig, "localhost");
//		/// Jedis implements Closable. Hence, the jedis instance will be auto-closed after the last statement.
//		try (Jedis jedis = pool.getResource()) {
//		  /// ... do stuff here ... for example
////		  jedis.set("foo", "bar");
//		  String foobar = jedis.get("foo");
////		  jedis.zadd("sose", 0, "car"); jedis.zadd("sose", 0, "bike"); 
////		  Set<String> sose = jedis.zrange("sose", 0, -1);
//		  System.out.println(foobar);
//		  assertEquals("bar", foobar);  
//		}
//		/// ... when closing your application:
//		pool.destroy();
//	}
//	
	
//	@Test
//	public void testHashes (){
//		JedisPool pool = new JedisPool(jedisPoolConfig, "localhost");
//		/// Jedis implements Closable. Hence, the jedis instance will be auto-closed after the last statement.
//		try (Jedis jedis = pool.getResource()) {
//			
//			jedis.hset("joker", "name", "weige");
//			jedis.hset("joker", "age", "11");
//			jedis.hset("joker", "address", "GZ");
//			
//			assertEquals("weige",  jedis.hget("joker", "name")); 
//			assertEquals("11",  jedis.hget("joker", "age")); 
//			assertEquals("GZ",  jedis.hget("joker", "address")); 
//		}
//		/// ... when closing your application:
//		pool.destroy();
//	}
//	
//	
//	@Test
//	public void testLists(){
//		JedisPool pool = new JedisPool(jedisPoolConfig, "localhost");
//		/// Jedis implements Closable. Hence, the jedis instance will be auto-closed after the last statement.
//		try (Jedis jedis = pool.getResource()) {
//			
//			jedis.lpush("pro", "app","mouse","org","java");
//			
//			List<String> lrange = jedis.lrange("pro", 0, jedis.llen("pro"));
//			System.out.println(ArrayUtils.toString(lrange));
//			
//			String lpop = jedis.lpop("pro");
//			assertEquals("app",  lpop); 
//			
//		}
//		/// ... when closing your application:
//		pool.destroy();
//	}
	
	
	
	 @Test
	  public void zrange() {
		 JedisPool pool = new JedisPool(jedisPoolConfig, "localhost");
		 try (Jedis jedis = pool.getResource()) {
			jedis.del("foo1");
		    jedis.zadd("foo1", 1d, "a");
		    jedis.zadd("foo1", 10d, "b");
		    jedis.zadd("foo1", 0.1d, "c");
		    jedis.zadd("foo1", 2d, "a");
		    jedis.zadd("foo1", 2d, "d");
		    jedis.zadd("foo1", 2d, "e");
	
		    Set<String> expected = new LinkedHashSet<String>();
		    expected.add("c");
		    expected.add("a");
	
		    Set<String> range = jedis.zrange("foo1", 0, 10);
		    System.out.println(ArrayUtils.toString(range));
		    assertEquals(expected, range);
	
		    expected.add("b");
		    range = jedis.zrange("foo1", 0, 100);
		    assertEquals(expected, range);

		 }
	  }
}
