package org.hbhk.aili.support.server.excel.jxl;

import java.util.List;

import org.hbhk.aili.core.share.util.FileAsStringUtil;
import org.hbhk.aili.support.server.excel.jxl.cache.ExcelConfigCache;
import org.hbhk.aili.support.server.excel.jxl.convertor.ExcelConvertor;
import org.hbhk.aili.support.server.excel.jxl.model.Model;
import org.hbhk.aili.support.server.excel.jxl.model.Models;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class InitExcelConfig implements InitializingBean {

	private String path;

	@Override
	public void afterPropertiesSet() throws Exception {
		FileAsStringUtil stringUtil = new FileAsStringUtil();
		// 获取所有excel配置文件
		List<String> excelConfig = stringUtil.scanBeansXml(path,"xml");
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
