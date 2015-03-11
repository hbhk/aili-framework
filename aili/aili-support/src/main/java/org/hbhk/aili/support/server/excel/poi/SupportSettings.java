package org.hbhk.aili.support.server.excel.poi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class SupportSettings {
final static Logger logger = LoggerFactory.getLogger(SupportSettings.class);
	
	private static SupportSettings instance;
	private static final String[] CONFIGS = new String[]{"excel-support","excel/support-default"};
	
	private List<Properties> props = new ArrayList<Properties>(); 
	
	private SupportSettings(){
		for(String config: CONFIGS){
			InputStream is = getResourceAsStream(
					config + ".properties", SupportSettings.class);
			if(is != null){
				Properties prop = new Properties();
				try {
					prop.load(is);
					props.add(prop);
				} catch (IOException e) {
					e.printStackTrace();			
					logger.warn("Error occurs when loading {}.properties", config);
				}
			}else{
				logger.warn("Could not find {}.properties", config);
			}
		}
	}
	
	public static SupportSettings getInstance(){
		if(instance == null) instance = new SupportSettings();		
		return instance;
	}
	
	public String get(String name){
		String result = null;
		for(Properties prop: props){
			result = prop.getProperty(name);
			if(result != null) break;
		}
		return result;
	}
	
	private InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResource(resourceName, callingClass);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }
		
	private URL getResource(String resourceName, Class<?> callingClass) {
        URL url = null;
        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            url = SupportSettings.class.getClassLoader().getResource(resourceName);
        }
        if (url == null) {
            url = callingClass.getClassLoader().getResource(resourceName);
        }
        return url;
    }
}
