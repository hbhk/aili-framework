package org.hbhk.aili.support.server.excel.poi.convertor;

import java.util.List;

import org.hbhk.aili.support.server.excel.poi.definition.ExcelCell;
import org.hbhk.aili.support.server.excel.poi.exception.ErrorCode;
import org.hbhk.aili.support.server.excel.poi.exception.ExcelManipulateException;

public abstract class ChoiceConvertor<T> implements DataConvertor<T> {

	public T convert(Object value, int sheetNo, String cellIndex, ExcelCell cellDefinition) throws ExcelManipulateException {
		T convertedValue = convertValue(value, sheetNo, cellIndex, cellDefinition);
		List<? extends T> choices = getChoices(cellDefinition);
		if(convertedValue == null || choices == null || choices.contains(convertedValue))
			return convertedValue;
		throw new ExcelManipulateException(ErrorCode.OUT_OF_CHOICES,
				new Object[]{sheetNo + 1, cellIndex,
				convertedValue,cellDefinition.getPattern(),
				cellDefinition.getChoiceString()});
	}
	
	protected abstract List<? extends T> getChoices(ExcelCell cellDefinition);
	protected abstract T convertValue(Object value, int sheetNo, String cellIndex, ExcelCell cellDefinition)
	 throws ExcelManipulateException;

}
