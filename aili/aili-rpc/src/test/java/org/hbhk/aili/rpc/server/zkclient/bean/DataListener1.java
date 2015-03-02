package org.hbhk.aili.rpc.server.zkclient.bean;
import org.hbhk.aili.rpc.server.zkclient.IDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DataListener1  implements IDataListener {
	public static final Logger log = LoggerFactory
			.getLogger(DataListener1.class);

	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		System.out.println("path:"+dataPath);
    	System.out.println("data:"+data);
	}

	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		
	}

	@Override
	public String getPath() {
		return "/hbhk2";
	}
}

