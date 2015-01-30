package org.hbhk.aili.jms.server.transfer;

import java.io.UnsupportedEncodingException;

import org.hbhk.aili.jms.share.ex.ConvertException;

public interface IMessageTransform<T> {
	
	
	/**
	 * 把字符串转换为POJO.
	 */
	T toMessage(String string) throws ConvertException, UnsupportedEncodingException;

	
	/**
	 * 把POJO转换为字符串.
	 */
	String fromMessage(T message) throws ConvertException;
	
}
