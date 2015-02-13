package org.hbhk.aili.test.server.rmi;
import java.rmi.Naming;
import java.util.List;

import org.hbhk.aili.rpc.server.rmi.IPersonService;
import org.hbhk.aili.rpc.share.model.PersonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class RmiClient {
	public static final Logger log = LoggerFactory.getLogger(RmiClient.class);
	
	 public static void main(String[] args){  
	        try{  
	            //调用远程对象，注意RMI路径与接口必须与服务器配置一致  
	            IPersonService personService=(IPersonService)Naming.lookup("rmi://127.0.0.1:6600/personService");  
	            List<PersonEntity> personList=personService.getList();  
	            for(PersonEntity person:personList){  
	                System.out.println("ID:"+person.getId()+" Age:"+person.getAge()+" Name:"+person.getName());  
	            }  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	    }  
}

