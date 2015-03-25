package org.hbhk.aili.security.server.dao;

import org.hbhk.aili.mybatis.server.dao.IBaseDao;
import org.hbhk.aili.security.share.model.UserInfo;
import org.hbhk.spring.security.share.vo.UserDetailsVo;

public interface IUserDao  extends IBaseDao<UserInfo, String> {
	
	UserDetailsVo findUserDetailsVo(String username);

}