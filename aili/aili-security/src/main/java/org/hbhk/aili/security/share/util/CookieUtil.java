package org.hbhk.aili.security.share.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 添加一个cookie值
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @param time
	 *            cookie的有效期
	 * @param response
	 *            保存cookie的对象
	 */
	public static void setCookie(String name, String value, Integer time,
			HttpServletResponse response) {
		try {
			// 关键点
			value = URLEncoder.encode(value, "UTF-8");

		} catch (UnsupportedEncodingException e) {
		}
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(time);
		response.addCookie(cookie);
	}

	/**
	 * 根据name值,从cookie当中取值
	 * 
	 * @param name
	 *            要获取的name
	 * @param request
	 *            cookie存在的对象
	 * @return 与name对应的cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		Cookie[] cs = request.getCookies();
		String value = "";
		if (cs != null) {
			for (Cookie c : cs) {
				if (name.equals(c.getName())) {
					try {

						// 关键点
						value = URLDecoder.decode(c.getValue(), "UTF-8");

					} catch (UnsupportedEncodingException e) {
					}
					return value;
				}
			}
		}
		return value;

	}
}
