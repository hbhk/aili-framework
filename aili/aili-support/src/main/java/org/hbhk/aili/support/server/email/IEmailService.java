package org.hbhk.aili.support.server.email;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.hbhk.aili.support.server.email.impl.EmailInfo;

public interface IEmailService {

	/**
	 * 发送单个html格式邮件
	 */
	void sendEmail(EmailInfo email) throws MessagingException, IOException;

	/**
	 * 批量发送html格式邮件
	 */
	void sendBatchEmail(List<EmailInfo> emails) throws MessagingException,
			IOException;
}
