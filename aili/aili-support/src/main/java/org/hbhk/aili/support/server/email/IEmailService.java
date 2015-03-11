package org.hbhk.aili.support.server.email;

import java.util.List;
import java.util.Map;

import org.hbhk.aili.support.server.email.impl.EmailInfo;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IEmailService {

	void sendEmail(String subject, String content, String... address)
			throws Exception ;
	void sendEmail(String subject, String content,Map<String, Object> params,String... address)
			throws Exception ;
	void sendEmailWithAttachment(String subject, String content,List<String> paths,
			String... address) throws Exception;
	
	void sendEmailWithAttachment(String subject, String content,List<String> paths,Map<String, Object> params,
			String... address) throws Exception;
	/**
	 * 发送单个html格式邮件
	 */
	void sendEmail(EmailInfo email) throws Exception;
	void sendEmail(EmailInfo email,Map<String, Object> params) throws Exception;

	/**
	 * 批量发送html格式邮件
	 */
	void sendBatchEmail(List<EmailInfo> emails) throws Exception;
	void sendBatchEmail(List<EmailInfo> emails,Map<String, Object> params) throws Exception;
}
