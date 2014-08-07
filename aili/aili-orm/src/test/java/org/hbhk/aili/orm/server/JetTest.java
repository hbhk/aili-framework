package org.hbhk.aili.orm.server;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

public class JetTest {

	public static void main(String[] args) {

		JetEngine engine = JetEngine.create();

		// 获取一个模板对象
		JetTemplate template = engine.createTemplate("${name}");

		// 创建 context 对象
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("name", "hbhk");
		// 渲染模板
		StringWriter writer = new StringWriter();
		template.render(context, writer);

		// 打印结果
		System.out.println(writer.toString());
	}

}
