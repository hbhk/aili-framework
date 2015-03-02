package org.hbhk.aili.support.server.bean;
import org.hbhk.aili.support.server.zkclient.IDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DataListener  implements IDataListener {
	public static final Logger log = LoggerFactory
			.getLogger(DataListener.class);

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
		return "/hbhk1";
	}
}

