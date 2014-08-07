package org.hbhk.test;

import org.hbhk.aili.core.share.util.EncryptUtil;

public class EncrypTest {
	public static void main(String args[]) {
		String strSrc = "123456";
		System.out.println((new StringBuilder("Source String:")).append(strSrc)
				.toString());
		System.out.println("Encrypted String:");
		System.out.println(EncryptUtil.encodeMD5(strSrc));
		System.out.println(EncryptUtil.encodeSHA1(strSrc));
		System.out.println(EncryptUtil.encodeSHA256(strSrc));
	}
}
