package org.hbhk.aili.orm.server.service.impl;

import java.io.StringWriter;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.ResourceNotFoundException;

import org.hbhk.aili.orm.server.context.OrmContext;
import org.hbhk.aili.orm.server.service.IGetbrickTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "getbrickTemplate")
public class GetbrickTemplate implements IGetbrickTemplate {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public String setContextData(Map<String, Object> context, String id) {
		JetTemplate template = getTemplate(id);
		// 渲染模板
		StringWriter writer = new StringWriter();
		template.render(context, writer);
		String sql = writer.toString();
		log.debug("template-->>sql:" + sql);
		return sql;
	}

	@Override
	public JetTemplate getTemplate(String id) throws ResourceNotFoundException {
		JetEngine engine = JetEngine.create();
		String sql = OrmContext.getSql(id);
		log.debug("jet-template-sql:" + sql);
		// 获取一个模板对象
		JetTemplate template = engine.createTemplate(sql);
		return template;
	}

}
