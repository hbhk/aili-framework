package org.hbhk.aili.mybatis.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class AddJavaClassComments {

	private static void listNext(File dir) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				listNext(files[i]);
			} else {
				try {
					if (files[i].getName().endsWith(".java")
							|| files[i].getName().endsWith(".jsp")
							|| files[i].getName().endsWith(".js")
							|| files[i].getName().endsWith(".xml")) {
						BufferedReader br = new BufferedReader(new FileReader(
								files[i]));
						String strLine = null;
						boolean flag = false;
						StringBuffer one = new StringBuffer();
						while ((strLine = br.readLine()) != null) {
							String str = strLine.trim();
							strLine = strLine + "\r\n";
							if (str.startsWith("package") || flag) {
								if(str.startsWith("/*")){
									flag = false;
									one.append(strLine);
								}else{
									flag = true;
									if (str.startsWith("public")||str.startsWith("@")) {
										flag = false;
										// 添加注释
										File cf = new File("D:/baocun-ws/aili/aili-mybatis/src/test/resources/comments.txt");
										List<String>  comments=  FileUtils.readLines(cf);
										for (String string : comments) {
											one.append(string+"\r\n");
										}
										one.append(strLine);
									} else {
										one.append(strLine);
									}
								}
							
							} else {
								one.append(strLine);
							}
						}
						System.out.println(one);
						FileUtils.writeStringToFile(files[i], one.toString(),
								false);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		String PROJECT_DIR = "D:/baocun-ws/aili/aili-support/src/main/java";
		File root = new File(PROJECT_DIR);
		listNext(root);
	}

}
