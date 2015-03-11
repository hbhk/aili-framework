package org.hbhk.aili.jms.server.process;
/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface ICallBackProcess {

	/**
	 * 回调方法,请尽量把所有异常都抛出来.
	 */
	void callback(Object response);

	/**
	 * 当服务端处理业务逻辑时，出现了异常。 而在返回对象里没有定义异常信息时，最终返回一个异常对象。 默认是.
	 */
	void errorHandler(Object errorResponse);

}
