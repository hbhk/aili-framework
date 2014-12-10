package org.hbhk.aili.gen.server;

import org.hbhk.aili.gen.server.model.MakeModel;
import org.hbhk.aili.gen.server.service.MakeCodeService;
import org.hbhk.aili.gen.server.service.MakeCodeServiceImpl;
import org.hbhk.aili.gen.server.service.MakeModelService;
import org.hbhk.aili.gen.server.service.MakeModelServiceImpl;
import org.hbhk.aili.gen.server.test.CarInfo;

public class GenerateMain {

	/**
	 * 生成的实体
	 */
	private final Class<?> modelClass = CarInfo.class;

	public static String projectName = "secretary";

	public static String moduleName = "backend";
	/**
	 * 作者名
	 */
	private final String authName = "何波";
	/**
	 * 生命周期字段,如果没有则为""空串
	 */
	private final String lifecycle = "status";

	/**
	 * 是否拥有删除状态,拥有删除状态则会在所有的查询后加上lifecycle!=2
	 */
	private final Boolean hasDeleteLifecycle = true;

	private static String getAutoMakeCode() {

		return System.getProperty("user.dir") + "/target/template/";
	}

	private void execute(String[] args) throws Exception {

		if (args == null || args.length == 0) {
			args = new String[4];
			args[0] = modelClass.getName();
			args[1] = authName;
			args[2] = lifecycle;
			args[3] = String.valueOf(hasDeleteLifecycle);
		}

		MakeModelService mmService = new MakeModelServiceImpl();

		Class<?> clazz = Class.forName(args[0]);

		MakeModel mm = mmService.queryByClass(clazz);

		mm.setAuthName(args[1]);
		if (args[2] == null || "null".equals(args[2])) {
			mm.setLifecycle("");
		} else {
			mm.setLifecycle(args[2]);
		}

		Boolean hasDeleteLifecycle = new Boolean(args[3]);
		mm.setHasDeleteLifecycle(hasDeleteLifecycle);

		MakeCodeService mcs = new MakeCodeServiceImpl();

		mcs.makeSqlXml(mm, getAutoMakeCode());

		mcs.makeDao(mm, getAutoMakeCode());

		mcs.makeManager(mm, getAutoMakeCode());
		mcs.makeController(mm, getAutoMakeCode());
		System.out.println(args[0] + args[1] + args[2] + args[3]);
	}

	public static void execute(Class<?> cls, String author, String lifecycle,
			boolean hasDeleteLifecycle) throws Exception {
		String[] args = new String[4];
		if (args == null || args.length == 0) {
			args = new String[4];
			args[0] = cls.getName();
			args[1] = author;
			args[2] = lifecycle;
			args[3] = String.valueOf(hasDeleteLifecycle);
		}

		MakeModelService mmService = new MakeModelServiceImpl();

		Class<?> clazz = Class.forName(args[0]);

		MakeModel mm = mmService.queryByClass(clazz);

		mm.setAuthName(args[1]);
		if (args[2] == null || "null".equals(args[2])) {
			mm.setLifecycle("");
		} else {
			mm.setLifecycle(args[2]);
		}

		mm.setHasDeleteLifecycle(hasDeleteLifecycle);
		MakeCodeService mcs = new MakeCodeServiceImpl();

		mcs.makeSqlXml(mm, getAutoMakeCode());

		mcs.makeDao(mm, getAutoMakeCode());

		mcs.makeManager(mm, getAutoMakeCode());
		mcs.makeController(mm, getAutoMakeCode());

		System.out.println(args[0] + args[1] + args[2] + args[3]);
	}

	public static void main(String[] args) throws Exception {

		GenerateMain gm = new GenerateMain();

		gm.execute(args);

	}
}
