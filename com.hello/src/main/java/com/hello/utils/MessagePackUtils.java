package com.hello.utils;

import java.io.IOException;

import org.msgpack.MessagePack;

public class MessagePackUtils {
	private static final MessagePack msgpack = new MessagePack();
	
	@SuppressWarnings("unchecked")
	public static <T> T byte2Object(byte[]msg,Class<T> t) throws IOException{
       Object read = msgpack.read(msg,t);
       return (T)read;
	}
	
	public static byte[]getBytes(Object obj) throws IOException{
		return msgpack.write(obj);
	}
}
