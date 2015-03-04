package org.hbhk.aili.support.server.email;
public interface IEmailCallBack {

	void success(String[] address);
	
	void fail(String[] address);
}

