package org.hbhk.aili.core.share.ex;


public final class GeneralServiceException extends GeneralException {
    
    private static final long serialVersionUID = 7317828017595658980L;
    
    public GeneralServiceException(String msg, String errorCode, Object... args) {
        super(msg);
        this.errCode = errorCode;
        this.setErrorArguments(args);
    }
    
}
