package org.hbhk.aili.core.share.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public static final String SHA_1 = "SHA-1";
	public static final String MD5 = "MD5";

	public static String encodeMD5(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte bt[] = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return strSrc;
		}
		return strDes;
	}

	public static String encodeSHA1(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte bt[] = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return strSrc;
		}
		return strDes;
	}

	public static String encodeSHA256(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte bt[] = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return strSrc;
		}
		return strDes;
	}

	public static String bytes2Hex(byte bts[]) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = Integer.toHexString(bts[i] & 0xff);
			if (tmp.length() == 1)
				des = (new StringBuilder(String.valueOf(des))).append("0")
						.toString();
			des = (new StringBuilder(String.valueOf(des))).append(tmp)
					.toString();
		}

		return des;
	}

}
