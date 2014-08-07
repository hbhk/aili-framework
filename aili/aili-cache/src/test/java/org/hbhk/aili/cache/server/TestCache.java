package org.hbhk.aili.cache.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCache {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:cache/**/cacheContext.xml");
		CacheSupportTest cacheSupportTest = (CacheSupportTest) context
				.getBean("cacheSupportTest");
		cacheSupportTest.setExpire(10);
		RoleInfo sss = cacheSupportTest.get("hebo");
		// String sss = (String) CacheManager.getInstance().getCache("hbhk")
		// .get("hebo");
		// CacheManager.getInstance().getCache("hbhk").set("hbhk1","dddd");
		System.out.println(sss);
//		Thread.sleep(5000);
//		sss = cacheSupportTest.get("hebo");
//		System.out.println(sss);
//		Thread.sleep(10000);
//		sss = cacheSupportTest.get("hebo");
//		System.out.println(sss);

	}

	public static String DownLoadFileStr(String urlStr) {
		InputStream in = null;
		BufferedReader br = null;
		String returnStr = "";
		try {
			File f = new File(urlStr);
			in = new FileInputStream(f);
			StringBuffer sb = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			returnStr = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return returnStr;

	}

}
