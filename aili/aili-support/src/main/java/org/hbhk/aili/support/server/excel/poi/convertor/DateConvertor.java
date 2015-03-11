package org.hbhk.aili.support.server.excel.poi.convertor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.hbhk.aili.support.server.excel.poi.SupportConstants;
import org.hbhk.aili.support.server.excel.poi.SupportSettings;
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
public class DateConvertor implements DataConvertor<Date> {
	
	private String datePattern = SupportSettings.getInstance().
		get(SupportConstants.DATE_PATTERN);
	
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public Date convert(Object value, int sheetNo, String cellIndex, 
			ExcelCell cellDefinition) throws ExcelManipulateException {
		if(value == null && cellDefinition.isMandatory())
			throw new ExcelManipulateException(ErrorCode.WRONG_DATA_NULL,
					new Object[]{sheetNo + 1, cellIndex,
					null,cellDefinition.getPattern(),
					cellDefinition.getChoiceString()});
		if(value == null) return null;
		if(value instanceof String){
			String str = (String) value;
			if(str.length() == 0){
				if(cellDefinition.isMandatory())
					throw new ExcelManipulateException(ErrorCode.WRONG_DATA_NULL,
							new Object[]{sheetNo + 1, cellIndex,
							null,cellDefinition.getPattern(),
							cellDefinition.getChoiceString()});
				else
					return null;
			}else{
				String pattern = cellDefinition.getPattern() == null ? 
						datePattern : cellDefinition.getPattern();
				try {
					DateFormat df = new SimpleDateFormat(pattern);
					return df.parse((String)value);
				} catch (ParseException e) {
					throw new ExcelManipulateException(ErrorCode.WRONG_DATA_TYPE_DATE,
							new Object[]{sheetNo + 1, cellIndex,
							value,cellDefinition.getPattern(),
							cellDefinition.getChoiceString()});
				}
			}
		}else if(value instanceof Date){
			return (Date)value;
		}else if(value instanceof Double){
			return DateUtil.getJavaDate((Double)value);
		}else
			throw new ExcelManipulateException(ErrorCode.WRONG_DATA_TYPE_DATE,
					new Object[]{sheetNo + 1, cellIndex,
					value,cellDefinition.getPattern(),
					cellDefinition.getChoiceString()});
	}

	public String getDataTypeAbbr() {
		return "date";
	}

	public Class<Date> supportClass() {
		return Date.class;
	}

}
