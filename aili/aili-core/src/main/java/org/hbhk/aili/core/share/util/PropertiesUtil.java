package org.hbhk.aili.core.share.util;

import java.io.IOException;

import org.springframework.core.io.support.ResourcePropertySource;

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
