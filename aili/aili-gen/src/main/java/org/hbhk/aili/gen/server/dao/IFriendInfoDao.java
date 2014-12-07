package org.hbhk.aili.gen.server.dao;

import java.util.List;
import java.util.Map;
import org.hbhk.aili.orm.server.annotation.NativeQuery;
import org.hbhk.aili.orm.server.annotation.NativeUpdate;
import org.hbhk.aili.orm.server.annotation.QueryParam;
import org.hbhk.aili.orm.server.dao.GenericEntityDao;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.gen.server.test.FriendInfo;

/**
 * FriendInfoDao
 * @author  何波
 *
 */
public interface IFriendInfoDao extends GenericEntityDao<FriendInfo,String>{

	/**
	 * 获取所有FriendInfo列表
	 * @return
	 */
	@NativeQuery(model = FriendInfo.class)
	List<FriendInfo> findAllFriendInfoList();
	
	/**
	 * 通过ids获取FriendInfo列表
	 * @param ids
	 * @return
	 */
	@NativeQuery(model = FriendInfo.class)
	List<FriendInfo> findFriendInfoListByIds(@QueryParam("ids")List<Long> ids);
	
	/**
	 * 通过参数map获取FriendInfo列表
	 * @param paraMap
	 * @return
	 */
	@NativeQuery(model = FriendInfo.class)
	List<FriendInfo> findFriendInfoListByQueryMap(@QueryParam Map<String, Object> paraMap);
	
	/**
	 * 分页获取FriendInfo列表
	 * @param start
	 * @param size
	 * @param paraMap
	 * @param sorts 
	 * @return
	 */
	@NativeQuery(model = FriendInfo.class)
	Pagination<FriendInfo> findFriendInfoListByQueryMapWithPage(Page page,Sort[] sorts,@QueryParam Map<String, Object> paraMap);
	
	
	
	/**
	 * 通过ids批量启用或禁用FriendInfo
	 * 设置status =0 或 1
	 * @param ids
	 * @return
	 */
	@NativeUpdate
	int updateStatusFriendInfoByIds(@QueryParam("ids")List<Long> ids,@QueryParam("state")Integer status);
	
	
}
