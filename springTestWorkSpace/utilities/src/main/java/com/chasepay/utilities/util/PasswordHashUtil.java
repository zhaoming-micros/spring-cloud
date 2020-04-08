package com.chasepay.utilities.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.codec.digest.Crypt;
import com.chasepay.utilities.constant.SystemConstants;

public class PasswordHashUtil {

	public static String getPassword(byte[] keyBytes)
	{
		if (keyBytes == null)
			return null;
		return Crypt.crypt(keyBytes, SHA512_PREFIX+SystemConstants.PSSD_SLT);
	}
	
	public static boolean isPasswdCorrect(byte[] keyBytes, String token)
	{
		if (token == null || keyBytes == null)
			return false;
		
		return token.equals(Crypt.crypt(keyBytes, SHA512_PREFIX+SystemConstants.PSSD_SLT));
	}
	
	public static String getToken(byte[] keyBytes, String email)
	{
		if (keyBytes == null)
			return null;
		
		return Crypt.crypt(keyBytes, SHA512_PREFIX+SystemConstants.PSSD_SLT+email);
	}
	
	public static String getActivateCode(String keyBytes)
	{
		return Crypt.crypt(keyBytes.getBytes(),SHA512_PREFIX+SystemConstants.ACTIVATE_SLT);
	}
	
	public static String getResetCode(String keyBytes)
	{
		return Crypt.crypt(SHA512_PREFIX+System.currentTimeMillis()+keyBytes.getBytes()+SystemConstants.RESET_SLT);
	}
	
	public static String getLoginId(String keyBytes)
	{
		return Crypt.crypt(keyBytes, SHA512_PREFIX+System.currentTimeMillis()+SystemConstants.LOGIN_SLT);
	}
	
	public static String getReqCode(String keyBytes)
	{
		return Crypt.crypt(keyBytes, SHA512_PREFIX+System.currentTimeMillis()+SystemConstants.REQ_SLT);
	}
	
	public static String getCodeDate()
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, SystemConstants.CODE_EXIPRE);
		
		return new SimpleDateFormat(SystemConstants.DATE_FORMAT2, Locale.ENGLISH).format(c.getTime());
	}
	
	public static int timeDiff(String oldTime)
	{
		
		SimpleDateFormat s = new SimpleDateFormat(SystemConstants.DATE_FORMAT2, Locale.ENGLISH);
		try {
			Date oldDate = s.parse(oldTime);
		
			Date now = new Date();
			
			//System.out.println((int)((now.getTime() - oldDate.getTime()) / (60 * 60 * 1000))); //hours
			//System.out.println((int)((now.getTime() - oldDate.getTime()) / (60 * 1000)));  //minute
			return (int)((now.getTime() - oldDate.getTime()) / (60 * 60 * 1000));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	private static final String SHA512_PREFIX = "$6$";
	
	/*
	public static void main(String[] args)
	{
		PasswordHashUtil u = new PasswordHashUtil();
		
		String token = u.getActivateCode(); 
				//u.getPassword("helloWorld".getBytes());
		System.out.println(token);
		
		//System.out.println(u.isPasswdCorrect("helloWorld".getBytes(), token));
	}
	*/

	
}
