package org.hbhk.aili.support.server.excel.poi.convertor;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelCell;
import org.hbhk.aili.support.server.excel.poi.exception.ExcelManipulateException;


public interface DataConvertor<T> {
	Class<T> supportClass();
	String getDataTypeAbbr();
	T convert(Object value, int sheetNo, String cellIndex, ExcelCell cellDefinition)
		throws ExcelManipulateException;
}
