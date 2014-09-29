package org.hbhk.aili.orm.server.service.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.ResourceNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.orm.server.context.OrmContext;
import org.hbhk.aili.orm.server.service.IGetbrickTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "getbrickTemplate")
public class GetbrickTemplate implements IGetbrickTemplate {

	private Logger log = LoggerFactory.getLogger(getClass());
	private static JetEngine engine = JetEngine.create();
	private static Map<String, JetTemplate> ormTemplateCache = new HashMap<String, JetTemplate>();

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
		String sql = OrmContext.getSql(id);
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("id:"+id+"未找到对应的sql");
		}
		log.debug("jet-template-sql:" + sql);
		JetTemplate template = null;
		// 获取一个模板对象
		if (ormTemplateCache.containsKey(id)) {
			template = ormTemplateCache.get(id);
		} else {
			template = engine.createTemplate(sql);
			ormTemplateCache.put(id, template);
		}
		return template;
	}

}
