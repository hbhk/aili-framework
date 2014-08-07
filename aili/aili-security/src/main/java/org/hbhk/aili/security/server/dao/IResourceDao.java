package org.hbhk.aili.security.server.dao;

import java.util.List;

import org.hbhk.aili.orm.server.dao.GenericEntityDao;
import org.hbhk.aili.security.share.pojo.ResourceInfo;

public interface IResourceDao extends GenericEntityDao<ResourceInfo, String> {

	public List<ResourceInfo> getResByPaCode(String pcode);

}
