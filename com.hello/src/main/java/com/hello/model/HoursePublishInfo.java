package com.hello.model;

import java.util.ArrayList;
import java.util.List;

import org.msgpack.annotation.Message;

import com.hello.utils.TimeDateUtil;

@Message 
public class HoursePublishInfo {

	private String userId;
	
	private String userEmail;
	
	private String id;

	private String title;

	private int room;

	private int ting;

	private int wei;

	private int scare;

	private int money;

	private String declare;

	private String contractPeople;

	private String phone;

	private String qq;

	private String weixin;

	private List<String> imgData=new ArrayList<String>();

	private int createTime;

	private List<FeedBackInfo> feedBackList;
	
	private int floor;
	
	private int totalFloor;
	
	private String address;
	
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 显示日期
	 */
	private String showDay;
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the showDay
	 */
	public String getShowDay() {
		return showDay;
	}

	/**
	 * @param showDay the showDay to set
	 */
	public void setShowDay(String showDay) {
//		this.showDay = showDay;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @param floor the floor to set
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * @return the totalFloor
	 */
	public int getTotalFloor() {
		return totalFloor;
	}

	/**
	 * @param totalFloor the totalFloor to set
	 */
	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	/**
	 * @return the isClose
	 */
	public byte getIsClose() {
		return isClose;
	}

	/**
	 * @param isClose the isClose to set
	 */
	public void setIsClose(byte isClose) {
		this.isClose = isClose;
	}

	/**
	 * 是否已经结贴
	 */
	private byte isClose;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the room
	 */
	public int getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(int room) {
		this.room = room;
	}

	/**
	 * @return the ting
	 */
	public int getTing() {
		return ting;
	}

	/**
	 * @param ting
	 *            the ting to set
	 */
	public void setTing(int ting) {
		this.ting = ting;
	}

	/**
	 * @return the wei
	 */
	public int getWei() {
		return wei;
	}

	/**
	 * @param wei
	 *            the wei to set
	 */
	public void setWei(int wei) {
		this.wei = wei;
	}

	/**
	 * @return the scare
	 */
	public int getScare() {
		return scare;
	}

	/**
	 * @param scare
	 *            the scare to set
	 */
	public void setScare(int scare) {
		this.scare = scare;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the declare
	 */
	public String getDeclare() {
		return declare;
	}

	/**
	 * @param declare
	 *            the declare to set
	 */
	public void setDeclare(String declare) {
		this.declare = declare;
	}

	/**
	 * @return the contractPeople
	 */
	public String getContractPeople() {
		return contractPeople;
	}

	/**
	 * @param contractPeople
	 *            the contractPeople to set
	 */
	public void setContractPeople(String contractPeople) {
		this.contractPeople = contractPeople;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the weixin
	 */
	public String getWeixin() {
		return weixin;
	}

	/**
	 * @param weixin
	 *            the weixin to set
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

    

	/**
	 * @return the imgData
	 */
	public List<String> getImgData() {
		return imgData;
	}

	/**
	 * @param imgData the imgData to set
	 */
	public void setImgData(List<String> imgData) {
		this.imgData = imgData;
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
	 * @return the feedBackList
	 */
	public List<FeedBackInfo> getFeedBackList() {
		return feedBackList;
	}

	/**
	 * @param feedBackList
	 *            the feedBackList to set
	 */
	public void setFeedBackList(List<FeedBackInfo> feedBackList) {
		this.feedBackList = feedBackList;
	}

	public HoursePublishInfo(String id, String title, int room, int ting,
			int wei, int scare, int money, String declare,
			String contractPeople, String phone, String qq, String weixin,
			List<String> imgData, int createTime,int floor,int totalFloor,String address,String userId) {
		super();
		this.id = id;
		this.title = title;
		this.room = room;
		this.ting = ting;
		this.wei = wei;
		this.scare = scare;
		this.money = money;
		this.declare = declare;
		this.contractPeople = contractPeople;
		this.phone = phone;
		this.qq = qq;
		this.weixin = weixin;
		this.imgData = imgData;
		this.createTime = createTime;
		this.feedBackList = new ArrayList<FeedBackInfo>();
		this.floor=floor;
		this.totalFloor=totalFloor;
		this.address=address;
		this.userId=userId;
	}
	
	public HoursePublishInfo(){
	}
	
	public void convertTime2Show(){
		String result=null;
		int currentTime=TimeDateUtil.getCurrentTime();
		int between=currentTime-this.createTime;
		if(between<60){
			result="刚刚";
		}else if(between>60&&between<3600){
			int minute= between/60;
			result=minute+"分钟前";
		}else if(between>3600&&between<3600*24){
			int hour= between/3600;
			result=hour+"小时前";
		}else{
			int day= between/(3600*24);
			result=day+"天前";
		}
		this.showDay=result;
	}

}
