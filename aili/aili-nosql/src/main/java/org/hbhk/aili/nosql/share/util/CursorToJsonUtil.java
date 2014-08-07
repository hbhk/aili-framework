package org.hbhk.aili.nosql.share.util;

import java.util.ArrayList;
import java.util.List;

import org.hbhk.aili.nosql.share.pojo.DBBaseInfo;

import com.alibaba.fastjson.JSON;
import com.mongodb.DBCursor;

public class CursorToJsonUtil {
	public static List<String> getCursorToStr(DBCursor cursor) {
		List<String> results = new ArrayList<String>();
		while (cursor.hasNext()) {
			String result = cursor.next().toString();
			results.add(result);
		}
		return results;
	}

	public static <T> List<T> getCursorToObj(DBCursor cursor, Class< ? extends DBBaseInfo> clz ) {
		List<String> results = new ArrayList<String>();
		while (cursor.hasNext()) {
			String result = cursor.next().toString();
			results.add(result);
		}
		return getJson(results, clz);
	}
	

	@SuppressWarnings("unchecked")
	public static <T> T getJson(String json,Class< ? extends DBBaseInfo> clz ) {
		return (T)JSON.parseObject(getJson(json),clz);
	}

	public static String getJson(String json) {
		json = json.replace("_id", "id");
		json = json.replace("$oid", "oid");
		return json;
	}

	public static <T> List<T> getJson(List<String> jsons, Class< ? extends DBBaseInfo> clz ) {
		List<T> dbs = new ArrayList<T>();
		for (String json : jsons) {
			T  obj = getJson(json, clz);
			dbs.add(obj);
		}
		return dbs;
	}

}
