package org.hbhk.aili.security.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hbhk.aili.core.server.annotation.SecurityFilter;
import org.hbhk.aili.security.server.comparator.ResourceComparator;
import org.hbhk.aili.security.server.service.IResourceService;
import org.hbhk.aili.security.share.define.SecurityConstant;
import org.hbhk.aili.security.share.pojo.ResourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(SecurityConstant.moduleName)
public class LoginController {

	private Logger  log = LoggerFactory.getLogger(getClass());
	@Resource
	private IResourceService resourceService;

	@RequestMapping("/loginpage")
	@SecurityFilter(false)
	public String loginpage() {
		return "loginpage";
	}

	@SecurityFilter(false)
	@RequestMapping("/home")
	public String home() {
		return "home4";
	}

	@RequestMapping("/error")
	@SecurityFilter(false)
	public String error() {
		return "error";
	}

	@RequestMapping("/menu")
	@SecurityFilter(false)
	public String menu() {
		return "menu";
	}

	@RequestMapping("/getMenu")
	@SecurityFilter(false)
	@ResponseBody
	public List<ResourceInfo> getMenu(String root, String additional) {

		// ResourceInfo menus = new ResourceInfo();
		// menus.setText("text");
		// menus.setExpanded(true);
		// menus.setHasChildren(true);
		// List<ResourceInfo> mm = new ArrayList<ResourceInfo>();
		// for (int i = 0; i < 3; i++) {
		// ResourceInfo m = new ResourceInfo();
		// for (int j = 0; j < 1 && i == j; j++) {
		// ResourceInfo mj = new ResourceInfo();
		// mj.setText("textj");
		// List<ResourceInfo> mm1 = new ArrayList<ResourceInfo>();
		// mm1.add(mj);
		// m.setChildren(mm1);
		// }
		// m.setText("text" + i);
		// mm.add(m);
		// }
		// menus.setChildren(mm);
		List<ResourceInfo> ress  = new ArrayList<ResourceInfo>();
		ress = resourceService.getResByPaCode(root);
		//排序
		Collections.sort(ress, new ResourceComparator());
		log.info("menu");

		return ress;
	}

}