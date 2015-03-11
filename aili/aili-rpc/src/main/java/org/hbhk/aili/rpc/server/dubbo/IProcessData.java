package org.hbhk.aili.rpc.server.dubbo;

/**
 * 
 * @Description: 远程调用增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IProcessData {
	String deal(String data);
	
	String deal1(TableConfig test ,String data);
}
