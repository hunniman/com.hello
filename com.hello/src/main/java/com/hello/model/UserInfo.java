package com.hello.model;

import org.msgpack.annotation.Message;

@Message 
public class UserInfo {

	private int id;
	
	private String email;
	
	private String Password;
	
	private String userName;
	
	private int createTime;
	
	private int loginTime;
	
	private byte isActive;
	
	/**
	 * 头像
	 */
    private String headerImage;
	
	/**
	 * 性别 0.女,1.男
	 */
	private int gender;
	
	/**
	 * 联系方式
	 */
	private String contract;
	
	/**
	 * @return the headerImage
	 */
	public String getHeaderImage() {
		return headerImage;
	}
	/**
	 * @param headerImage the headerImage to set
	 */
	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}

	
	
	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	/**
	 * @return the isActive
	 */
	public byte getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the createTime
	 */
	public int getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the loginTime
	 */
	public int getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}

	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the contract
	 */
	public String getContract() {
		return contract;
	}
	/**
	 * @param contract the contract to set
	 */
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	
	public UserInfo(String email, String password, String userName,
			int createTime, int loginTime, String contract) {
		super();
		this.email = email;
		Password = password;
		this.userName = userName;
		this.createTime = createTime;
		this.loginTime = loginTime;
		this.contract = contract;
	}
	
	
	public UserInfo(){}
}
