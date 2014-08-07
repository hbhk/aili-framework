package org.hbhk.aili.support.server.email.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.ResourceNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.support.server.email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements IEmailService {

	private Log log = LogFactory.getLog(getClass());
	@Autowired
	protected JavaMailSender mailSender;
	@Value("${toFromEmail}")
	private String toFromEmail;

	/**
	 * 发送带模板的单个html格式邮件
	 */
	// public void sendMessage(String content, String address)
	// throws MessagingException {
	// MimeMessage msg = sender.createMimeMessage();
	// // 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
	// MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
	// helper.setTo(address); // 邮件接收地址
	// helper.setFrom(from); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
	// helper.setSubject(subject); // 主题
	// String htmlText = getMailText(content); // 使用模板生成html邮件内容
	// helper.setText(htmlText, true); // 邮件内容，注意加参数true，表示启用html格式
	// sender.send(msg); // 发送邮件
	// }

	/**
	 * 批量发送带附件的html格式邮件
	 */
	// public void sendBatchEmail(String content, List<String> address)
	// throws MessagingException {
	// MimeMessage msg = sender.createMimeMessage();
	// MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
	// String toList = getMailList(address);
	// InternetAddress[] iaToList = new InternetAddress().parse(toList);
	// msg.setRecipients(Message.RecipientType.TO, iaToList);
	// helper.setFrom(from);
	// helper.setSubject(subject);
	// helper.setText(content, true);
	// // 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
	// messageHelper.addInline("a", new File("E:/logo_a.jpg")); // 附件内容
	// messageHelper.addInline("b", new File("E:/logo_b.png"));
	// File file = new File("E:/测试中文文件.rar");
	// // 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
	// messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()),
	// file);
	// // 如果主题出现乱码，可以使用该函数，也可以使用下面的函数
	// // helper.setSubject(MimeUtility.encodeText(String text,String
	// // charset,String encoding))
	// // 第2个参数表示字符集，第三个为目标编码格式。
	// // MimeUtility.encodeWord(String word,String charset,String encoding)
	// sender.send(msg);
	// }

	/**
	 * 集合转换字符串
	 */
	public String getMailList(List<String> to) {
		StringBuffer toList = new StringBuffer();
		int length = to.size();
		if (to != null && length < 2) {
			toList.append(to.get(0));
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(to.get(i));
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}

	@Override
	public void sendEmail(EmailInfo email) throws MessagingException,
			ResourceNotFoundException, IOException {
		MimeMessage msg = mailSender.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		helper.setTo(email.getEmail()); // 邮件接收地址
		helper.setFrom(toFromEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
		helper.setSubject(email.getSubject()); // 主题
		// String htmlText = getMailText(content); // 使用模板生成html邮件内容
		helper.setText(email.getContext(), true); // 邮件内容，注意加参数true，表示启用html格式
		mailSender.send(msg); // 发送邮件
	}

	@Override
	public void sendBatchEmail(List<EmailInfo> emails)
			throws MessagingException {
		for (EmailInfo email : emails) {
			MimeMessage msg = mailSender.createMimeMessage();
			// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setTo(email.getEmail()); // 邮件接收地址
			helper.setFrom(toFromEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
			helper.setSubject(email.getSubject()); // 主题
			// String htmlText = getMailText(content); // 使用模板生成html邮件内容
			helper.setText(email.getContext(), true); // 邮件内容，注意加参数true，表示启用html格式
			mailSender.send(msg); // 发送邮件
		}
	}

	public String setContext(String msg) throws ResourceNotFoundException,
			IOException {
		JetEngine engine = JetEngine.create();
		// Resource resource =
		// FileLoadUtil.getResourceForServletpath("mailtmp.html");
		// 获取一个模板对象
		String p = "src/test/resources/mailtmp.html";
		JetTemplate template = engine.getTemplate(p);
		// 渲染模板
		StringWriter writer = new StringWriter();
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("content", msg);
		template.render(datas, writer);
		String context = writer.toString();
		log.debug("email-->> context:" + context);
		return "context";
	}

	public String setContextData(Map<String, Object> datas)
			throws ResourceNotFoundException, IOException {
		JetEngine engine = JetEngine.create();
		// Resource resource =
		// FileLoadUtil.getResourceForServletpath("mailtmp.html");
		// 获取一个模板对象
		String p = "src/test/resources/mailtmp.html";
		JetTemplate template = engine.getTemplate(p);
		// 渲染模板
		StringWriter writer = new StringWriter();
		template.render(datas, writer);
		String context = writer.toString();
		log.debug("email-->> context:" + context);
		return context;
	}

}