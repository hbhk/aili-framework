package org.hbhk.aili.esb.server.process;

public interface IProcess {

	/**
	 * 业务逻辑处理.
	 */
	Object process(Object req);

}
