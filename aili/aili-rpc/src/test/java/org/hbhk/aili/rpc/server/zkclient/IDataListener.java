package org.hbhk.aili.rpc.server.zkclient;

import org.I0Itec.zkclient.IZkDataListener;

public interface IDataListener  extends IZkDataListener{

	String  getNode();
}

