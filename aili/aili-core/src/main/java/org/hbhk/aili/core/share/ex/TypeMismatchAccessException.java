package org.hbhk.aili.core.share.ex;

public final class TypeMismatchAccessException extends GeneralException {
    
    private static final long serialVersionUID = -3526762703628318946L;
    public static final String ERROR_CODE = "errror.common.typemissmatch";
    
    public TypeMismatchAccessException(Throwable t) {
        super(t);
        this.errCode = ERROR_CODE;
    }
    
}
