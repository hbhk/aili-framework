package org.hbhk.aili.support.server.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	String saveFile(MultipartFile Filedata, String path, String name)
			throws Exception;

	String saveFile(InputStream input, String path, String name)
			throws Exception;

	String saveImage(MultipartFile Filedata, String path, String name,
			int width, int height) throws IOException;

	String saveImage(InputStream input, String path, String name, int width,
			int height) throws IOException;
}
