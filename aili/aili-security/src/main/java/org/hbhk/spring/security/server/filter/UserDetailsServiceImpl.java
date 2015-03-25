package org.hbhk.spring.security.server.filter;

import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.model.UserInfo;
import org.hbhk.spring.security.share.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userService.getMe(username);
        if(userInfo==null){
        	throw new UsernameNotFoundException("can't found user " + username);
        }
        UserDetailsVo detailsVo = new UserDetailsVo();
        detailsVo.setUserId(userInfo.getId());
        detailsVo.setUsername(userInfo.getUserName());
        detailsVo.setPassword(userInfo.getPassword());
        return detailsVo;
    }
    

}
