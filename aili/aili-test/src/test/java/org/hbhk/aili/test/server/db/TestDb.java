package org.hbhk.aili.test.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDb {

	public static void main(String[] args) {
		
		//连接MySql数据库，用户名和密码都是root   
	     String url = "jdbc:mysql://10.0.16.16:4066/hbhk888918_mysql_evnekmpo" ;    
	     String username = "Grg3Qt7l" ;   
	     String password = "UuFmiitk8ZNR" ;   
	     try{   
	    Connection con =    
	             DriverManager.getConnection(url , username , password ) ;   
	     }catch(SQLException se){   
	    System.out.println("数据库连接失败！");   
	    se.printStackTrace() ;   
	     }   
	}
}
