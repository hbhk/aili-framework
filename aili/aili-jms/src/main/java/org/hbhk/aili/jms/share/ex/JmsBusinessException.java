package org.hbhk.aili.jms.share.ex;


/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class JmsBusinessException extends BusinessException {

	private static final long serialVersionUID = 691797036656592039L;

	public JmsBusinessException(String errCode) {
		super();
		super.errCode = errCode;
	}
	
	public JmsBusinessException(String errCode, Throwable cause){
		super(errCode, cause);
		super.errCode = errCode;
	}
}
