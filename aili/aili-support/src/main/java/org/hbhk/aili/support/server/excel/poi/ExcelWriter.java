package org.hbhk.aili.support.server.excel.poi;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelManipulatorDefinition;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface ExcelWriter {
	WriteStatus write(OutputStream os, Map<String, Object> beans);
	WriteStatus writePerSheet(OutputStream os, List<Map<String,Object>> beansList);
	
	WriteStatus write(String template, OutputStream os, Map<String, Object> beans);
	WriteStatus write(InputStream is, OutputStream os, Map<String, Object> beans);
	WriteStatus writePerSheet(String template, OutputStream os, List<Map<String,Object>> beansList);
	WriteStatus writePerSheet(InputStream is, OutputStream os, List<Map<String,Object>> beansList);
	ExcelManipulatorDefinition getDefinition();
	void setDefinition(ExcelManipulatorDefinition definition);
}
