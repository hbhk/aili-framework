package org.hbhk.aili.cache.server.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.hbhk.aili.cache.server.serialize.ICacheSerialize;

public class DefaultCacheSerialize implements ICacheSerialize<byte[]> {

	@Override
	public byte[] serialize(Object obj) {
		byte[] serObj = null;
		ByteArrayOutputStream saos = null;
		ObjectOutputStream oos = null;
		try {
			saos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(saos);
			oos.writeObject(obj);
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

	@Override
	public Object deserialize(byte[] obj) {
		Object object = null;
		ByteArrayInputStream sais = null;
		ObjectInputStream ois = null;
		try {
			sais = new ByteArrayInputStream(obj);
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
