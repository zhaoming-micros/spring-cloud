package com.chasepay.manage;

import com.chasepay.utilities.util.PasswordHashUtil;

public class TestActiveCode {
	
	public static void main(String[] args) {
		
		String userName = "zhaoming";
		String email = "waxdyy2007@126.com";
		String password = "1987o2o2";
		
		String activateCode = PasswordHashUtil.getPassword("1987o2o2".getBytes());
		System.out.println(activateCode);
	}

}
