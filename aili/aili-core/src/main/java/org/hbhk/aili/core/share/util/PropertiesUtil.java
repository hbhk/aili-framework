package org.hbhk.aili.core.share.util;

import java.io.IOException;

import org.springframework.core.io.support.ResourcePropertySource;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class PropertiesUtil {
	private static ResourcePropertySource propertySource = null;

	public static String getPValue(String k) {
		try {
			if (propertySource == null) {
				propertySource = new ResourcePropertySource("config.properties");
			}
			return (String) propertySource.getProperty(k);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
