package com.hello.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msgPackageTest.MyMessage;
import msgPackageTest.ObjectTemplate;
import net.coobird.thumbnailator.Thumbnails;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.hello.common.Constants;
import com.hello.core.Blog;
import com.hello.model.UserInfo;
import com.hello.redis.RedisConstants;
import com.hello.redis.RedisUtils;
import com.hello.utils.EncryptionUtil;
import com.hello.utils.JsonUtil;
import com.hello.utils.MessagePackUtils;
import com.hello.utils.TimeDateUtil;
import com.hello.utils.email.EmailUtil;

@Controller
public class UserController extends BaseController {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RedisUtils redisUtils;
	
	@RequestMapping("/embeddedTestJson")
	public  ModelAndView  test(){
//		List<Blog> find = Blog.dao.find("select * from blog ");
		List<UserInfo> find=new ArrayList<UserInfo>();
		MessagePack msgpack = new MessagePack();
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				Map<byte[], byte[]> hgetAll = jedis.hgetAll(RedisConstants.userKey);
				for(byte[]tempByte:hgetAll.keySet()){
					byte[]value=hgetAll.get(tempByte);
					UserInfo read = msgpack.read(value,UserInfo.class);
					find.add(read);
					jedis.hdel(RedisConstants.userKey, tempByte);
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
					UserInfo userInfo=new UserInfo(email, encodePwd, "", TimeDateUtil.getCurrentTime(), TimeDateUtil.getCurrentTime(), "");
					byte[] write = MessagePackUtils.getBytes(userInfo);
					jedis.hset(RedisConstants.userKey, email.getBytes(), write);
					result.put("valid", SUCCESS);
					jedis.hset(RedisConstants.activityCodeKey,activeCode, email);
					jedis.set(email, activeCode);
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
	
	@RequestMapping(path ="/doActivity", method = RequestMethod.GET)
	public ModelAndView doActivity(@RequestParam String token){
		UserInfo userReturn=new UserInfo();
		try {
			try (Jedis jedis = redisUtils.getJedisPool().getResource()) {
				if(jedis.hexists(RedisConstants.activityCodeKey, token)){
					String email=jedis.hget(RedisConstants.activityCodeKey, token);
//					byte[] hget = jedis.hget(RedisConstants.userKey, email.getBytes());
//					UserInfo user = MessagePackUtils.byte2Object(hget, UserInfo.class);
					userReturn.setEmail(email);
//					if(user.getIsActive()==0){
//						user.setIsActive((byte)1);
//						byte[] bytes = MessagePackUtils.getBytes(user);
//						jedis.hset(RedisConstants.userKey, email.getBytes(), bytes);
//						log.info("{} has success to active account",email);
//						userReturn.setIsActive((byte)1);
//						jedis.hdel(RedisConstants.activityCodeKey, token);
//						System.err.println("2222222222222222222");
//					}
//					System.err.println("dddddddddddddddddddddddddddddddd");
				}
			}
			return new ModelAndView("doActivityBack","userInfo",userReturn);
		} catch (Exception e) {
			log.error("doActivity err",e);
			return new ModelAndView("doActivityBack","userInfo",userReturn);
		}
	}
	
	
	
	/**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/headerPhoUpload")
    public @ResponseBody String headerPhoUpload(@RequestParam MultipartFile[] editorFile, HttpServletRequest request) throws IOException{
        //可以在上传文件的同时接收其它参数
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
                originalFilename =System.currentTimeMillis()+ myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));;
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
//        return request.getContextPath() + "/upload/" + originalFilename;
        return "cacheUpload/" + originalFilename;
    }
}
