package org.hbhk.aili.rpc.server.dubbo.callback;
public interface ICallbackService {
	void addListener(String key, ICallbackListener listener);
}

