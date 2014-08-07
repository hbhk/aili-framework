package org.hbhk.aili.lock.share.pojo;


/**
 * 互斥对象
 */
public class MutexElement {

	/**
	 * 锁定业务对象编号，如运单号
	 */
	private String businessNo;

	/**
	 * 锁定业务对象描述，如开单
	 */
	private String businessDesc;

	/**
	 * 锁定业务对象类型
	 */
	private MutexElementType type;

	/**
	 * 锁定时间，超时后自动解除锁定 单位：秒，默认1分钟
	 */
	private int ttl = 60;

	/**
	 * 构造函数
	 */
	public MutexElement(String businessNo, String businessDesc,
			MutexElementType type) {
		this.businessNo = businessNo;
		this.businessDesc = businessDesc;
		this.type = type;
	}

	/**
	 * @return businessNo
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * @return businessDesc
	 */
	public String getBusinessDesc() {
		return businessDesc;
	}

	/**
	 * @return type
	 */
	public MutexElementType getType() {
		return type;
	}

	/**
	 * @return ttl
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

}
