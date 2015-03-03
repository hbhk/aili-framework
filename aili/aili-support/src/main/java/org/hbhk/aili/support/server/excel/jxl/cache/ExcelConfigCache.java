package org.hbhk.aili.support.server.excel.jxl.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hbhk.aili.support.server.excel.jxl.model.Model;

public class ExcelConfigCache {

	public static Map<String, Model> cache = new ConcurrentHashMap<String, Model>();
}
