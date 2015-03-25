package org.hbhk.aili.security.server.dao;

import java.util.List;

import org.hbhk.aili.mybatis.server.dao.IBaseDao;
import org.hbhk.aili.security.share.model.ResourceInfo;

public interface IResourceDao extends IBaseDao<ResourceInfo,String>{

	public List<ResourceInfo> getResByPaCode(String pcode);

}
