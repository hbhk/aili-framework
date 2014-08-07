package org.hbhk.aili.esb.share.ex;
public interface IException {
    
    String getErrorCode();
    
    String getNativeMessage();
    
    void setErrorArguments(Object... objects);
    
    Object[] getErrorArguments();
    
}
