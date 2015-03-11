package org.hbhk.aili.support.server.excel.poi.convertor;

import java.util.Arrays;
import java.util.List;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelCell;
import org.hbhk.aili.support.server.excel.poi.exception.ErrorCode;
import org.hbhk.aili.support.server.excel.poi.exception.ExcelManipulateException;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class StringConvertor extends ChoiceConvertor<String> {

	@Override
	protected String convertValue(Object value, int sheetNo, String cellIndex, 
			ExcelCell cellDefinition) throws ExcelManipulateException {
		String str = (value == null ? null : value.toString());
		if(str != null && str.length() == 0) str = null;
		if(str == null && cellDefinition.isMandatory())
			throw new ExcelManipulateException(ErrorCode.WRONG_DATA_NULL,
					new Object[]{sheetNo + 1, cellIndex,
					null,cellDefinition.getPattern(),
					cellDefinition.getChoiceString()});
		//remove .0 for Integer Data
		if(value instanceof Double 
				&& str.length() > 2 && str.lastIndexOf(".0") == (str.length() - 2))
			str = str.substring(0, str.length() -2);
		return str;
	}

	@Override
	protected List<? extends String> getChoices(ExcelCell cellDefinition) {
		if(cellDefinition.getAvailableChoices() == null ||
				cellDefinition.getAvailableChoices().length == 0)
			return null;
		return Arrays.asList(cellDefinition.getAvailableChoices());
	}

	public String getDataTypeAbbr() {
		return "string";
	}

	public Class<String> supportClass() {
		return String.class;
	}
}
