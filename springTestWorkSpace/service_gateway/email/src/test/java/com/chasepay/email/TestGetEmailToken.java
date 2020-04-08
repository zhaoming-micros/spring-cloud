package com.chasepay.email;

import org.apache.commons.codec.digest.Crypt;


public class TestGetEmailToken {
	
	private static final String SHA512_PREFIX = "$6$";
	private static final String PSSD_SLT = "CPayEmailLuca";

	public static void main(String[] args)
	{
		String a = "FKCOSPRSRULLZGWF";
		Util util = new Util();
		String password = util.getPassword(a.getBytes());
		System.out.println(password);
	}
}
