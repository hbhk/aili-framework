package org.hbhk.aili.test.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		log.info("index");
		return "index";

	}
}
