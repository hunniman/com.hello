package com.hello.utils.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 发送邮件的方法，直接调用EmailUtil.send(subject, content, to)，即可发送邮件
 * @author gaoyihang
 *
 */
public class EmailUtil {
	
	private static Executor executor =null;
	
	private EmailUtil() {
		// Utility classes should not have a public constructor
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	/**
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 */
	public static void send(String subject, String content, String to){
		if(executor==null){
			executor= Executors.newFixedThreadPool(2);
		}
		ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("spring-smtp-mail.xml");
    	Email mm = (Email) context.getBean("simpleMail");
    	MailTask mailTask=new MailTask(subject, content, to, mm);
        LOGGER.info("Params : createTime = ["+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) +"] ,  subject = [" + subject + "] ,  content = [" + content + "] ,  to = [" + to + "]");
		executor.execute(mailTask);
		context.close();
    }

	private static class MailTask implements Runnable{

		private String subject;
		
		private String content;
		
		private String to;
		
		private Email mm ;
		
		public MailTask(String subject, String content, String to,Email mm){
			this.subject=subject;
			this.content=content;
			this.to=to;
			this.mm=mm;
		}
		
		@Override
		public void run() {
			try {
				mm.sendMail(subject, content, to);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
