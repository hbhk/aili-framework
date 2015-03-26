package org.hbhk.aili.support.server.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
/**
 * @author 何波
 * @date 2015年3月4日 下午3:36:40 
 */
public interface IChildListener  extends IZkChildListener{

	String  getPath();
	
}

