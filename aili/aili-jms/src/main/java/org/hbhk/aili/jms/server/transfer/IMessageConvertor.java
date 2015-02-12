package org.hbhk.aili.jms.server.transfer;

import org.hbhk.aili.jms.share.ex.ConvertException;

public interface IMessageConvertor<T> {

	/**
	 * 把字符串转换为POJO.
	 */
	T toMessage(String string, Class<?> cls) throws ConvertException;

	/**
	 * 把POJO转换为字符串.
	 */
	String fromMessage(T message) throws ConvertException;

}
