package com.chasepay.utilities.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.utilities.constant.SystemConstants;

public class DesCryptUtil 
{

	private static Logger logger = LogManager.getLogger(ServletUtil.class);
	
	public static String encrypt(String data)
	{
		try 
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(StringConverter.getBytes(SystemConstants.DES_KEY), "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] doFinal = cipher.doFinal(data.getBytes());
			return StringConverter.getHex(doFinal);
		} 
		catch (Exception e) 
		{
			logger.error("Des encrypt error.");
			logger.error(e.getMessage(),e);
			return null;
		}
		
	}
	
	
	public static String decrypt(String data)
	{
		try 
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(StringConverter.getBytes(SystemConstants.DES_KEY), "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] doFinal = cipher.doFinal(StringConverter.getBytes(data));
			return new String(doFinal);
		} 
		catch (Exception e) 
		{
			logger.error("Des decrypt error.");
			logger.error(e.getMessage(),e);
			return null;
		}
		
	}

}
