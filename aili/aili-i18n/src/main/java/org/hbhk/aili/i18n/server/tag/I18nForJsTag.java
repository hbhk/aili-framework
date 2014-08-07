package org.hbhk.aili.i18n.server.tag;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.i18n.server.msg.MessageBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class I18nForJsTag extends SimpleTagSupport {

	private MessageBundle messageBundle;
	private String subModule;
	private String groups;
	private String contextPath;

	@Override
	public void setJspContext(JspContext pc) {
		super.setJspContext(pc);
		PageContext pageContext = (PageContext) pc;
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletContext());
		messageBundle = (MessageBundle) ctx.getBean("messageBundle");
		contextPath = pageContext.getServletContext().getContextPath();
	}

	/**
	 * SimpleTagSupport标签执行，调用的主方法
	 */
	@Override
	public void doTag() throws JspException, IOException {
		String moduleName = RequestContext.getCurrentContext().getModuleName();
		String[] groupArray = parseStringToArray(groups);
		Map<String, String> tags ;
		if(groupArray.length==0){
			tags = TagsCache.getInstance().getTagesInfo(moduleName);
		}else{
			tags = TagsCache.getInstance().getTagesInfo(moduleName, groupArray);
		}
		String keys = tags.get("keys");
		getJspContext().getOut().write(createModuleScript(moduleName));
		getJspContext().getOut().write(createI18nScript(moduleName, keys));
		if (groupArray.length != 0) {
			for (String group : groupArray) {
				getJspContext().getOut().write(
						createWroResourceUrl(moduleName, group));
			}
		}
	}

	/**
	 * 生成模块javascript代码格式
	 */
	private String createModuleScript(String moduleName) {
		String appcontext = ((PageContext) this.getJspContext())
				.getServletContext().getContextPath();
		StringBuilder msgObject = new StringBuilder("");
		msgObject.append("<script type='text/javascript'> \n");
		msgObject.append("if(typeof ").append(moduleName)
				.append(" == 'undefined'){\n").append(moduleName)
				.append("={};\n").append("}");
		msgObject.append("\n").append(moduleName)
				.append(".realPath = function (path) { \n");
		msgObject.append("return '").append(appcontext).append("/");
		msgObject.append(moduleName).append("/' + ").append("path;\n");
		msgObject.append("};\n");
		if (subModule != null) {
			msgObject.append("\n").append(moduleName).append(".")
					.append(subModule).append("={}");
		}
		msgObject.append("\n</script>\n");

		return msgObject.toString();
	}

	/**
	 * 生成国际化javascript代码格式
	 */
	private String createI18nScript(String moduleName, String keys) {
		if (StringUtils.isBlank(keys)) {
			return keys;
		}
		StringBuilder msgObject = new StringBuilder("");
		msgObject.append("<script type='text/javascript'> \n");
		// 声明一个function，用于取message
		msgObject.append(moduleName);
		if (subModule != null) {
			msgObject.append(".");
			msgObject.append(subModule);
		}
		msgObject.append(".i18n = function(key, args) { \n");
		// 声明一个对象，存放message信息
		msgObject.append("msg = {");
		String[] keyArray = parseStringToArray(keys);
		for (String key : keyArray) {
			String message = messageBundle.getMessage(key);
			if (message != null && !"".equals(message)) {
				msgObject.append("'" + key + "'" + ":'" + message + "',");
			}
		}
		// 如果keys为空,下面这句截取字符串会把方法参数中的逗号去掉
		msgObject.deleteCharAt(msgObject.lastIndexOf(","));
		msgObject.append("};");

		msgObject.append("\n");
		msgObject.append("var message = msg[ key] ; \n");
		msgObject.append("if(args != null){  \n");
		msgObject.append("for ( var i = 0; i < args.length; i++) { \n");
		msgObject.append("var reg ='{'+i+'}'; \n");
		msgObject
				.append("message = message.toString().replace(reg, args[i]); \n");
		msgObject.append("} \n");
		msgObject.append("} \n");
		msgObject.append("return message; \n");
		msgObject.append("} \n");
		msgObject.append("</script>");
		return msgObject.toString();
	}

	/**
	 * 根据模块名称和资源名称查找文件
	 */
	private String createWroResourceUrl(String moduleName, String resource)
			throws IOException {
		StringBuilder wroResPropObject = new StringBuilder("");
		Properties properties = WroResourcePropCache.getInstance()
				.getWroResourceInfo(moduleName);
		if (properties == null) {
			return wroResPropObject.toString();
		}
		String resJs = properties.getProperty(resource + ".js");
		String resCss = properties.getProperty(resource + ".css");
		if (resCss != null && !resCss.endsWith("-0.css")) {
			wroResPropObject
					.append("<link rel='stylesheet' type='text/css' href='");
			wroResPropObject.append(contextPath + "/styles/" + moduleName);
			wroResPropObject.append("/wro/");
			wroResPropObject.append(resCss);
			wroResPropObject.append("'/> \n");
		}
		if (resJs != null && !resJs.endsWith("-0.js")) {
			wroResPropObject.append("<script type='text/javascript' src='");
			wroResPropObject.append(contextPath + "/scripts/" + moduleName);
			wroResPropObject.append("/wro/");
			wroResPropObject.append(resJs);
			wroResPropObject.append("'></script> \n");
		}
		return wroResPropObject.toString();
	}

	/**
	 * 
	 * <p>
	 * 将字符串转成字符数组
	 * </p>
	 */
	private String[] parseStringToArray(String str) {
		if (str == null) {
			return new String[0];
		}
		str = str.replaceAll("\n", "");
		str = str.replaceAll("\r", "");
		String[] keyArray = str.split(",");
		for (int i = 0; i < keyArray.length; i++) {
			keyArray[i] = keyArray[i].trim();
		}
		return keyArray;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

}
