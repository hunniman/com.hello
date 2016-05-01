package com.hello;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.model.UserInfo;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//			Thumbnails.of(new File("F:\\ee.jpg")).size(434,951).toFile("F:\\ee3.jpg");
//		EmailUtil.send("jjj", "<h2>ssssssss</h2>", "1836311765@qq.com");
		
//		System.out.println("userKey".getBytes());
		try {
//			System.out.println(EncryptionUtil.encode("1836311765@qq.com"));
			 ObjectMapper mapper = new ObjectMapper();  
			 UserInfo u=new UserInfo("22", "334", "ee", "eeeeeeeee", 3333333, 333333, "eeee");
			 String json = mapper.writeValueAsString(u);  
			 System.out.println(json);
			 UserInfo readValue = mapper.readValue(json, UserInfo.class);
			 System.out.println(readValue.getEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
