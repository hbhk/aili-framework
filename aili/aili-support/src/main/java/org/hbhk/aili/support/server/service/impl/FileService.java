package org.hbhk.aili.support.server.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.hbhk.aili.core.share.util.ReSizeImageUtil;
import org.hbhk.aili.core.share.util.SpringIOUtils;
import org.hbhk.aili.support.server.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements IFileService {

	public String saveFile(MultipartFile Filedata, String path, String name)
			throws IOException {
		SpringIOUtils.saveFile(Filedata.getInputStream(), path, name);
		return name;
	}

	public String saveFile(InputStream input, String path, String name)
			throws IOException {
		SpringIOUtils.saveFile(input, path, name);
		return name;
	}

	public String saveImage(InputStream input, String path, String name,
			int width, int height) throws IOException {
		ReSizeImageUtil.resize(input, width, height, path, name);
		return name;
	}

	public String saveImage(MultipartFile Filedata, String path, String name,
			int width, int height) throws IOException {
		ReSizeImageUtil.resize(Filedata.getInputStream(), width, height, path,
				name);
		return name;
	}

}
