package org.hbhk.aili.esb.share.pojo;

import java.util.ArrayList;
import java.util.List;

public class StatusList {
	
	/** The status info list. */
	private List<StatusInfo> statusInfoList = new ArrayList<StatusInfo>();

	/**
	 * Get the list of 'statusInfo' element items. 状态列表
	 * 
	 * @return list
	 */
	public List<StatusInfo> getStatusInfoList() {
		return statusInfoList;
	}

	/**
	 * Set the list of 'statusInfo' element items. 状态列表
	 * 
	 * @param list
	 *            the new status info list
	 */
	public void setStatusInfoList(List<StatusInfo> list) {
		statusInfoList = list;
	}
}
