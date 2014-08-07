package org.hbhk.aili.cache.share.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {

	/**
	 * 序列化对象 @throws IOException
	 */
	public static byte[] serializeObject(Object object) {
		byte[] serObj = null;
		ByteArrayOutputStream saos = null;
		ObjectOutputStream oos = null;
		try {
			saos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(saos);
			oos.writeObject(object);
			oos.flush();
			serObj = saos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (saos != null) {
					saos.close();
				}
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return serObj;
	}

	/**
	 * 反序列化对象 @throws IOException @throws ClassNotFoundException
	 */
	public static Object deserializeObject(byte[] buf) {
		Object object = null;
		ByteArrayInputStream sais = null;
		ObjectInputStream ois = null;
		try {
			sais = new ByteArrayInputStream(buf);
			ois = new ObjectInputStream(sais);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sais != null) {
					sais.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return object;
	}

}
