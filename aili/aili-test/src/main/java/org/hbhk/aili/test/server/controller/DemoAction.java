package org.hbhk.aili.test.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class DemoAction{
	
	
	@RequestMapping("btsp")
	public  String  bootstap(){
		return  "bootstrap";
	}
	
	
	
}