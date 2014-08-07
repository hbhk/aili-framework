package org.hbhk.aili.support.server.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.FileLoadUtil;
import org.hbhk.aili.support.server.excel.cache.ExcelConfigCache;
import org.hbhk.aili.support.server.excel.model.Entry;
import org.hbhk.aili.support.server.excel.model.Model;
import org.hbhk.aili.support.server.excel.model.Property;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.Resource;

public class ExeclModelConvertor {
	private Log log = LogFactory.getLog(getClass());

	public <T> T getModel(File file, String modelId) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.error("getModel", e);
			throw new RuntimeException(e);
		}
		return getModel(stream, modelId);
	}

	@SuppressWarnings({ "unchecked" })
	public <T> T getModel(InputStream stream, String modelId) {
		if (stream == null) {
			log.error("getModel  stream 为空");
			throw new RuntimeException("stream 为空");
		}
		if (modelId == null || modelId.equals("")) {
			log.error("getModel  modelId 为空");
			throw new RuntimeException("modelId 为空");
		}
		Model model = ExcelConfigCache.cache.get(modelId);
		if (model == null) {
			log.error("getModel" + modelId + "对应的excel配置没有找到");
			throw new RuntimeException(modelId + "对应的excel配置没有找到");
		}

		Workbook book = null;
		try {
			book = Workbook.getWorkbook(stream);
		} catch (BiffException e) {
			log.error("getModel", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("getModel", e);
			throw new RuntimeException(e);
		}
		Sheet sheet = book.getSheet(0);
		List<Object> results = new ArrayList<Object>();
		for (int i = 1; i < sheet.getRows(); i++) {
			Object t = getModelInstance(model.getClazz().trim());
			BeanWrapper bw = new BeanWrapperImpl(t);
			for (int j = 0; j < sheet.getColumns(); j++) {
				String excelTitleName = sheet.getCell(j, 0).getContents()
						.trim();
				Property property = getPropertyName(model, excelTitleName);
				if (property != null) {
					String value = sheet.getCell(j, i).getContents().trim();
					if (value == null || value.length() < 1) {
						value = property.getDefault();
					}
					if (property.getIsConvertable() != null
							&& property.getIsConvertable().equals("true")) {
						value = getMapValueByKey(property.getMap().getEntry(),
								value);
					}
					String dateType = property.getDataType();
					Date date = null;
					if (dateType != null && dateType.equalsIgnoreCase("Date")) {
						String dateFormat = property.getFormat();
						if (StringUtils.isEmpty(dateFormat)) {
							dateFormat = "yyyy-MM-dd HH:mm:ss";
						}
						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
						try {
							date = sdf.parse(value);
						} catch (ParseException e) {
							log.error("getModel", e);
							throw new RuntimeException(e);
						}
					}
					if (date != null) {
						bw.setPropertyValue(property.getName(), date);
					} else {
						bw.setPropertyValue(property.getName(), value);
					}
				}

			}
			results.add(t);
		}
		return (T) results;

	}

	private String getMapValueByKey(List<Entry> entries, String value) {
		for (Entry entry : entries) {
			if (value.equals(entry.getExcelKey().trim())) {
				return entry.getBeanValue();
			}
		}
		return null;

	}

	private Property getPropertyName(Model model, String excelTitleName) {
		List<Property> properties = model.getProperty();
		for (Property property : properties) {
			if (excelTitleName.equals(property.getExcelTitleName().trim())) {
				return property;
			}
		}
		return null;

	}

	private Object getModelInstance(String className) {
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
			log.debug("init Class = " + className);
		} catch (ClassNotFoundException e) {
			log.error("getModelInstance", e);
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			log.error("getModelInstance", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("getModelInstance", e);
			throw new RuntimeException(e);
		}
		return obj;
	}

	public InputStream model2ExcelToStream(String modelId, List<Object> models) {
		File file = model2ExcelToFile(modelId, models);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.error("model2ExcelToStream", e);
			throw new RuntimeException(e);
		}
	}

	public File model2ExcelToFile(String modelId, List<Object> models) {
		// 获取模板文件
		Resource resource = null;
		try {
			resource = FileLoadUtil.getResourceForClasspath("support",
					"excelTemplate.xlsx");
		} catch (IOException e) {
			log.error("model2ExcelToFile", e);
			throw new RuntimeException(e);
		}
		File excelFile = null;
		try {
			excelFile = resource.getFile();
		} catch (Exception e) {
			log.error("model2ExcelToFile", e);
			throw new RuntimeException(e);
		}

		Model model = ExcelConfigCache.cache.get(modelId);
		List<Property> properties = model.getProperty();
		WritableWorkbook wb = null;
		try {
			wb = Workbook.createWorkbook(excelFile);
		} catch (IOException e) {
			log.error("model2ExcelToFile", e);
			throw new RuntimeException(e);
		}
		WritableSheet wsheet = wb.createSheet("first page", 0);
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);// Set the table header in bold
		WritableCellFormat format1 = new WritableCellFormat(font1);
		int columns_size = properties.size();
		for (Property property : properties) {
			int column = Integer.parseInt(property.getColumn());
			try {
				String width = property.getWidth();
				if (StringUtils.isEmpty(width)) {
					wsheet.setColumnView(column - 1, Integer.parseInt(width));
				} else {
					wsheet.setColumnView(column - 1, 30);
				}
				wsheet.addCell(new Label(column - 1, 0, property
						.getExcelTitleName(), format1));
			} catch (RowsExceededException e) {
				log.error("model2ExcelToFile", e);
				throw new RuntimeException(e);
			} catch (WriteException e) {
				log.error("model2ExcelToFile", e);
				throw new RuntimeException(e);
			}
		}
		// write real data to excel file from beans list
		for (int i = 0; i < models.size(); i++) {
			Object obj = models.get(i);
			BeanWrapper bw = new BeanWrapperImpl(obj);
			for (int k = 0; k < columns_size; k++) {
				String excelTitleName = wsheet.getCell(k, 0).getContents()
						.trim();// title name in excel
				Property property = getPropertyName(model, excelTitleName);
				String beanPropertyName = property.getName();
				Object propertyValue = bw.getPropertyValue(beanPropertyName);
				if (propertyValue == null) {
					continue;
				}
				String dataType = property.getDataType();
				// convert string to date
				if (dataType != null && dataType.equalsIgnoreCase("Date")) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							property.getFormat());
					propertyValue = sdf.format((Date) propertyValue);
				}
				if (property.getIsConvertable() != null
						&& property.getIsConvertable().equals("true")) {
					propertyValue = getMapValueByKey(property.getMap()
							.getEntry(), (String) propertyValue);
				}
				try {
					wsheet.addCell(new Label(Integer.parseInt(property
							.getColumn()) - 1, i + 1, (String) propertyValue));
				} catch (RowsExceededException e) {
					log.error("model2ExcelToFile", e);
					throw new RuntimeException(e);
				} catch (NumberFormatException e) {
					log.error("model2ExcelToFile", e);
					throw new RuntimeException(e);
				} catch (WriteException e) {
					log.error("model2ExcelToFile", e);
					throw new RuntimeException(e);
				}
			}
		}
		try {
			wb.write();
			wb.close();
		} catch (IOException e) {
			log.error("model2ExcelToFile", e);
			throw new RuntimeException(e);
		} catch (WriteException e) {
			log.error("model2ExcelToFile", e);
			throw new RuntimeException(e);
		}
		return excelFile;
	}
}
