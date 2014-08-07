package org.hbhk.aili.i18n.server.msg;

import java.util.Locale;

public interface IMessageBundle {
	/**
	 * 根据键取得国际化资源，并格式化
	 * getMessage
	 * @param key 
	 * @param args 格式化参数 
	 * @return
	 * @return String
	 * @since:0.6
	 */
	String getMessage(String key, Object... args);
	
	/**
	 * 取得指定地区的国际化资源
	 * getMessage
	 * @param locale
	 * @param key
	 * @param args 格式化参数
	 * @return
	 * @return String
	 * @since:0.6
	 */
	String getMessage(Locale locale, String key, Object... args);

}
