package com.hello.redis;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.hello.model.FeedBackInfo;
import com.hello.model.HoursePublishInfo;
import com.hello.utils.MessagePackUtils;
import com.hello.utils.TimeDateUtil;

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
	
	
//	
//	 @Test
//	  public void zrange() throws IOException {
//		 JedisPool pool = new JedisPool(jedisPoolConfig, "localhost");
//		 try (Jedis jedis = pool.getResource()) {
//			 List<String>imgList=new ArrayList<String>();
//			 HoursePublishInfo hourseInfo=new HoursePublishInfo("4444", "dddd", 3, 4, 5, 66, 4544," 33"," 45"," 345", "345", "345", imgList, TimeDateUtil.getCurrentTime());
//			 List<FeedBackInfo>backList=new ArrayList<FeedBackInfo>();
//			 for(int i=0;i<4;i++){
//				 FeedBackInfo f=new FeedBackInfo();
//				 f.setCreateTime(345345);
//				 f.setId(i+"");
//				 f.setMessage("msg"+i);
//				 f.setUserEmail("userEmail"+i);
//				 backList.add(f);
//				 for(int j=0;j<4;j++){
//					 FeedBackInfo d=new FeedBackInfo();
//					 d.setCreateTime(345345);
//					 d.setId(i+"");
//					 d.setMessage("ch    msg"+i);
//					 d.setUserEmail("userEmail"+i);
//					 if(f.getLeaving()==null){
//						 List<FeedBackInfo>bList=new ArrayList<FeedBackInfo>();
//						 f.setLeaving(bList);
//					 }
//					 f.getLeaving().add(d);
//				 }
//			 }
//			 hourseInfo.setFeedBackList(backList);
//			 
//			byte[] write = MessagePackUtils.getBytes(hourseInfo);
//			HoursePublishInfo h = MessagePackUtils.byte2Object(write, HoursePublishInfo.class);
//			
//			System.out.println(h.getId());
//			for(FeedBackInfo f:h.getFeedBackList()){
//				System.out.println(f.getMessage());
//				for(FeedBackInfo d:f.getLeaving()){
//					System.out.println(d.getMessage());
//				}
//			}
////			jedis.del("1836311765@qq.com");
////			jedis.set("1836311765@qq.com", "222");
////		    jedis.zadd("1836311765@qq.com", TimeDateUtil.getCurrentTime(), "c");
////		    jedis.zadd("1836311765@qq.com", 10d, "b");
////		    jedis.zadd("1836311765@qq.com", 0.1d, "c");
////		    jedis.zadd("1836311765@qq.com", 2d, "a");
////		    jedis.zadd("1836311765@qq.com", 2d, "d");
////		    jedis.zadd("f1836311765@qq.1836311765@qq.com", 2d, "e");
//	
////		    Set<String> expected = new LinkedHashSet<String>();
////		    expected.add("c");
////		    expected.add("a");
////	
////		    Set<String> range = jedis.zrange("1836311765@qq.com", 0, 10);
////		    System.out.println(ArrayUtils.toString(range));
////		    assertEquals(expected, range);
////	
////		    expected.add("b");
////		    range = jedis.zrange("1836311765@qq.com", 0, 100);
////		    assertEquals(expected, range);
//
//		 }
//	  }
}
