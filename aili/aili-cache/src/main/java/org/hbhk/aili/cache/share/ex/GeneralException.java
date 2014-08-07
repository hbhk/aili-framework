package org.hbhk.aili.cache.share.ex;

public abstract class GeneralException extends RuntimeException implements IException {
    
    private static final long serialVersionUID = 5374060474539004523L;
    protected String errCode;
    private String nativeMsg;
    private Object[] arguments;
    

    public GeneralException() {
        super();
    }
    
    public GeneralException(String msg) {
        super(msg);
    }
    
    public GeneralException(Throwable cause) {
        super(cause);
    }
    
    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GeneralException(Throwable cause,String nativeMsg) {
        super(cause);
        this.nativeMsg = nativeMsg;
    }
    
    public GeneralException(String msg,String nativeMsg) {
        super(msg);
        this.nativeMsg = nativeMsg;
    }
    
    public GeneralException(String message, Throwable cause,String nativeMsg) {
        super(message, cause);
        this.nativeMsg = nativeMsg;
    }
    
    public GeneralException(String errCode, String message, Throwable cause, Object...arguments) {
    	super(message, cause);
    	this.errCode = errCode;
    	this.arguments = arguments;
    }
    
    public GeneralException(String errCode, String message, Throwable cause,String nativeMsg, Object...arguments) {
    	super(message, cause);
    	this.errCode = errCode;
    	this.arguments = arguments;
    	this.nativeMsg = nativeMsg;
    }
    
    public String getErrorCode() {
        return errCode;
    }
    
    @Override
    public void setErrorArguments(Object... args) {
        this.arguments = args;
    }
    
    @Override
    public String getNativeMessage() {
        return this.nativeMsg;
    }
    
    @Override
    public Object[] getErrorArguments() {
        return this.arguments;
    }
    
}
