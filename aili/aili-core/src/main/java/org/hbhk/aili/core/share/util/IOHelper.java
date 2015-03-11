package org.hbhk.aili.core.share.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class IOHelper {
	public static final Logger log = LoggerFactory.getLogger(IOHelper.class);

	public static boolean deleteFile(File fileToDelete) {
		if ((fileToDelete == null) || (!fileToDelete.exists())) {
			return true;
		}
		boolean result = deleteChildren(fileToDelete);
		result &= fileToDelete.delete();
		return result;
	}

	public static boolean deleteChildren(File parent) {
		if ((parent == null) || (!parent.exists())) {
			return false;
		}
		boolean result = true;
		if (parent.isDirectory()) {
			File[] files = parent.listFiles();
			if (files == null)
				result = false;
			else {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if ((!file.getName().equals("."))
							&& (!file.getName().equals(".."))) {
						if (file.isDirectory())
							result &= deleteFile(file);
						else {
							result &= file.delete();
						}
					}
				}
			}
		}
		return result;
	}

	public static void moveFile(File src, File targetDirectory)
			throws IOException {
		if (!src.renameTo(new File(targetDirectory, src.getName())))
			throw new IOException("Failed to move " + src + " to "
					+ targetDirectory);
	}

	public static void copyFile(File src, File dest) throws IOException {
		FileInputStream fileSrc = new FileInputStream(src);
		FileOutputStream fileDest = new FileOutputStream(dest);
		copyInputStream(fileSrc, fileDest,4096);
	}
	public static void copyFile(File src, File dest,int buffer) throws IOException {
		FileInputStream fileSrc = new FileInputStream(src);
		FileOutputStream fileDest = new FileOutputStream(dest);
		copyInputStream(fileSrc, fileDest,buffer);
	}
	public static void copyInputStream(InputStream in, OutputStream out,int buffer)
			throws IOException {
		byte[] bts = new byte[buffer];
		int len = in.read(bts);
		while (len >= 0) {
			out.write(bts, 0, len);
			len = in.read(bts);
		}
		in.close();
		out.close();
	}

	public static void mkdirs(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new IOException("Failed to create directory '" + dir
						+ "', regular file already existed with that name");
			}

		} else if (!dir.mkdirs())
			throw new IOException("Failed to create directory '" + dir + "'");
	}
}
