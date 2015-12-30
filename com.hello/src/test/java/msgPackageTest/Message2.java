package msgPackageTest;

import org.msgpack.annotation.Message;

@Message  
public class Message2 {
	public Message2() {

	}
	public Message2(String name) {
		this.name = name;
	}

	public String name;
}
