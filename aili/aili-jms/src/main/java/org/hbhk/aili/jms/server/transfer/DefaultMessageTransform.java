package org.hbhk.aili.jms.server.transfer;

import org.hbhk.aili.jms.share.ex.ConvertException;
import org.hbhk.aili.jms.share.util.JsonUtil;

public class DefaultMessageTransform implements IMessageTransform<Object> {

	@Override
	public Object toMessage(String str,Class<?> cls) throws ConvertException {
		return JsonUtil.parseJson(str, cls);
	}

	@Override
	public String fromMessage(Object message) throws ConvertException {
		return JsonUtil.toJson(message);
	}

}
