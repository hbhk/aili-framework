package org.hbhk.aili.support.server.zkclient;

import org.I0Itec.zkclient.IZkChildListener;

public interface IChildListener  extends IZkChildListener{

	String  getPath();
	
}

