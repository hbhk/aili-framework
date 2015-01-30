package org.hbhk.aili.jms.server.process;

public interface IProcess {

	/**
	 * 业务逻辑处理.
	 */
	Object process(Object req);

}
