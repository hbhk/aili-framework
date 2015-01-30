package org.hbhk.aili.jms.share.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;

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
			Class<?>... parameterClasses) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			JavaType jt = mapper.getTypeFactory().constructParametricType(
					parametrized, parameterClasses);
			return mapper.readValue(json, jt);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String json, Class<?> parametrized) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			return (T) mapper.readValue(json, parametrized);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T toJson(Object obj){
		if (obj == null) {
			return null;
		}
		try {
			return (T) mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
