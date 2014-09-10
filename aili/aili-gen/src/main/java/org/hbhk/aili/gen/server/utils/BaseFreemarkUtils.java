package org.hbhk.aili.gen.server.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 写入freemarker的公共生成工具
 */
public class BaseFreemarkUtils {

	private static Configuration cfg = new Configuration();

	/**
	 * 创建目录树
	 * @param path
	 */
	private static void makeDir(String path) {
		int index = path.lastIndexOf("/");
		String dir = path.substring(0, index);
		new File(dir).mkdirs();
	}

	/**
	 * 
	 * @param templatePath
	 * @param obj
	 * @param outPath
	 */
	public static void generate(String templatePath, Object obj, String outPath) {
		try {
			int index = templatePath.lastIndexOf("/");
			String templateDir = templatePath.substring(0, index);
			String templateFile = templatePath.substring(index + 1);

			cfg.setDirectoryForTemplateLoading(new File(templateDir));
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding("UTF-8");

			Template template = cfg.getTemplate(templateFile);

			File outFile = new File(outPath);
			makeDir(outPath);
			if (outFile.exists()) {
				outFile.delete();
			}

			Writer writer = new FileWriter(outFile);
			template.process(obj, writer);
			writer.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
