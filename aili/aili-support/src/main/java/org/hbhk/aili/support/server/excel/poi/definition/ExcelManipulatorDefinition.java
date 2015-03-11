package org.hbhk.aili.support.server.excel.poi.definition;

import java.util.ArrayList;
import java.util.List;

import org.hbhk.aili.support.server.util.StringUtil;


/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ExcelManipulatorDefinition {
	private Integer styleSheetPosition;
	private List<ExcelSheet> excelSheets = new ArrayList<ExcelSheet>();

	public List<ExcelSheet> getExcelSheets() {
		return excelSheets;
	}

	public void setExcelSheets(List<ExcelSheet> excelSheets) {
		this.excelSheets = excelSheets;
	}

	public Integer getStyleSheetPosition() {
		return styleSheetPosition;
	}

	public void setStyleSheetPosition(Integer styleSheetPosition) {
		this.styleSheetPosition = styleSheetPosition;
	}

	@Override
	public String toString() {
		return "ExcelManipulatorDefinition [styleSheetPosition=" + (styleSheetPosition == null?"Undefined":styleSheetPosition) + 
				",\r\n excelSheets=\r\n\t" + StringUtil.join(excelSheets, "\r\n\t") + "]";
	}
	
	
}
