package org.hbhk.aili.support.server.excel.poi.convertor;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelCell;
import org.hbhk.aili.support.server.excel.poi.exception.ExcelManipulateException;


/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface DataConvertor<T> {
	Class<T> supportClass();
	String getDataTypeAbbr();
	T convert(Object value, int sheetNo, String cellIndex, ExcelCell cellDefinition)
		throws ExcelManipulateException;
}
