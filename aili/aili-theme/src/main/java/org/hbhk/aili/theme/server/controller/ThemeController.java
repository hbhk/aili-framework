package org.hbhk.aili.theme.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theme")
public class ThemeController {

	@RequestMapping("common")
	public String bootstap() {
		return "common";
	}

}