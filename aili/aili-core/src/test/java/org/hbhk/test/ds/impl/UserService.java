package org.hbhk.test.ds.impl;

import org.hbhk.test.ds.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements IUserService {

	@Override
	public void getUser() {
		System.out.println("11111111111");
	}

	@Override
	@Transactional(readOnly = true)
	public void getUser1() {
		System.out.println("222222222222");
		
	}
             
}
