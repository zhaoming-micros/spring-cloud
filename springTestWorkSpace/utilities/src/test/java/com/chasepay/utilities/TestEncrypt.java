package com.chasepay.utilities;

import com.chasepay.utilities.util.DesCryptUtil;

public class TestEncrypt {
	
	public static void main(String[] args) {
		String encrypt = DesCryptUtil.encrypt("12|token");
		System.out.println(encrypt);
		
		String decrypt = DesCryptUtil.decrypt(encrypt);
		System.out.println(decrypt);
		
        
		

	}

}
