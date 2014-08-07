package org.hbhk.aili.orm.share.util;

import java.io.IOException;

import org.springframework.core.io.support.ResourcePropertySource;

public class PropertiesUtil {

	public static String getPValue(String k) {
		try {
			ResourcePropertySource propertySource = new ResourcePropertySource(
					"config.properties");
			// orm.auotscan.path="org/hbhk/aili"
			// orm.auotscan.filename="orm.xml"
			return (String) propertySource.getProperty(k);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
