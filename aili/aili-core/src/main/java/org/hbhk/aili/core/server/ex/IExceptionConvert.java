package org.hbhk.aili.core.server.ex;

import org.hbhk.aili.core.share.ex.GeneralException;


public interface IExceptionConvert {
    /**
     * 转化成GeneralException
     */
    GeneralException convert(Throwable target);
    
    /**
     * 转化成Exception
     */
    Exception nativeConvert(Throwable target);
    
    /**
     * 转化成字符串
     */
    String parseExceptionMessage(Throwable target);
}
