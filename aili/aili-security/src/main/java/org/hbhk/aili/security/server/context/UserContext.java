package org.hbhk.aili.security.server.context;

import org.hbhk.aili.security.share.pojo.UserInfo;



public class UserContext {

    private static ThreadLocal<UserContext> context = new ThreadLocal<UserContext>(){
        @Override
        protected UserContext initialValue(){
            return new UserContext();
        }
    };
    private  String currentUserName ;
    
    private UserInfo currentUser;
    
    public static void setCurrentUserName(String currentUserName) {
    	UserContext userContext = getCurrentContext();
    	userContext.currentUserName = currentUserName;
	}
    
	public UserInfo getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(UserInfo currentUser) {
		UserContext userContext = getCurrentContext();
		userContext.currentUser = currentUser;
	}

	public static UserContext getCurrentContext() {
		return context.get();
	}
	public static void remove(){	
	    context.remove();
	}
	public String getCurrentUserName() {
		return currentUserName;
	}
	
	

}
