package org.hbhk.aili.support.server;

import java.io.IOException;

import javax.mail.MessagingException;

import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.hbhk.aili.support.server.email.IEmailService;
import org.hbhk.aili.support.server.email.impl.EmailInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MailTest {

	public static void main(String[] args) throws MessagingException, IOException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");
		WebApplicationContextHolder.setContext(ctx);
		
		IEmailService sender = (IEmailService) ctx.getBean("emailService");
		EmailInfo emailInfo = new EmailInfo();
		emailInfo.setSubject("hbhk");
		emailInfo.setContext("hbhk");
		emailInfo.setEmail("1024784402@qq.com");
		sender.sendEmail(emailInfo);

	}

}
