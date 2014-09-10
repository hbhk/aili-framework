package org.hbhk.aili.gen.server.service;

import org.hbhk.aili.gen.server.model.MakeModel;

/**
 * 实体反射处理
 */
public interface MakeModelService {

	/**
	 * 生成中间数据 makeModel
	 * 
	 * @param clazz
	 * @param authName
	 * @param lifecycle
	 * @return
	 */
	MakeModel queryByClass(Class<?> clazz);
}
