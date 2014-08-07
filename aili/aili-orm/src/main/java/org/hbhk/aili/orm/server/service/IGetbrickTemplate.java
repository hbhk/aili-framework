package org.hbhk.aili.orm.server.service;

import java.util.Map;

import jetbrick.template.JetTemplate;
import jetbrick.template.ResourceNotFoundException;

public interface IGetbrickTemplate {

	String setContextData(Map<String, Object> context,String beanId);

	JetTemplate getTemplate(String name) throws ResourceNotFoundException;
}
