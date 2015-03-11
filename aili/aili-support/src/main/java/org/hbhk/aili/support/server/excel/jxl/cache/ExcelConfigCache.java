package org.hbhk.aili.support.server.excel.jxl.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hbhk.aili.support.server.excel.jxl.model.Model;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class ExcelConfigCache {

	public static Map<String, Model> cache = new ConcurrentHashMap<String, Model>();
}
