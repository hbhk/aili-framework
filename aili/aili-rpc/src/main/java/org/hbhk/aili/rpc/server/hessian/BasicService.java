package org.hbhk.aili.rpc.server.hessian;

import com.caucho.hessian.server.HessianServlet;

/**
 * 
 * @Description: 远程调用增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class BasicService extends HessianServlet implements IBasic {
	private static final long serialVersionUID = 7299340854234925425L;

	public String hello(String str) {
		return "Hello, world";
	}
}
