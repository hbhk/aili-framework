package org.hbhk.test.ds.impl;

import org.hbhk.test.ds.IUserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService1 implements IUserService1 {

	@Autowired
	private IUserService1 userService1;
	
	@Override
	public void getUser() {
		System.out.println("11111111111");
		userService1.getUser1();
	}

	@Override
	@Transactional(readOnly = true)
	public void getUser1() {
		System.out.println("222222222222");
		
	}
             
}
