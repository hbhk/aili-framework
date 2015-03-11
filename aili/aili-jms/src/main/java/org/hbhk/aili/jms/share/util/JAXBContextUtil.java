package org.hbhk.aili.jms.share.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @Description: jms增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class JAXBContextUtil {

	private static Log log = LogFactory.getLog(JAXBContextUtil.class);
	
	/**
	 * Inits the context.
	 * 
	 * @return the jAXB context
	 */
	@SuppressWarnings("rawtypes")
	public static final JAXBContext initContext(Class clzz) {
		try {
			return JAXBContext.newInstance(clzz);
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
