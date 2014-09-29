package org.hbhk.aili.support.server.excel;

import java.util.List;

import org.hbhk.aili.core.share.util.FileAsStringUtil;
import org.hbhk.aili.support.server.excel.cache.ExcelConfigCache;
import org.hbhk.aili.support.server.excel.convertor.ExcelConvertor;
import org.hbhk.aili.support.server.excel.model.Model;
import org.hbhk.aili.support.server.excel.model.Models;
import org.springframework.beans.factory.InitializingBean;

public class InitExcelConfig implements InitializingBean {

	private String path;

	@Override
	public void afterPropertiesSet() throws Exception {
		FileAsStringUtil stringUtil = new FileAsStringUtil();
		// 获取所有excel配置文件
		List<String> excelConfig = stringUtil.scanBeansXml(path);
		if (excelConfig != null && excelConfig.size() != 0) {
			ExcelConvertor convertor = new ExcelConvertor();
			for (String str : excelConfig) {
				Models models = convertor.toMessage(str);
				List<Model> ms = models.getModel();
				if (ms != null && ms.size() != 0) {
					for (Model model : ms) {
						String id = model.getId();
						if (ExcelConfigCache.cache.containsKey(id)) {
                              throw new RuntimeException("excel配置文件id重复");
						}
						ExcelConfigCache.cache.put(id, model);
					}
				}
			}
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
