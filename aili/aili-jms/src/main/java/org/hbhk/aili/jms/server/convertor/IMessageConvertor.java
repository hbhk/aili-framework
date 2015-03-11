package org.hbhk.aili.jms.server.convertor;

import org.hbhk.aili.jms.share.ex.ConvertException;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IMessageConvertor<T> {

	/**
	 * 把字符串转换为POJO.
	 */
	T toMessage(String str, Class<?> cls) throws ConvertException;

	/**
	 * 把POJO转换为字符串.
	 */
	String fromMessage(T message) throws ConvertException;

}
