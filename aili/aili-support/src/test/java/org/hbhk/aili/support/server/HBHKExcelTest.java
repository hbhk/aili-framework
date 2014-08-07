package org.hbhk.aili.support.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hbhk.aili.support.server.excel.ExeclModelConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HBHKExcelTest {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-excel.xml");
		ExeclModelConvertor convertor = new ExeclModelConvertor();
		File file = new File(
				"D:/baocun-ws/aili/aili-support/src/test/resources/1.xls");
		List<DeptModel> deptModels = convertor.getModel(file, "deptModel");
		List<Object> models = new ArrayList<Object>();
		for (DeptModel deptModel : deptModels) {
			models.add(deptModel);
			System.out.println(deptModel.getDeptName());
		}

		File file2 = new File(
				"D:/baocun-ws/aili/aili-support/src/test/resources/2.xls");
		if (file2.exists()) {
			file2.delete();
		}
		File input = convertor.model2ExcelToFile("deptModel", models);
		try {
			FileUtils.copyFile(input, file2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
