package org.hbhk.aili.i18n.server.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hbhk.aili.i18n.server.msg.MessageBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 
 * 提供前台jsp页面使用的国际化标签
 */
public class I18nForJspTag extends SimpleTagSupport{
	//国际化信息key
	private String key;
	
	private MessageBundle messageBundle;
	
	@Override
	public void setJspContext(JspContext pc) {
		super.setJspContext(pc);
		PageContext pageContext = (PageContext) pc;
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletContext());
		messageBundle = (MessageBundle) ctx.getBean("messageBundle");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(messageBundle.getMessage(key));
	}
}
