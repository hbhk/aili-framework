package org.hbhk.aili.core.share.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class SpringIOUtils {
	private static Log log = LogFactory.getLog(SpringIOUtils.class);

	private SpringIOUtils() {
	}

	public static boolean saveFile(InputStream is, String path,String name) throws IOException {
		log.debug("file info:" + path  + "," + name);
		String uploadFile = path;
		File f = new File(uploadFile);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileUtils.copyInputStreamToFile(is, new File(uploadFile, name));
		return true;
	}

	public static boolean saveFile(InputStream is, String path,
			String childPath, String name) throws IOException {
		String sep = System.getProperty("file.separator");
		log.debug("file info:" + path + "," + childPath + "," + name);
		String uploadFile = path + sep + childPath;
		File f = new File(uploadFile);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileUtils.copyInputStreamToFile(is, new File(uploadFile, name));

		return true;

	}

}
