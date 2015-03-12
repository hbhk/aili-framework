package org.hbhk.aili.support.server.file;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @Description: 整合第三方框架支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IFileService {

	String saveFile(MultipartFile Filedata, String path, String name)
			throws Exception;

	String saveFile(InputStream input, String path, String name)
			throws Exception;

	String saveImage(MultipartFile Filedata, String path, String name,
			int width, int height) throws IOException;

	String saveImage(InputStream input, String path, String name, int width,
			int height) throws IOException;
	
	void downloadFile(HttpServletResponse response,InputStream input,String name,int buffer) throws IOException;
}
