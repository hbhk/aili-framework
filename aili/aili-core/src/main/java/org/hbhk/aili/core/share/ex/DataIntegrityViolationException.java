package org.hbhk.aili.core.share.ex;

public final class DataIntegrityViolationException extends GeneralException {
    
    private static final long serialVersionUID = 6591089800166168075L;
    
    public static final String ERROR_CODE = "errror.common.DataIntegrityViolationException";
    
    public DataIntegrityViolationException(Throwable t) {
        super(t);
        this.errCode = ERROR_CODE;
    }
    
}
