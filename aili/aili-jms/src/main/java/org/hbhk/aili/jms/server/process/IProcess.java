package org.hbhk.aili.jms.server.process;

import org.hbhk.aili.jms.share.pojo.JmsHeader;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IProcess<T> {

	/**
	 * 业务逻辑处理.
	 */
	Object process(T req,JmsHeader header);

}
