package org.hbhk.aili.core.share.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.hbhk.aili.core.server.web.WebApplicationContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 提供文件操作的的工具类
 */
public final class FileLoadUtil {
	/**
	 * 
	 * <p>
	 * 通过模块名称和文件名称查找文件
	 * </p>
	 */
	public static Resource[] getResourcesForClasspath(String moduleName,
			String fileName) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:org/hbhk/**/"
				+ moduleName + "/**/server/META-INF/**/" + fileName);
		return resources;
	}

	
	public static Resource getResourceForClasspath(String moduleName,String fileName)
			throws IOException {
		return getResourcesForClasspath(moduleName, fileName)[0];
	}

	/**
	 * <p>
	 * 通过模块名称和文件名称查找文件
	 * </p>
	 */
	public static InputStream getInputStreamForClasspath(String moduleName,
			String fileName) throws FileNotFoundException, IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:org/hbhk/**/"
				+ moduleName + "/**/server/META-INF/**/" + fileName);
		if (resources == null || resources.length < 1) {
			throw new FileNotFoundException("file '" + fileName
					+ "' not found in this module");
		}
		InputStream in = resources[0].getInputStream();
		return in;
	}

	public static InputStream getInputStreamForClasspath(String fileName)
			throws FileNotFoundException, IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:" + fileName);
		if (resources == null || resources.length < 1) {
			throw new FileNotFoundException("file '" + fileName
					+ "' not found in this root path!");
		}
		InputStream in = resources[0].getInputStream();
		return in;
	}

	public static Resource[] getResourcesForServletpath(String path)
			throws IOException {
		Resource[] resources = WebApplicationContextHolder
				.getWebApplicationContext().getResources(path);
		return resources;
	}

	public static Resource getResourceForServletpath(String fileName)
			throws IOException {
		Resource resource = WebApplicationContextHolder
				.getWebApplicationContext().getResource(fileName);
		return resource;
	}

	public static InputStream getInputStreamForServletpath(String filePath)
			throws FileNotFoundException, IOException {
		Resource[] resources = WebApplicationContextHolder
				.getWebApplicationContext().getResources(filePath);
		if (resources == null || resources.length < 1) {
			throw new FileNotFoundException("file '" + filePath
					+ "' not found in this module");
		}
		InputStream in = resources[0].getInputStream();
		return in;
	}

}
