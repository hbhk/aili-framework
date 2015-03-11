package org.hbhk.aili.jms.server.convertor;

import org.hbhk.aili.jms.share.ex.ConvertException;
import org.hbhk.aili.jms.share.util.JsonUtil;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
@Component("messageConvertor")
public class DefaultMessageConvertor<T> implements IMessageConvertor<T> {

	@Override
	public T toMessage(String str, Class<?> cls) throws ConvertException {
		return JsonUtil.parseJson(str, cls);
	}

	@Override
	public String fromMessage(Object message) throws ConvertException {
		return JsonUtil.toJson(message);
	}

}
