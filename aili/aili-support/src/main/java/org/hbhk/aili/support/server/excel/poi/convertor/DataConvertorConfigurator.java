package org.hbhk.aili.support.server.excel.poi.convertor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class DataConvertorConfigurator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6172555487692156540L;

	private Map<String, Class<?>> supportsMap = new HashMap<String, Class<?>>();
	private Map<Class<?>, DataConvertor<?>> convertorMap = new HashMap<Class<?>, DataConvertor<?>>();
	
	private static DataConvertorConfigurator instance;
	
	private DataConvertorConfigurator(){
		registerDataConvertor(new StringConvertor());
		registerDataConvertor(new IntegerConvertor());
		registerDataConvertor(new LongConvertor());
		registerDataConvertor(new DoubleConvertor());
		registerDataConvertor(new BigDecimalConvertor());
		registerDataConvertor(new DateConvertor());
	}
	
	public void registerDataConvertor(DataConvertor<?> dc){
		supportsMap.put(dc.getDataTypeAbbr(), dc.supportClass());
		convertorMap.put(dc.supportClass(), dc);
	}
	
	@SuppressWarnings("unchecked")
	public <T> DataConvertor<T> getConvertor(Class<T> clazz){
		return (DataConvertor<T>)convertorMap.get(clazz);
	}
	
	public Class<?> getSupportedClass(String name){
		return supportsMap.get(name);
	}
	
	public static DataConvertorConfigurator getInstance(){
		if(instance == null)
			instance = new DataConvertorConfigurator();
		return instance;
	}
}
