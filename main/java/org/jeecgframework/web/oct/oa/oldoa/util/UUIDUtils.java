package org.jeecgframework.web.oct.oa.oldoa.util;

import java.util.UUID;

public class UUIDUtils {
	public static String getUUID(){	
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
}
