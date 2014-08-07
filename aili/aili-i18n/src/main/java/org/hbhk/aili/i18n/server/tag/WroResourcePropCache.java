package org.hbhk.aili.i18n.server.tag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.FileLoadUtil;

/**
 * 页面静态文件版本信息缓存
 */
public class WroResourcePropCache {

	private static final Log logger = LogFactory
			.getLog(WroResourcePropCache.class);

	private static WroResourcePropCache INSTANCE;

	// 是否开启缓存，true开启，false未开启
	private boolean enableWroCache = true;

	/**
	 * 保存所有缓存实例
	 */
	private final Map<String, Properties> caches = new ConcurrentHashMap<String, Properties>();

	/**
	 * 禁止从外部拿到实例 创建一个新的实例 WroResourcePropCache.
	 */
	private WroResourcePropCache() {
	}

	public void setEnableWroCache(boolean enableWroCache) {
		this.enableWroCache = enableWroCache;
	}

	public void init() {
		INSTANCE = this;
	}

	public boolean getEnableWroCache() {
		if (WroResourcePropCache.getInstance() == null) {
			return true;
		} else {
			return WroResourcePropCache.getInstance().enableWroCache;
		}
	}

	public static WroResourcePropCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new WroResourcePropCache();
			logger.error("wroResourcePropCache is not config!");
		}
		return INSTANCE;
	}

	/**
	 * 根据wroResPropId获取缓存实例 getWroResourceInfo
	 */
	public Properties getWroResourceInfo(String moduleName) {
		String wroResPropId = "wro_" + moduleName;
		Properties wroResPropInfo = caches.get(wroResPropId);
		if (!getEnableWroCache() || wroResPropInfo == null) {
			wroResPropInfo = new Properties();
			try {
				InputStream in = FileLoadUtil.getInputStreamForClasspath(
						moduleName, "wromapping.properties");
				wroResPropInfo.load(in);
				caches.put(wroResPropId, wroResPropInfo);
			} catch (IOException e) {
				logger.debug(e.getMessage());
			}
		}
		return wroResPropInfo;
	}
}