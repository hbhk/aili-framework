package org.hbhk.aili.core.share.ex;
public final class DuplicateKeyException extends GeneralException {
    
    private static final long serialVersionUID = 1L;
    public static final String ERROR_CODE = "errror.common.duplicatekey";
    
    public DuplicateKeyException(Throwable t) {
        super(t);
        this.errCode = ERROR_CODE;
    }
    
}
