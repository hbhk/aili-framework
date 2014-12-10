package org.hbhk.aili.gen.server.service;

import org.hbhk.aili.gen.server.Constants;
import org.hbhk.aili.gen.server.GenerateMain;
import org.hbhk.aili.gen.server.model.MakeModel;
import org.hbhk.aili.gen.server.utils.BaseFreemarkUtils;

public class MakeCodeServiceImpl implements MakeCodeService {

	private String queryTemplatePath(String fileName) {
		String path = GenerateMain.class.getClassLoader()
				.getResource("template/" + fileName).getPath();
		return path;
	}

	@Override
	public void makeDao(MakeModel makeModel, String generateOutDir) {
		String name =  makeModel.getEntityName();
		name = name.replaceAll("Info", "").trim();
		makeModel.setFname(name);
		BaseFreemarkUtils.generate(queryTemplatePath(Constants.DAO_TEMPLATE),
				makeModel, generateOutDir + Constants.DAO_FILE_START + "I"
						+ name+ "Dao.java");
	}

	@Override
	public void makeManager(MakeModel makeModel, String generateOutDir) {
		String name =  makeModel.getEntityName();
		name = name.replaceAll("Info", "").trim();
		makeModel.setFname(name);
		BaseFreemarkUtils.generate(
				queryTemplatePath(Constants.MANAGER_TEMPLATE),
				makeModel,
				generateOutDir + Constants.MANAGER_FILE_START + "I"
						+ name + "Service.java");
		BaseFreemarkUtils.generate(
				queryTemplatePath(Constants.MANAGER_IMPL_TEMPLATE),
				makeModel,
				generateOutDir + Constants.MANAGER_IMPL_FILE_START
						+name + "Service.java");
	}
	
	public void makeController(MakeModel makeModel, String generateOutDir) {
		String name =  makeModel.getEntityName();
		name = name.replaceAll("Info", "").trim();
		makeModel.setFname(name);
		BaseFreemarkUtils.generate(
				queryTemplatePath("controller_template.fl"),
				makeModel,
				generateOutDir +"controller/"
						+ name + "Controller.java");
	}

	@Override
	public void makeSqlXml(MakeModel makeModel, String generateOutDir) {
		String name =  makeModel.getEntityName();
		name = name.replaceAll("Info", "").trim();
		makeModel.setFname(name);
		BaseFreemarkUtils.generate(queryTemplatePath(Constants.XML_TEMPLATE),
				makeModel, generateOutDir + Constants.XML_FILE_START
						+ "orm" + "-"
						+ name.toLowerCase() + ".xml");

	}

}
