package msgPackageTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;


public class Test {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create serialize objects.
//		List<String> src = new ArrayList<String>();
//		src.add("msgpack");
//		src.add("kumofs");
//		src.add("viver");
//		
		MessagePack msgpack = new MessagePack();
		// Serialize
		try {
//			byte[] raw = msgpack.write(src);
//			System.out.println(ArrayUtils.toString(raw));
			
			
			    MyMessage src = new MyMessage();  
		        src.setName("msgpack");  
		        src.setVersion(0.6);  
		        src.getList().add("1111");  
		        src.getList().add("2222");  
		        src.getMap().put("a", "aaaa");  
		        src.getMap().put("b", "bbbb");  
		        src.setMessage(new Message2("============="));  
		        src.getList2().add(new Message2("sssss"));  
		        src.getList2().add(new Message2("ffffff"));  
		        src.getMap2().put("aa", new Message2("xxxx"));  
		        src.getMap2().put("bb", new Message2("zzzz"));  
		         
		        List<MyMessage> srcList = new ArrayList<MyMessage>();
		        srcList.add(src);
		        byte[] bytes = msgpack.write(srcList);  
		          
		        System.out.println(bytes); 
		        ObjectTemplate<MyMessage>objectTemplate = new ObjectTemplate<MyMessage>(MyMessage.class);
		        
		        List<MyMessage> src2=  msgpack.read(bytes,Templates.tList(objectTemplate));
//		        
		        System.out.println(src2.get(0).getMap2().get("aa").name); 
//			
//			// Deserialize directly using a template
//			List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
//			System.out.println(dst1.get(0));
//			System.out.println(dst1.get(1));
//			System.out.println(dst1.get(2));
			
			
//			// Serialize Map object
//	        Map<Integer, user> map = new HashMap<Integer, user>();
//	        
		    List<user>testList=new ArrayList<user>();
	        for(int i=1;i<=10;i++){
	        	user u=new user();
	        	u.setId(i);
	        	u.setName(i+" mynane");
	        	u.setPwd(i+" pwd");
	        	testList.add(u);
	        }
	        byte[] raw2= msgpack.write(testList); // Map object
	        ObjectTemplate<user>objectTemplate2 = new ObjectTemplate<user>(user.class);
	        List<user>userList= msgpack.read(raw2,Templates.tList(objectTemplate2));
	        System.out.println(ArrayUtils.toString(userList.get(0).getName()));
	        
	        
	        Map<Integer,user>testMap=new HashMap<Integer,user>();
	        for(int i=1;i<=10;i++){
	        	user u=new user();
	        	u.setId(i);
	        	u.setName(i+" mynane");
	        	u.setPwd(i+" pwd");
	        	testMap.put(i, u);
	        }
	        
	        byte[] bytesMap = msgpack.write(testMap);  
	        Map<Integer, user> map2 = msgpack.read(bytesMap, Templates.tMap(Templates.TInteger, objectTemplate2));
	        System.out.println(map2.toString());
	        
//			user u=new user();
//        	u.setId(1);
//        	u.setName(1+" mynane");
//        	u.setPwd(1+" pwd");
//        	 for(int i=2;i<=10;i++){
// 	        	user u2=new user();
// 	        	u2.setId(i);
// 	        	u2.setName(i+" mynane");
// 	        	u2.setPwd(i+" pwd");
// 	        	u.getTestList().add(u2);
// 	        }
			
//        	byte[] bs = msgpack.write(u);
//        	System.out.println(ArrayUtils.toString(bs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}

}
