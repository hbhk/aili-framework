package org.hbhk.aili.theme.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theme")
public class DemoAction{
	
	@RequestMapping("btsp")
	public  String  bootstap(){
		return  "bootstrap";
	}
	
	@RequestMapping("index")
	public  String  index(){
		return  "index";
	}
	
	
}