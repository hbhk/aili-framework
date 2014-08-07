package org.hbhk.aili.security.server.service;

import java.util.List;

import org.hbhk.aili.security.share.pojo.ResourceInfo;

public interface IResourceService {

	public ResourceInfo getResByCode(String code);

	public List<ResourceInfo> getResByPaCode(String pcode);
 
}
