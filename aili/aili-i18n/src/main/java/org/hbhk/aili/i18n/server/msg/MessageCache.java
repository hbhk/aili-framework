package org.hbhk.aili.i18n.server.msg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;
import org.hbhk.aili.cache.server.templet.impl.MemoryCacheTemplet;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class MessageCache {

	public static final String CACHEID = "messageCache";
	public static final String I18NKEYS = "i18nkeys";
	private Log logger = LogFactory.getLog(MessageCache.class);

	private ICacheTemplet<String, Map<String, Properties>> cacheSupport;
//	private ICacheTemplet<String, String> cacheSupportI18nKeys;

	public MessageCache() {
		cacheSupport = new MemoryCacheTemplet<Map<String, Properties>>();
		//cacheSupportI18nKeys = new MemoryCacheTemplet<String>();
	}
	public void doInitialization() {
		final String prefix = "org/hbhk/";
		try {
			// 加载指定位置的properties文件
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:" + prefix
					+ "**/server/META-INF/messages/message*.properties");
			//Properties properties = new Properties();
			Map<String, Properties> map = new HashMap<String, Properties>();
			for (Resource resource : resources) {
				String path = resource.getURL().getPath();
				String classpath = path.substring(path.lastIndexOf(prefix));
				if (logger.isInfoEnabled()) {
					logger.info("message bundle: " + classpath);
				}
				String subStr = "aili/";
				String moduleStr = classpath.substring(
						classpath.indexOf(subStr) + subStr.length(),
						classpath.length());
				final String moduleName = moduleStr.substring(0,
						moduleStr.indexOf("/"));
				InputStream in = resource.getInputStream();
				try {
					Properties p = new Properties();
					p.load(in);
					map.put(moduleName, p);
				//	properties.putAll(map);
				//	cacheSupportI18nKeys.set(moduleName + I18NKEYS,
				//			convertI18nKeys(p));

				} finally {
					in.close();
				}

			}
			cacheSupport.set(CACHEID, map);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getCacheId() {
		return CACHEID;
	}

	public Properties getI18nProperties(String moduleName) {
		return cacheSupport.get(CACHEID).get(moduleName);
	}

//	public String getI18nKeys(String key) {
//		return cacheSupportI18nKeys.get(key);
//	}

//	@SuppressWarnings("rawtypes")
//	private String convertI18nKeys(Properties pros) {
//		Enumeration enumeration = pros.propertyNames();
//		StringBuffer keys = new StringBuffer();
//		while (enumeration.hasMoreElements()) {
//			String key = (String) enumeration.nextElement() + ",";
//			keys.append(key);
//		}
//		return keys.toString();
//	}
}
