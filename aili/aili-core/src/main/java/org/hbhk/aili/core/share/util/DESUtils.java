package org.hbhk.aili.core.share.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {

	private static Key key;
	private static String key_str = "seckey";

	static {

		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(key_str.getBytes()));
			key = generator.generateKey();

			generator = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getEncyptString(String str) throws Exception {
		BASE64Encoder base64 = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes();
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encyptStrBytes = cipher.doFinal(strBytes);
			return base64.encode(encyptStrBytes);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}
	
	public static String getDecyptString(String str) throws Exception {
		BASE64Decoder base64 = new BASE64Decoder();
		try {
			byte[] strBytes = base64.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encyptStrBytes = cipher.doFinal(strBytes);
			
			return new String(encyptStrBytes,"UTF-8");
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	public static void main(String[] args) throws Exception {
		String str= "hbhk";
		String en =getEncyptString(str);
		System.out.println(en);
		String de= getDecyptString(en);
		System.out.println(de);
	}
}
