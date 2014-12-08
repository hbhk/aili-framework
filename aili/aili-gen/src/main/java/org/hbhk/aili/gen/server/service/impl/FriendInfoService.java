package org.hbhk.aili.gen.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.gen.server.dao.IFriendInfoDao;
import org.hbhk.aili.gen.server.service.IFriendInfoService;
import org.hbhk.aili.gen.server.test.FriendInfo;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * FriendInfoManager
 * @author  何波
 *
 */
@Transactional
@Service 
public class FriendInfoService implements IFriendInfoService {

	private  Logger log = LoggerFactory.getLogger(FriendInfoService.class);
	@Autowired
	private IFriendInfoDao friendInfoDao;


	/**
	 * 保存FriendInfo
	 */
	public FriendInfo save(FriendInfo model){
		model.setCreateTime(new Date());
		return friendInfoDao.save(model);
	}
	
	/**
	 *修改FriendInfo
	 */
	public FriendInfo update(FriendInfo model){
		model.setUpdateTime(new Date());
		return friendInfoDao.update(model);
	}
	
	
	public FriendInfo getOne(FriendInfo model){
		return friendInfoDao.getOne(model);
	}
	
	public List<FriendInfo> get(FriendInfo model){
		return friendInfoDao.get(model);
	}
	
	public List<FriendInfo> get(FriendInfo model,Page page){
		return friendInfoDao.get(model,page);
	}
	/**
	 * 通过id获取FriendInfo
	 * 
	 */
	public FriendInfo findFriendInfoById(Long id){
	
		return null;
	}

	/**
	 * 获取所有FriendInfo列表
	 * @return
	 */
	public List<FriendInfo> findAllFriendInfoList(){
	
		return friendInfoDao.findAllFriendInfoList();
	}
	
	/**
	 * 通过ids获取FriendInfo列表
	 * @param ids
	 * @return
	 */
	public List<FriendInfo> findFriendInfoListByIds(List<Long> ids){
	
		return friendInfoDao.findFriendInfoListByIds(ids);
	}
	
	/**
	 * 通过参数map获取FriendInfo列表
	 * @param paraMap
	 * @return
	 */
	public List<FriendInfo> getList(Map<String, Object> paraMap){
	
		return friendInfoDao.findFriendInfoListByQueryMap(paraMap);
	}
	
	/**
	 * 分页获取FriendInfo列表
	 * @param start
	 * @param size
	 * @param paraMap
	 * @param sorts 
	 * @return
	 */
	public Pagination<FriendInfo> getPagination(Page page,Sort[] sorts,Map<String, Object> paraMap){
	
		return friendInfoDao.findFriendInfoListByQueryMapWithPage(page,sorts,paraMap);
	}
	
	
	
	/**
	 * 通过ids批量启用或禁用FriendInfo
	 * 设置status =0 或 1
	 * @param ids
	 * @return
	 */
	public int updateStatusByIds(List<Long> ids,Integer state){
		return state;
	}
	
	
}
