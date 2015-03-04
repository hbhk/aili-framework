package org.hbhk.aili.rpc.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hbhk.aili.rpc.server.dubbo.TableConfig;
import org.hbhk.aili.rpc.share.model.PersonEntity;
/**
 * 
* @Description: 此为远程对象调用的接口，必须继承Remote类  
* @author 何波
* @date 2015年2月13日 下午5:40:36 
*
 */
public interface IPersonService extends Remote {
	List<PersonEntity> getList() throws RemoteException;
	
	String deal1(TableConfig test ,String data) throws RemoteException;
}
