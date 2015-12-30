package com.hello;

import java.io.File;
import java.io.IOException;

import com.hello.utils.EncryptionUtil;
import com.hello.utils.email.EmailUtil;

import net.coobird.thumbnailator.Thumbnails;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//			Thumbnails.of(new File("F:\\ee.jpg")).size(434,951).toFile("F:\\ee3.jpg");
//		EmailUtil.send("jjj", "<h2>ssssssss</h2>", "1836311765@qq.com");
		
//		System.out.println("userKey".getBytes());
		try {
			System.out.println(EncryptionUtil.encode("1836311765@qq.com"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
