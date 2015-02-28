package org.hbhk.aili.jms.server.process;

import org.hbhk.aili.jms.share.pojo.ESBHeader;

public interface IProcess<T> {

	/**
	 * 业务逻辑处理.
	 */
	Object process(T req,ESBHeader header);

}
