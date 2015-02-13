package org.hbhk.aili.rpc.server.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmiServer {
	public static final Logger log = LoggerFactory.getLogger(RmiServer.class);

	public static void main(String[] args) {
		try {
			IPersonService personService = new PersonService();
			// 注册通讯端口
			LocateRegistry.createRegistry(6600);
			// 注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6600/personService", personService);
			System.out.println("Service Start!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
