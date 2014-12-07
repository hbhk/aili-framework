package org.hbhk.aili.gen.server.service;

import org.hbhk.aili.gen.server.model.MakeModel;

/**
 * 生成代码
 */
public interface MakeCodeService {

	void makeDao(MakeModel makeModel, String generateOutDir);

	void makeManager(MakeModel makeModel, String generateOutDir);

	void makeSqlXml(MakeModel makeModel, String generateOutDir);

	void makeController(MakeModel makeModel, String generateOutDir);
}
