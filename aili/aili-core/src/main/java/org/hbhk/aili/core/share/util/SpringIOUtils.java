package org.hbhk.aili.core.share.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

public class SpringIOUtils {
	private static Log log = LogFactory.getLog(SpringIOUtils.class);

	private SpringIOUtils() {
	}

	public static boolean saveFile(InputStream is, String path, String name)
			throws IOException {
		Resource resource = FileLoadUtil.getResourceForServletpath(path);
		if (resource == null) {
			log.error("file info:" + path + "," + name);
			throw new IOException("path not find");
		}
		log.debug("file info:" + path + "," + name);
		File f = resource.getFile();
		String uploadFile = f.getAbsolutePath();
		FileUtils.copyInputStreamToFile(is, new File(uploadFile, name));

		return false;

	}

	public static boolean saveFile(InputStream is, String path,
			String childPath, String name) throws IOException {
		String sep = System.getProperty("file.separator");
		Resource resource = FileLoadUtil.getResourceForServletpath(path);
		if (resource == null) {
			log.error("file info:" + path + "," + childPath + "," + name);
			throw new IOException("path not find");
		}
		log.debug("file info:" + path + "," + childPath + "," + name);
		File file = resource.getFile();
		String uploadFile = file.getAbsolutePath() + sep + childPath;
		File f = new File(uploadFile);
		if (!f.exists()) {
			f.mkdir();
		}
		FileUtils.copyInputStreamToFile(is, new File(uploadFile, name));

		return false;

	}

}
