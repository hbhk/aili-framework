package org.hbhk.aili.support.server.excel.poi;

import java.io.InputStream;
import java.util.Map;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelManipulatorDefinition;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface ExcelReader {
	
	/**
	 * Read All sheets infos into one beans scope with multiple sheet definitions
	 * @param is
	 * @param beans
	 * @return
	 */
	ReadStatus readAll(InputStream is, Map<String, Object> beans);
	
	/**
	 * Read All sheets using one sheet definition to get colletion infos
	 * @param is
	 * @param beans
	 * @return
	 */
	ReadStatus readAllPerSheet(InputStream is, Map<String,Object> beans);
	
	/**
	 * Read specific sheet using one sheet definition
	 * @param is
	 * @param sheetNo
	 * @param beans
	 * @return
	 */
	ReadStatus readSheet(InputStream is, int sheetNo, Map<String,Object> beans);
	ExcelManipulatorDefinition getDefinition();
	void setDefinition(ExcelManipulatorDefinition definition);
}
