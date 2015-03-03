package org.hbhk.aili.support.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hbhk.aili.support.server.excel.jxl.model.Models;

public class ExcelTest {

	public static void main(String[] args) {
		File file = new File(
				"D:/baocun-ws/aili/aili-support/src/test/resources/ExcelModeMappingl.xml");
		try {
			String ss = FileUtils.readFileToString(file);
			OrmConvertor convertor = new OrmConvertor();
			Models m = convertor.toMessage(ss);
			System.out.println(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
