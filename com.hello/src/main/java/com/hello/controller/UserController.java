package com.hello.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import com.hello.common.Constants;
import com.hello.model.FeedBackInfo;
import com.hello.model.HoursePublishInfo;
import com.hello.model.UserInfo;
import com.hello.redis.RedisConstants;
import com.hello.redis.RedisUtils;
import com.hello.utils.EncryptionUtil;
import com.hello.utils.ExtensionUtils;
import com.hello.utils.JsonUtil;
import com.hello.utils.MessagePackUtils;
import com.hello.utils.TimeDateUtil;
import com.hello.utils.email.EmailUtil;

@Controller
public class UserController extends BaseController {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RedisUtils redisUtils;
	
	@RequestMapping("/index")
	public  ModelAndView  index(){
//		List<Blog> find = Blog.dao.find("select * from blog ");
		List<HoursePublishInfo> find=new ArrayList<HoursePublishInfo>();
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				
				Set<String> zrange = jedis.zrange(RedisConstants.publicOrderKey, 0, jedis.zcard(RedisConstants.publicOrderKey));
				for(String pid:zrange){
					byte[] hget = jedis.hget(RedisConstants.publishKey, pid.getBytes());
					HoursePublishInfo hoursePublishInfo = MessagePackUtils.byte2Object(hget,HoursePublishInfo.class);
					hoursePublishInfo.convertTime2Show();
					find.add(hoursePublishInfo);
				}
			}
		} catch (Exception e) {
			log.error("test error,",e);
		}
		System.err.println(request.getServerName()+":"+ request.getServerPort()+request.getContextPath());
		return new ModelAndView("test", "list", find);  
	}
	
	
	/**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/fileUpload")
    public @ResponseBody String addUser(@RequestParam MultipartFile[] editorFile, HttpServletRequest request) throws IOException{
        //可以在上传文件的同时接收其它参数
        System.out.println("收到用户[]的文件上传请求");
        System.out.println(editorFile.length);
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/cacheUpload");
        //设置响应给前台内容的数据格式
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        for(MultipartFile myfile : editorFile){
            if(myfile.isEmpty()){
                return "请选择文件后上传";
            }else{
                originalFilename = myfile.getOriginalFilename();
                System.out.println("文件原名: " + originalFilename);
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("========================================");
                try {
                    //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                    //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
//                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));
                    Thumbnails.of(myfile.getInputStream()).scale(1).toFile(new File(realPath, originalFilename));
//                    Thumbnails.of(new File("F:\\ee.jpg")).size(434,951).toFile("F:\\ee3.jpg");
                } catch (IOException e) {
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
                    return "文件上传失败，请重试！！";
                }
            }
        }
        //此时在Windows下输出的是[D:\Develop\apache-tomcat-6.0.36\webapps\AjaxFileUpload\\upload\愤怒的小鸟.jpg]
        //System.out.println(realPath + "\\" + originalFilename);
        //此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]
        //System.out.println(request.getContextPath() + "/upload/" + originalFilename);
        //不推荐返回[realPath + "\\" + originalFilename]的值
        //因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img src="D:/aa.jpg">firefox是不认的
//        out.print("0`" + request.getContextPath() + "/upload/" + originalFilename);
        return request.getContextPath() + "/upload/" + originalFilename;
    }
    
    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @RequestMapping(value="/generateCharCode")
    public void generateCharCode( HttpServletRequest request , HttpServletResponse response){
    	try {
    		RandomValidateCode randomValidateCode = RandomValidateCode.getInstance();
    		randomValidateCode.getRandcode(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
	@RequestMapping("/signUp")
	public String  signUp(){
		return "signUp"; 
	}
	
	
	@RequestMapping("/login")
	public String  login(){
		return "login"; 
	}
	
	
	@RequestMapping("/checkEmail")
	@ResponseBody
	public String checkEmail(@RequestParam String email,HttpServletResponse response){
		  Map<String,Object>result=new HashMap<String,Object>(1);
		  try (Jedis jedis =redisUtils.getJedisPool().getResource()) {
				if(jedis.hexists(RedisConstants.userKey, email.getBytes())){
					result.put("valid", false);
				}else{
					result.put("valid", true);
				}
           return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		 }
	}
	
	

	@RequestMapping(path ="/doSignUp", method = RequestMethod.POST)
	@ResponseBody
	public String doSignUp(@RequestParam String email,@RequestParam String textPwd,@RequestParam String textConfirmPwd,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>result=new HashMap<String,Object>(1);
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				if(!textPwd.equals(textConfirmPwd)||jedis.exists(email)){
					result.put("valid", FAILED);
				}else{
					String encodePwd=EncryptionUtil.md5(textPwd);
					String activeCode=EncryptionUtil.encode(email);
					UUID uuid = UUID.randomUUID();
					UserInfo userInfo=new UserInfo(uuid.toString(),email, encodePwd, "", TimeDateUtil.getCurrentTime(), TimeDateUtil.getCurrentTime(), "");
					byte[] write = MessagePackUtils.getBytes(userInfo);
					jedis.hset(RedisConstants.userKey, email.getBytes(), write);
					result.put("valid", SUCCESS);
					jedis.hset(RedisConstants.activityCodeKey,activeCode, email);
					String activityUrl=request.getServerName()+":"+ request.getServerPort()+request.getContextPath()+"/doActivity?token="+activeCode;
					//发邮件
					String mailContent= Constants.REGEMAILCCONTENT+"<a href='"+activityUrl+"'>"+activityUrl+"</a>";
					EmailUtil.send("激活邮件", mailContent, email);
				}
			}
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		} catch (Exception e) {
			log.error("{} {} doSignUp",email,textPwd,e);
			result.put("valid", FAILED);
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		}
	}
	
	
	
	@RequestMapping(path ="/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public String doLogin(@RequestParam String email,@RequestParam String textPwd,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>result=new HashMap<String,Object>(1);
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
					String encodePwd=EncryptionUtil.md5(textPwd);
					byte[] hget = jedis.hget(RedisConstants.userKey, email.getBytes());
					UserInfo user = MessagePackUtils.byte2Object(hget, UserInfo.class);
//					if(user==null){
//						result.put("valid", FAILED);
//					}else if(!StringUtils.equals(encodePwd, user.getPassword())){
//						result.put("valid", FAILED);
//					}else{
//						result.put("valid", SUCCESS);
//						request.getSession().setAttribute(Constants.USER_SESSION,generalSessinonUser(user));
//					}
					
					result.put("valid", SUCCESS);
					request.getSession().setAttribute(Constants.USER_SESSION,generalSessinonUser(user));
			}
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		} catch (Exception e) {
			log.error("{} {} doLogin",email,textPwd,e);
			result.put("valid", FAILED);
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		}
	}
	
	
	@RequestMapping(path ="/doActivity", method = RequestMethod.GET)
	public ModelAndView doActivity(@RequestParam String token,HttpServletRequest request){
		int result=Constants.STATUS_SUCCESS;
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				if(jedis.hexists(RedisConstants.activityCodeKey, token)){
					String email=jedis.hget(RedisConstants.activityCodeKey, token);
					byte[] hget = jedis.hget(RedisConstants.userKey, email.getBytes());
					UserInfo user = MessagePackUtils.byte2Object(hget, UserInfo.class);
					user.setIsActive((byte)1);
					jedis.hdel(RedisConstants.activityCodeKey, token);
					byte[] write = MessagePackUtils.getBytes(user);
					jedis.hset(RedisConstants.userKey, user.getEmail().getBytes(), write);
					request.getSession().setAttribute(Constants.USER_SESSION,generalSessinonUser(user));
				}else{
					result=Constants.STATUS_FAILURE;
				}
			}
			return new ModelAndView("activityBack","result",result);
		} catch (Exception e) {
			log.error("doActivity err",e);
			return new ModelAndView("activityBack","result",result);
		}
	}
	
	
	@RequestMapping(path ="/baseInfo", method = RequestMethod.GET)
	public ModelAndView baseInfo(HttpServletRequest request){
		UserInfo userReturn=null;
		try {
			userReturn=getUser();
			if(userReturn==null){
				return index();
			}
			return new ModelAndView("baseInfo","userInfo",userReturn);
		} catch (Exception e) {
			log.error("baseInfo err",e);
			return new ModelAndView("baseInfo","userInfo",userReturn);
		}
	}
	
	/**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/headerPhoUpload")
    public @ResponseBody String headerPhoUpload(@RequestParam MultipartFile[] editorFile, HttpServletRequest request) throws IOException{
       try {
    	UserInfo user = getUser();
        if(user==null)
        	return FAILED;
    	//可以在上传文件的同时接收其它参数
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.HEADERCACHE);
        //设置响应给前台内容的数据格式
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        for(MultipartFile myfile : editorFile){
            if(myfile.isEmpty()){
                return FAILED;
            }else{
                originalFilename =EncryptionUtil.encode(user.getEmail())+"."+ExtensionUtils.getExtension(myfile.getOriginalFilename());;
                System.out.println("文件原名: " + originalFilename);
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("========================================");
                    //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                    //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
//                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));
                	BufferedImage bi = ImageIO.read(myfile.getInputStream());
                	if(bi.getWidth()>100||bi.getHeight()>100){
                		double scare=1;
                		if(bi.getWidth()>500){
                			scare=(double)500/bi.getWidth();
                		}else if(bi.getHeight()>500){
                			scare=(double)500/bi.getHeight();
                		}
                		Thumbnails.of(myfile.getInputStream()).scale(scare).toFile(new File(realPath, originalFilename));
                	}else if(bi.getWidth()<100||bi.getHeight()<100){
                		Thumbnails.of(myfile.getInputStream()).size(100,100).toFile(new File(realPath, originalFilename));
                	}else{
                		Thumbnails.of(myfile.getInputStream()).scale(1).toFile(new File(realPath, originalFilename));
                	}
//                  Thumbnails.of(new File("F:\\ee.jpg")).size(434,951).toFile("F:\\ee3.jpg");
            }
        }
        //此时在Windows下输出的是[D:\Develop\apache-tomcat-6.0.36\webapps\AjaxFileUpload\\upload\愤怒的小鸟.jpg]
        //System.out.println(realPath + "\\" + originalFilename);
        //此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]
        //System.out.println(request.getContextPath() + "/upload/" + originalFilename);
        //不推荐返回[realPath + "\\" + originalFilename]的值
        //因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img src="D:/aa.jpg">firefox是不认的
//        out.print("0`" + request.getContextPath() + "/upload/" + originalFilename);
//        return request.getContextPath() + "/upload/" + originalFilename;
        	return Constants.HEADERCACHE+"/" + originalFilename;
       } catch (Exception e) {
    	    log.error("headerPhoUpload err",e);
			return FAILED;
		}
    }
    
    
    
    
    @RequestMapping(value="/cutheaderPho")
    public @ResponseBody String cutheaderPho(@RequestParam String fileName,@RequestParam int x,@RequestParam int y,@RequestParam int w,@RequestParam int h){
    	ImageInputStream iis = null;  
    	try {
    		 UserInfo user=getUser();
    		 if(user==null)return FAILED;
    		 
    		 fileName= ExtensionUtils.getFileName(fileName);
    		 log.debug(fileName+":"+x+":"+y+":"+w+":"+h);
    		 String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.HEADERCACHE);
    		 File f=new File(realPath+"\\"+fileName);
    		 if(!f.exists()){
    			 return FAILED;
    		 }
    		 BufferedImage bi = (BufferedImage)ImageIO.read(f);  
    		 h = Math.min(h, bi.getHeight());     
    		  w = Math.min(w, bi.getWidth());     
    		  if(h <= 0) h = bi.getHeight();     
    		  if(w <= 0) w = bi.getWidth();     
    		  x = Math.min(Math.max(0, x), bi.getHeight()-h);     
    		  y = Math.min(Math.max(0, y), bi.getWidth()-w);  
    		 BufferedImage bi_cropper = bi.getSubimage(x, y, w, h);
    		 String outPath = request.getSession().getServletContext().getRealPath("/"+Constants.HEADERIMAGE)+"\\"+fileName;
    		 ImageIO.write(bi_cropper,ExtensionUtils.getExtension(fileName), new File(outPath));  
    		 user.setHeaderImage(Constants.HEADERIMAGE+"/" + fileName);
    		 try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
 				byte[] write = MessagePackUtils.getBytes(user);
 				jedis.hset(RedisConstants.userKey, user.getEmail().getBytes(), write);
 			}
    		 f.delete();
    		 return Constants.HEADERIMAGE+"/" + fileName;
		} catch (Exception e) {
			log.error("{} cutheaderPho error",e);
			return FAILED;
		}finally{
			if(iis!=null)
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    }
    
    
    @RequestMapping(path ="/updateBaseInfo", method = RequestMethod.POST)
	@ResponseBody
	public String updateBaseInfo(@RequestParam String userName,@RequestParam String gender,@RequestParam String contract,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>result=new HashMap<String,Object>(1);
		try {
			UserInfo user= getUser();
			if(user==null){
				result.put("valid", FAILED);
				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);	
			}
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				byte[] hget = jedis.hget(RedisConstants.userKey, user.getEmail().getBytes());
				UserInfo upUser = MessagePackUtils.byte2Object(hget, UserInfo.class);
				
				upUser.setUserName(userName);
				upUser.setGender(Integer.parseInt(gender));
				upUser.setContract(contract);
				byte[] write = MessagePackUtils.getBytes(upUser);
				jedis.hset(RedisConstants.userKey, user.getEmail().getBytes(), write);
				request.getSession().setAttribute(Constants.USER_SESSION, generalSessinonUser(upUser));
			}
			result.put("valid", SUCCESS);
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		} catch (Exception e) {
			result.put("valid", FAILED);
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		}
	}
    
    
    
    @RequestMapping(path ="/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@RequestParam String textPwd,@RequestParam String textConfirmPwd,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>result=new HashMap<String,Object>(1);
		try {
			UserInfo sessionUser=getUser();
			if(sessionUser==null){
				result.put("valid", FAILED);
				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
			}
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				if(!textPwd.equals(textConfirmPwd)){
					result.put("valid", FAILED);
				}else{
					String encodePwd=EncryptionUtil.md5(textPwd);
					
					byte[] hget = jedis.hget(RedisConstants.userKey, sessionUser.getEmail().getBytes());
					UserInfo upUser = MessagePackUtils.byte2Object(hget, UserInfo.class);
					upUser.setPassword(encodePwd);
					byte[] write = MessagePackUtils.getBytes(upUser);
					jedis.hset(RedisConstants.userKey, sessionUser.getEmail().getBytes(), write);
					request.getSession().setAttribute(Constants.USER_SESSION, generalSessinonUser(upUser));
					result.put("valid", SUCCESS);
				}
			}
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		} catch (Exception e) {
			log.error("{}updatePassword err",textPwd,e);
			result.put("valid", FAILED);
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
		}
	}
    
    
	@RequestMapping("/publish")
	public String  publish(){
		return "publish"; 
	}
	
	
	
	/**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/hoursePhoUpload")
    public @ResponseBody String hoursePhoUpload(@RequestParam MultipartFile[] editorFile, HttpServletRequest request) throws IOException{
       try {
    	UserInfo user = getUser();
        if(user==null)
        	return FAILED;
    	//可以在上传文件的同时接收其它参数
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.HOURSEUPLOADCACHE);
        //设置响应给前台内容的数据格式
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        for(MultipartFile myfile : editorFile){
            if(myfile.isEmpty()){
                return FAILED;
            }else{
                originalFilename =System.currentTimeMillis()+EncryptionUtil.encode(user.getEmail())+"."+ExtensionUtils.getExtension(myfile.getOriginalFilename());;
                System.out.println("文件原名: " + originalFilename);
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("========================================");
                	BufferedImage bi = ImageIO.read(myfile.getInputStream());
                	if(bi.getWidth()>600||bi.getHeight()>600){
                		double scare=1;
                		if(bi.getWidth()>600){
                			scare=(double)600/bi.getWidth();
                		}else if(bi.getHeight()>600){
                			scare=(double)600/bi.getHeight();
                		}
                		Thumbnails.of(myfile.getInputStream()).scale(scare).toFile(new File(realPath, originalFilename));
                	}else{
                		Thumbnails.of(myfile.getInputStream()).scale(1).toFile(new File(realPath, originalFilename));
                	}
            }
        }
        	return Constants.HOURSEUPLOADCACHE+"/" + originalFilename;
       } catch (Exception e) {
    	    log.error("hourseUpload err",e);
			return FAILED;
		}
    }
    
    
    
    @RequestMapping(path ="/updatePublishData", method = RequestMethod.POST)
  	@ResponseBody
  	public String updatePublishData(@RequestParam String publishId,@RequestParam String title,@RequestParam int room,@RequestParam int ting,@RequestParam int wei,@RequestParam int scare,@RequestParam int money,@RequestParam String declare,@RequestParam String contractPeople,@RequestParam String phone,@RequestParam String qq,@RequestParam String weixin,@RequestParam int floor,@RequestParam int totalFloor,@RequestParam String imgData,@RequestParam String address,HttpServletRequest request,HttpServletResponse response){
  		Map<String,Object>result=new HashMap<String,Object>(1);
  		try {
  			UserInfo user= getUser();
  			if(user==null){
  				result.put("valid", FAILED);
  				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);	
  			}
  			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
  				List<String>imgList=new ArrayList<String>();
  				String imgArr[]=imgData.split("_");
  				for(String path:imgArr){
  					if(StringUtils.isBlank(path)){
  	  				   continue;	
  	  				}
  					 String fileName= ExtensionUtils.getFileName(path);
  		    		 String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.HOURSEUPLOADCACHE);
  		    		 File f=new File(realPath+"\\"+fileName);
  		    		 if(!f.exists()){
  		    			 continue;
  		    		 }
  		    		 BufferedImage bi = (BufferedImage)ImageIO.read(f);  
  		    		 String outPath = request.getSession().getServletContext().getRealPath("/"+Constants.HOURSEIMAGE)+"\\"+fileName;
  		    		 ImageIO.write(bi,ExtensionUtils.getExtension(fileName), new File(outPath));  
  		    		 imgList.add(Constants.HOURSEIMAGE+"/"+fileName);
  		    		 f.delete();
  				}
  				if(StringUtils.isBlank(publishId)){
  					UUID pid = UUID.randomUUID();
  					HoursePublishInfo hourseInfo=new HoursePublishInfo(pid.toString(), title, room, ting, wei, scare, money, declare, contractPeople, phone, qq, weixin, imgList, TimeDateUtil.getCurrentTime(),floor,totalFloor,address);
  					hourseInfo.setUserEmail(user.getEmail());
  					jedis.zadd(user.getEmail(), TimeDateUtil.getCurrentTime(),  pid.toString());//用户对应 发布的id list
  					byte[] write = MessagePackUtils.getBytes(hourseInfo);
					jedis.hset(RedisConstants.publishKey, pid.toString().getBytes(), write);//保存
					jedis.zadd(RedisConstants.publicOrderKey, System.currentTimeMillis(), pid.toString());
  				}else{
  					//修改
  					byte[] hget = jedis.hget(RedisConstants.publishKey, publishId.getBytes());
  					HoursePublishInfo h = MessagePackUtils.byte2Object(hget, HoursePublishInfo.class);
  					if(h==null){
  						result.put("valid", FAILED);
  		  				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);	
  					}
  					h.setTitle(title);
  					h.setRoom(room);
  					h.setWei(wei);
  					h.setScare(scare);
  					h.setMoney(money);
  					h.setDeclare(declare);
  					h.setContractPeople(contractPeople);
  					h.setPhone(phone);
  					h.setQq(qq);
  					h.setWeixin(weixin);
  					if(!imgList.isEmpty())
  						h.setImgData(imgList);
  				}
  			}
  			result.put("valid", SUCCESS);
  			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
  		} catch (Exception e) {
  			log.error("updatePublishData error,",e);
  			result.put("valid", FAILED);
  			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
  		}
  	}
    
    
    
	
	@RequestMapping("/HourseDetail/{id}")
	public  ModelAndView  HourseDetail(@PathVariable("id") String id){
		HoursePublishInfo hoursePublishInfo =null;
		UserInfo pUser=null;
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				    if(jedis.hexists(RedisConstants.publishKey, id.getBytes())){
				    	byte[] hget = jedis.hget(RedisConstants.publishKey, id.getBytes());
				        hoursePublishInfo = MessagePackUtils.byte2Object(hget,HoursePublishInfo.class);
				        hoursePublishInfo.convertTime2Show();
				        byte[] userEnc = jedis.hget(RedisConstants.userKey, hoursePublishInfo.getUserEmail().getBytes());
				        pUser= MessagePackUtils.byte2Object(userEnc,UserInfo.class);
				        pUser=generalSessinonUser(pUser);
				    	hoursePublishInfo.convertTime2Show();
				    }
			}
		} catch (Exception e) {
			log.error("test error,",e);
		}
		ModelAndView mav = new ModelAndView("hourseInfo"); 
		mav.addObject("hoursePublishInfo", hoursePublishInfo);
		mav.addObject("pUser", pUser);
		return mav;
	}
    
	 @RequestMapping(path ="/addLeaving", method = RequestMethod.POST)
	  	@ResponseBody
	  	public String addLeaving(@RequestParam String publishId,@RequestParam String txtLeaving,HttpServletRequest request,HttpServletResponse response){
	  		Map<String,Object>result=new HashMap<String,Object>(1);
	  		try {
	  			UserInfo user= getUser();
	  			if(user==null){
	  				result.put("valid", FAILED);
	  				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);	
	  			}
	  			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
  					//修改
  					byte[] hget = jedis.hget(RedisConstants.publishKey, publishId.getBytes());
  					HoursePublishInfo h = MessagePackUtils.byte2Object(hget, HoursePublishInfo.class);
  					if(h==null){
  						result.put("valid", FAILED);
  		  				return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);	
  					}
  					UUID pid = UUID.randomUUID();
  					FeedBackInfo back=new FeedBackInfo(pid.toString(), user.getEmail(), txtLeaving, TimeDateUtil.getCurrentTime(),user.getHeaderImage());
  					h.getFeedBackList().add(back);
  					byte[] write = MessagePackUtils.getBytes(h);
					jedis.hset(RedisConstants.publishKey, publishId.toString().getBytes(), write);//保存
  					jedis.zadd(publishId, System.currentTimeMillis(), pid.toString());//留言的顺序集合
	  			}
	  			result.put("valid", SUCCESS);
	  			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
	  		} catch (Exception e) {
	  			log.error("addLeaving error,",e);
	  			result.put("valid", FAILED);
	  			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);		
	  		}
	  	}
	
}
