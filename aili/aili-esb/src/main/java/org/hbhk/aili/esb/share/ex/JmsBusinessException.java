package org.hbhk.aili.esb.share.ex;


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
