package org.hbhk.aili.hibernate.share.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


	/**
	 * 格式化时间格式
	 * @param value
	 * @param format
	 * @return
	 */
	public static String formatDate(Object value, String format) {
		if (format == null || format.trim().isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(value);
	}
	/**
	 * 格式化时间格式
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parseDate(String str, String format) {
		if (format == null || format.trim().isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
