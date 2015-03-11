package org.hbhk.aili.core.share.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class FileAsStringUtil {

	private Log log = LogFactory.getLog(getClass());

	public List<String> scanBeansXml(String path, String suffix)
			throws IOException {
		Resource[] resources = FileLoadUtil
				.getResourcesForClasspathByPath(path);
		List<String> beansXml = new ArrayList<String>();
		for (Resource res : resources) {
			if (!res.isReadable()) {
				continue;
			}
			if (!res.getFile().getName().endsWith(suffix)) {
				continue;
			}
			InputStreamReader inputStreamReader = new InputStreamReader(
					res.getInputStream());
			char[] data = new char[10240];
			inputStreamReader.read(data);
			beansXml.add(new String(data).trim());
		}
		log.debug(beansXml);
		return beansXml;
	}

	public String readJarContext(InputStream is) throws IOException {
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder context = new StringBuilder();
		try {
			isr = new InputStreamReader(is, "utf8");
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				context.append(line);
			}
		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (is != null) {
				is.close();
			}
		}
		log.debug(context.toString());
		return context.toString();

	}

}
