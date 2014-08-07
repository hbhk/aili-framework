package org.hbhk.aili.security.share.util;

import java.util.UUID;

public class UUIDUitl {

	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
