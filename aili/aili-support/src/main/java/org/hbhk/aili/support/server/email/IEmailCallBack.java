package org.hbhk.aili.support.server.email;
/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IEmailCallBack {

	void success(String[] address);
	
	void fail(String[] address);
}

