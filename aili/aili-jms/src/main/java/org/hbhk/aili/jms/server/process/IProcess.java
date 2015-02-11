package org.hbhk.aili.jms.server.process;

public interface IProcess<T> {

	/**
	 * 业务逻辑处理.
	 */
	Object process(T req);

}
