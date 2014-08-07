package org.hbhk.aili.lock.share.pojo;

/**
 * 锁定业务类型
 */
public class MutexElementType {

	/**
	 * 业务锁前缀
	 */
	private String prefix;

	/**
	 * 业务锁名称
	 */
	private String name;

	public MutexElementType(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	/**
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
