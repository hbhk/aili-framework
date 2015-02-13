package org.hbhk.aili.rpc.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hbhk.aili.rpc.share.model.PersonEntity;

public interface IPersonService extends Remote {
	List<PersonEntity> getList() throws RemoteException;
}
