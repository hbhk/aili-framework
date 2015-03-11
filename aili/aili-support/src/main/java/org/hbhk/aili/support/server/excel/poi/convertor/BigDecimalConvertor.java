package org.hbhk.aili.support.server.excel.poi.convertor;

import java.math.BigDecimal;

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
public class BigDecimalConvertor implements DataConvertor<BigDecimal> {

	public BigDecimal convert(Object value, int sheetNo, String cellIndex, 
			ExcelCell cellDefinition) throws ExcelManipulateException {
		if(value == null && cellDefinition.isMandatory())
			throw new ExcelManipulateException(ErrorCode.WRONG_DATA_NULL,
					new Object[]{sheetNo + 1, cellIndex,
					null,cellDefinition.getPattern(),
					cellDefinition.getChoiceString()});
		if(value == null) return null;
		if(value instanceof String){
			String str = (String) value;
			str = str.trim();
			if(str.length() == 0){
				if(cellDefinition.isMandatory())
					throw new ExcelManipulateException(ErrorCode.WRONG_DATA_NULL,
							new Object[]{sheetNo + 1, cellIndex,
							null,cellDefinition.getPattern(),
							cellDefinition.getChoiceString()});
				else
					return null;
			}else{
				try {
					return new BigDecimal((String)value);
				} catch (NumberFormatException e) {
					throw new ExcelManipulateException(ErrorCode.WRONG_DATA_TYPE_NUMBER,
							new Object[]{sheetNo + 1, cellIndex,
							value,cellDefinition.getPattern(),
							cellDefinition.getChoiceString()});
				}
			}
		}else if(value instanceof Double){
			return new BigDecimal((Double)value);
		}else
			throw new ExcelManipulateException(ErrorCode.WRONG_DATA_TYPE_NUMBER,
					new Object[]{sheetNo + 1, cellIndex,
					value,cellDefinition.getPattern(),
					cellDefinition.getChoiceString()});
	}

	public String getDataTypeAbbr() {
		return "bigdecimal";
	}

	public Class<BigDecimal> supportClass() {
		return BigDecimal.class;
	}

}
