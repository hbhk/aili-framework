package org.hbhk.aili.support.server.zkclient;

import org.I0Itec.zkclient.IZkDataListener;

public interface IDataListener  extends IZkDataListener{

	String  getPath();
}

