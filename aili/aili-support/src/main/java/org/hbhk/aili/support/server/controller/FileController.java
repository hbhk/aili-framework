package org.hbhk.aili.support.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hbhk.aili.support.server.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/support")
public class FileController {

	@Autowired
	private IFileService fileService;

	@Value("${file.path}")
	private String path;

	@RequestMapping("/upload")
	public void upload(HttpServletRequest request,
			HttpServletResponse response, MultipartFile Filedata)
			throws Exception {
		fileService.saveFile(Filedata, path, Filedata.getOriginalFilename());

	}

	@RequestMapping("/uploadImg")
	public void upload(HttpServletRequest request,
			HttpServletResponse response, MultipartFile Filedata, int width,
			int height) throws Exception {
		fileService.saveImage(Filedata, path, Filedata.getOriginalFilename(), width, height);

	}
}
