package org.hbhk.aili.rpc.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import org.hbhk.aili.rpc.server.dubbo.TableConfig;
import org.hbhk.aili.rpc.share.model.PersonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @Description:此为远程对象的实现类，须继承UnicastRemoteObject 
* @author 何波
* @date 2015年2月13日 下午5:40:26 
*
 */
public class PersonService extends UnicastRemoteObject implements
		IPersonService {
	protected PersonService() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = -1938264316312417823L;
	public static final Logger log = LoggerFactory
			.getLogger(PersonService.class);

	@Override
	public List<PersonEntity> getList() throws RemoteException {
		System.out.println("Get Person Start!");
		List<PersonEntity> personList = new LinkedList<PersonEntity>();

		PersonEntity person1 = new PersonEntity();
		person1.setAge(25);
		person1.setId(0);
		person1.setName("Leslie");
		personList.add(person1);

		PersonEntity person2 = new PersonEntity();
		person2.setAge(25);
		person2.setId(1);
		person2.setName("Rose");
		personList.add(person2);

		return personList;
	}
	@Override
	public String deal1(TableConfig test, String data) {
		return test.getCaption()+"";
	}
}
