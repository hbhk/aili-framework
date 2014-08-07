package org.hbhk.aili.support.server.excel.convertor;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.support.server.excel.model.Models;
import org.hbhk.aili.support.server.excel.model.ObjectFactory;
import org.hbhk.aili.support.server.util.JAXBContextUtil;

public class ExcelConvertor {

	private static Log log = LogFactory.getLog(ExcelConvertor.class);
	/** The Constant CLZZ. */
	private static final Class<Models> CLZZ = Models.class;

	/** The log. */
	/** The context. */
	private static JAXBContext context = JAXBContextUtil.initContext(CLZZ);

	public Models toMessage(String msg) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(msg)) {
			throw new RuntimeException("msg is null");
		}
		if (context == null) {
			JAXBContextUtil.initContext(CLZZ);// 再次尝试一次
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(
					msg.getBytes("UTF-8"));
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<Models> element = unmarshaller.unmarshal(
					new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			log.error("解析orm文件出错", e);
			throw new RuntimeException(e);
		}
	}

	public String fromMessage(Models value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		if (context == null) {
			JAXBContextUtil.initContext(CLZZ);// 再次尝试一次
		}
		try {
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,
					"UTF-8");
			JAXBElement<Models> element = new ObjectFactory()
					.createModels(value);
			marshaller.marshal(element, stringWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} catch (PropertyException e) {
			log.error("解析orm文件出错", e);
			throw new RuntimeException(e);
		} catch (FactoryConfigurationError e) {
			log.error("解析orm文件出错", e);
			throw new RuntimeException(e);
		} catch (JAXBException e) {
			log.error("解析orm文件出错", e);
			throw new RuntimeException(e);
		}
	}
}
