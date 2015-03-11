package org.hbhk.aili.rpc.server.dubbo;
/**
 * 
 * @Description: 远程调用增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ProcessData implements IProcessData {

	@Override
	public String deal(String data) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Finished:" + data;
	}

	@Override
	public String deal1(TableConfig test, String data) {
		return test.getCaption()+"";
	}
}
