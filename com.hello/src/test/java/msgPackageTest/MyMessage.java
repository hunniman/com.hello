package msgPackageTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.annotation.Message;

@Message // Annotation 
public class MyMessage {
	private String name;  
	private double version;  
	private List<String> list = new ArrayList<String>();  
	private Map<String, String> map = new HashMap<String, String>();  
	private List<Message2> list2 = new ArrayList<Message2>();  
	private Map<String, Message2> map2 = new HashMap<String, Message2>();  
	private Message2 message;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(double version) {
		this.version = version;
	}
	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}
	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}
	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	/**
	 * @return the list2
	 */
	public List<Message2> getList2() {
		return list2;
	}
	/**
	 * @param list2 the list2 to set
	 */
	public void setList2(List<Message2> list2) {
		this.list2 = list2;
	}
	/**
	 * @return the map2
	 */
	public Map<String, Message2> getMap2() {
		return map2;
	}
	/**
	 * @param map2 the map2 to set
	 */
	public void setMap2(Map<String, Message2> map2) {
		this.map2 = map2;
	}
	/**
	 * @return the message
	 */
	public Message2 getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(Message2 message) {
		this.message = message;
	}  
	
	
	
}
