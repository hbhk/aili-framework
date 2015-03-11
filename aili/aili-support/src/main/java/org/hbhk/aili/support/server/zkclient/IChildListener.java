package org.hbhk.aili.support.server.zkclient;

import org.I0Itec.zkclient.IZkChildListener;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IChildListener  extends IZkChildListener{

	String  getPath();
	
}

