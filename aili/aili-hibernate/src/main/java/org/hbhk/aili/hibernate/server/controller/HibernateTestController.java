package org.hbhk.aili.hibernate.server.controller;

import javax.annotation.Resource;

import org.hbhk.aili.core.server.annotation.SecurityFilter;
import org.hbhk.aili.hibernate.server.service.ICateService;
import org.hbhk.aili.hibernate.share.model.Category;
import org.hbhk.aili.hibernate.share.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HibernateTestController {

	@Resource
	ICateService cateService;

	@RequestMapping("/t1")
	@SecurityFilter(false)
	@ResponseBody
	public String t1() {
		User user = new User();
		user.setAge("12");
		user.setId("1");
		user.setUserName("hbhk");
		return "";
	}

	@RequestMapping("/t2")
	@SecurityFilter(false)
	@ResponseBody
	public String t2() {
		Category c = new Category();
		c.setId(2);
		c.setName("c1");
		c.setDescription("desc");
		cateService.save(c);
		return "";
	}

	public void t3() {

	}

}
