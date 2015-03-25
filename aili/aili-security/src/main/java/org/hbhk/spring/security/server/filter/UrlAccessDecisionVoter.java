package org.hbhk.spring.security.server.filter;

import java.util.Collection;

import org.hbhk.spring.security.share.vo.UserDetailsVo;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class UrlAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
        String requestUrl = fi.getRequestUrl();
        
        // 不受管控的URL，直接跳过验证
        if (!isControlledUrl(requestUrl)) {
            return ACCESS_GRANTED;
        }   
        
        UserDetailsVo userDetails = (UserDetailsVo) authentication.getPrincipal();
        if (!userDetails.getPrivilegeUrls().contains(requestUrl)) {
            return ACCESS_DENIED;
        }
        
        return ACCESS_GRANTED;
    }
    
    private boolean isControlledUrl(String requestUrl) {
        return true;
    }

}
