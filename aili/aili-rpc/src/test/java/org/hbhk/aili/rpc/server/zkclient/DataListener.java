package org.hbhk.aili.rpc.server.zkclient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DataListener  implements IDataListener {
	public static final Logger log = LoggerFactory
			.getLogger(DataListener.class);

	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		
	}

	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		
	}

	@Override
	public String getNode() {
		return "/hbhk1";
	}
}

