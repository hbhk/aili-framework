package org.hbhk.aili.test.server.bina;

public class BinaScu {

	public static void main(String[] args) {
		int i = 1;
		int u = 2;
		int k = 4;
		int l = 8;

		int[] bbb = new int[20];
		for (int j = 0; j < 20; j++) {
			bbb[i] = j;
		}
		int srrr = 0;
		for (int j = 0; j < 20; j++) {
			int dd = srrr | bbb[j];
			srrr+=srrr|dd;
		}
		int a = i | u | k | l | l | 5 | 6 | 7 | 10| 23| 256; // 这样是得到所有权限
		System.out.println("a:"+a);
		System.out.println("ss"+srrr);

		int t = i | l| 10 ;
		System.out.println("t:"+t);
		// 上面的几个变量都是2的多少次方得来的（2^1,2^2..2^n），如果不是，就用Math.pow(2,n)来计算一次。
		// “|”的原理是二进制有一个为1就为1，“&”的原理就是二进制两个都为1才是1，
		// 判断是否具有某种权限 （具体权限&总权限）== 具体权限，如果有该权限，为true，没有则false。

		System.out.println((4 & t) == 4);

	}

}
