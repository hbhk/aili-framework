package org.hbhk.aili.support.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.support.server.email.IEmailService;
import org.hbhk.aili.support.server.email.impl.EmailInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MailTest {

	public static void main(String[] args) throws Exception {
		Resource resource = new ClassPathResource("/application.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");
		WebApplicationContextHolder.setContext(ctx);
		
		IEmailService sender = (IEmailService) ctx.getBean("emailService");
		EmailInfo emailInfo = new EmailInfo();
		emailInfo.setSubject("hbhk");
		emailInfo.setContext("hbhk");
		List<String> list = new  ArrayList<String>();
		list.add("1024784402@qq.com");
		emailInfo.setEmails(list);
		sender.sendEmail(emailInfo);

	}

}
