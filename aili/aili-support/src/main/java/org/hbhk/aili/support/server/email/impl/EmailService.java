package org.hbhk.aili.support.server.email.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

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
	private Executor executor = Executors.newFixedThreadPool(10);
	@Autowired
	protected JavaMailSender mailSender;
	@Value("${toEmail}")
	private String toEmail;

	/**
	 * 发送带模板的单个html格式邮件
	 */
	public void sendEmail(final String subject, final String content,
			final String... address) throws Exception {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
				MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(address); // 邮件接收地址
					helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
					helper.setSubject(subject); // 主题
					helper.setText(content, true); // 邮件内容，注意加参数true，表示启用html格式
					mailSender.send(msg); // 发送邮件
				} catch (Exception e) {
					log.error("sead emial error", e);
				}
			}
		});

	}

	/**
	 * 批量发送带附件的html格式邮件
	 */
	public void sendEmailWithAttachment(final String subject,
			final String content, final List<String> paths,
			final String... address) throws Exception {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(address);
					helper.setFrom(toEmail);
					helper.setSubject(subject);
					helper.setText(content, true);
					// 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
					// helper.addInline("a", new File("E:/logo_a.jpg")); // 附件内容
					for (String path : paths) {
						File file = new File(path);
						// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
						helper.addAttachment(
								MimeUtility.encodeWord(file.getName()), file);
					}
					// 如果主题出现乱码，可以使用该函数，也可以使用下面的函数
					// helper.setSubject(MimeUtility.encodeText(String
					// text,String
					// charset,String encoding))
					// 第2个参数表示字符集，第三个为目标编码格式。
					// MimeUtility.encodeWord(String word,String charset,String
					// encoding)
					mailSender.send(msg);
				} catch (Exception e) {
					log.error("sead emial error", e);
				}
			}
		});

	}

	@Override
	public void sendEmail(final EmailInfo email) throws MessagingException,
			ResourceNotFoundException, IOException {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
				MimeMessageHelper helper;
				try {
					List<String> address = email.getEmails();
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(address.toArray(new String[] {})); // 邮件接收地址
					helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
					helper.setSubject(email.getSubject()); // 主题
					// String htmlText = getMailText(content); // 使用模板生成html邮件内容
					helper.setText(email.getContext(), true); // 邮件内容，注意加参数true，表示启用html格式
					mailSender.send(msg); // 发送邮件
				} catch (Exception e) {
					log.error("sead emial error", e);
				}

			}
		});

	}

	@Override
	public void sendBatchEmail(final List<EmailInfo> emails)
			throws MessagingException {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				for (EmailInfo email : emails) {
					try {
						MimeMessage msg = mailSender.createMimeMessage();
						// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
						MimeMessageHelper helper = new MimeMessageHelper(msg,
								true, "utf-8");
						List<String> address = email.getEmails();
						helper.setTo(address.toArray(new String[] {})); // 邮件接收地址
						helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
						helper.setSubject(email.getSubject()); // 主题
						// String htmlText = getMailText(content); //
						// 使用模板生成html邮件内容
						helper.setText(email.getContext(), true); // 邮件内容，注意加参数true，表示启用html格式
						mailSender.send(msg); // 发送邮件
					} catch (Exception e) {
						log.error("sead emial error", e);
					}

				}
			}
		});

	}

	public String setContextData(String context, Map<String, Object> params)
			throws ResourceNotFoundException, IOException {
		JetEngine engine = JetEngine.create();
		// 获取一个模板对象
		JetTemplate template = engine.getTemplate(context);
		// 渲染模板
		StringWriter writer = new StringWriter();
		template.render(params, writer);
		context = writer.toString();
		log.debug("email-->> context:" + context);
		return context;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	@Override
	public void sendEmail(final EmailInfo email,
			final Map<String, Object> params) throws MessagingException,
			IOException {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
				MimeMessageHelper helper;
				try {
					List<String> emails = email.getEmails();
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(emails.toArray(new String[] {})); // 邮件接收地址
					helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
					helper.setSubject(email.getSubject()); // 主题
					String htmlText = setContextData(email.getContext(), params); // 使用模板生成html邮件内容
					helper.setText(htmlText, true); // 邮件内容，注意加参数true，表示启用html格式
					mailSender.send(msg); // 发送邮件
				} catch (Exception e) {
					log.error("sead emial error", e);
				}

			}
		});

	}

	@Override
	public void sendBatchEmail(final List<EmailInfo> emails,
			final Map<String, Object> params) throws MessagingException,
			IOException {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				for (EmailInfo email : emails) {
					try {
						MimeMessage msg = mailSender.createMimeMessage();
						// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
						MimeMessageHelper helper = new MimeMessageHelper(msg,
								true, "utf-8");
						List<String> emails = email.getEmails();
						helper.setTo(emails.toArray(new String[] {})); // 邮件接收地址
						helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
						helper.setSubject(email.getSubject()); // 主题
						String htmlText = setContextData(email.getContext(),
								params); //
						// 使用模板生成html邮件内容
						helper.setText(htmlText, true); // 邮件内容，注意加参数true，表示启用html格式
						mailSender.send(msg); // 发送邮件
					} catch (Exception e) {
						log.error("sead emial error", e);
					}
				}
			}
		});
	}

	@Override
	public void sendEmail(final String subject, final String content,
			final Map<String, Object> params, final String... address) throws Exception {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
				MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(address); // 邮件接收地址
					helper.setFrom(toEmail); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
					helper.setSubject(subject); // 主题
					String newcontent = setContextData(content, params);
					helper.setText(newcontent, true); // 邮件内容，注意加参数true，表示启用html格式
					mailSender.send(msg); // 发送邮件
				} catch (Exception e) {
					log.error("sead emial error", e);
				}
			}
		});
	}

	@Override
	public void sendEmailWithAttachment(final String subject, final String content,
			final List<String> paths, final Map<String, Object> params, final String... address)
			throws Exception {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage msg = mailSender.createMimeMessage();
				MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(msg, true, "utf-8");
					helper.setTo(address);
					helper.setFrom(toEmail);
					helper.setSubject(subject);
					String newcontent = setContextData(content, params);
					helper.setText(newcontent, true);
					// 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
					// helper.addInline("a", new File("E:/logo_a.jpg")); // 附件内容
					for (String path : paths) {
						File file = new File(path);
						// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
						helper.addAttachment(
								MimeUtility.encodeWord(file.getName()), file);
					}
					// 如果主题出现乱码，可以使用该函数，也可以使用下面的函数
					// helper.setSubject(MimeUtility.encodeText(String
					// text,String
					// charset,String encoding))
					// 第2个参数表示字符集，第三个为目标编码格式。
					// MimeUtility.encodeWord(String word,String charset,String
					// encoding)
					mailSender.send(msg);
				} catch (Exception e) {
					log.error("sead emial error", e);
				}
			}
		});
	}

}