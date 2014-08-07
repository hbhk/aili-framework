package org.hbhk.aili.support.server.json;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.support.server.httpclient.exception.ClientException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 把json字符串转换为对象
	 * 
	 * @param json
	 * @param parametrized
	 * @param parameterClasses
	 * @return 如果为空串，将返回null
	 * @throws ClientException
	 * @since
	 */
	public static <T> T parseJson(String json, Class<?> parametrized,
			Class<?>... parameterClasses) throws ClientException {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			JavaType jt = mapper.getTypeFactory().constructParametricType(
					parametrized, parameterClasses);
			return mapper.readValue(json, jt);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String json, Class<?> parametrized)
			throws ClientException {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			return (T) mapper.readValue(json, parametrized);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T toJson(Object obj) throws ClientException {
		if (obj == null) {
			return null;
		}
		try {
			return (T) mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new ClientException(e.getMessage(), e);
		}
	}
}
