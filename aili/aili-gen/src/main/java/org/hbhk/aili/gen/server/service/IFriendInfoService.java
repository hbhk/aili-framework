package org.hbhk.aili.gen.server.service;

import java.util.List;

import org.hbhk.aili.gen.server.test.FriendInfo;
import org.hbhk.aili.orm.server.service.ICommonService;
/**
 * FriendInfoService
 * @author  何波
 *
 */
public interface IFriendInfoService extends ICommonService<FriendInfo>  {

	
	/**
	 * 通过id获取FriendInfo
	 * 
	 */
	FriendInfo findFriendInfoById(Long id);

	/**
	 * 获取所有FriendInfo列表
	 * @return
	 */
	List<FriendInfo> findAllFriendInfoList();
	
	/**
	 * 通过ids获取FriendInfo列表
	 * @param ids
	 * @return
	 */
	List<FriendInfo> findFriendInfoListByIds(List<Long> ids);
	
	/**
	 * 通过ids批量启用或禁用FriendInfo
	 * 设置status =0 或 1
	 * @param ids
	 * @return
	 */
	int updateStatusByIds(List<Long> ids,Integer status);
	
	
}
