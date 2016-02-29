package com.hello.model;

import java.util.ArrayList;
import java.util.List;

import org.msgpack.annotation.Message;

@Message 
public class FeedBackInfo {
	private String id;

	private String userEmail;
	
	private String userHeader;

	private String message;

	private int createTime;

	private List<FeedBackInfo> leaving;
	

	/**
	 * @return the userHeader
	 */
	public String getUserHeader() {
		return userHeader;
	}

	/**
	 * @param userHeader the userHeader to set
	 */
	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the createTime
	 */
	public int getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the leaving
	 */
	public List<FeedBackInfo> getLeaving() {
		return leaving;
	}

	/**
	 * @param leaving
	 *            the leaving to set
	 */
	public void setLeaving(List<FeedBackInfo> leaving) {
		this.leaving = leaving;
	}
	
	public FeedBackInfo() {
		
	}

	public FeedBackInfo(String id, String userEmail, String message,
			int createTime,String userHeader) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.message = message;
		this.createTime = createTime;
		this.userHeader=userHeader;
		this.leaving=new ArrayList<FeedBackInfo>();
	}

	
}
