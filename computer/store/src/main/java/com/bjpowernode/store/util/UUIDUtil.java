package com.bjpowernode.store.util;

import org.springframework.util.DigestUtils;


public class UUIDUtil {
	
	public static String getUUID(String password,String sale){
		String password1 = "";
		for (int i = 0; i < 3; i++) {
			password1 = DigestUtils.md5DigestAsHex((sale+password+sale).getBytes()).toUpperCase();
		}
		//加密算法
		//(sale+password+sale).getBytes():转为字节
		return password1;
	}
}
