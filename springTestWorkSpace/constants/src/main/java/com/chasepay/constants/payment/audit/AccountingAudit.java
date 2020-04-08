package com.chasepay.constants.payment.audit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.webutil.AccountDatabaseConstants;


public class AccountingAudit {

	static Logger logger = LogManager.getLogger(AccountingAudit.class);
	
	public static void saveAudit(String userId, String companyId, String tableName, String recordId, String action, String newValue)
	{
		if (userId == null)
			userId = AccountDatabaseConstants.USER_SYSTEM;
		
		StringBuilder s = new StringBuilder(1024);
		s.append("\"")
		.append(userId)
		.append("\",\"")
		.append(companyId)
		.append("\",\"")
		.append(tableName)
		.append("\",\"")
		.append(recordId)
		.append("\",\"")
		.append(action)
		.append("\",\"")
		.append(newValue)
		.append("\"");
		
		logger.fatal(s.toString());
	}
	
	public static void saveLoginInfo(String userId, String code, String action, String userName)
	{
		
		StringBuilder s = new StringBuilder(1024);
		s.append("\"")
		.append(userId)
		.append("\",\"")
		.append(userName)
		.append("\",\"")
		.append(code)
		.append("\",\"")
		.append(action)
		.append("\"");
		
		logger.log(LOGIN_INFO, s.toString());
	}
	
	public static void saveLoginFail(String userName, String code, String requestHeader)
	{

		StringBuilder s = new StringBuilder(1024);
		s.append("\"")
		.append(userName)
		.append("\",\"")
		.append(code)
		.append("\",\"")
		.append(requestHeader)
		.append("\"");
		
		logger.log(LOGIN_FAIL_INFO, s.toString());
	}
	
	public static void saveModuleInfo(String message)
	{
		logger.log(MODULE_INFO, message);
	}
	
	public static void saveURLInfo(String message)
	{
		logger.log(URL_INFO, message);
	}
	
	public static void saveError(String message)
	{
		//System.out.println("in saveError:"+message);
		logger.log(ERROR, message);
	}
	
	
	private static Level LOGIN_INFO = Level.getLevel("LOGIN_LOG");
	private static Level LOGIN_FAIL_INFO = Level.getLevel("LOGIN_FAIL_LOG");
	private static Level MODULE_INFO = Level.getLevel("MODULE_LOG");
	private static Level URL_INFO = Level.getLevel("URL_LOG");
	private static Level ERROR = Level.getLevel("ERROR");
	/*
	public static void saveAudit(String userId, String tableName, List<String> recordId, String action, List<String> newValue)
	{
		int i = recordId.size();
		
		for (int j=0; j<i; j++)
		{
			if (userId == null)
				userId = AccountDatabaseConstants.USER_SYSTEM;
			
			StringBuilder s = new StringBuilder(2048);
			s.append("\"")
			.append(userId)
			.append("\",\"")
			.append(tableName)
			.append("\",\"")
			.append(recordId.get(i))
			.append("\",\"")
			.append(action)
			.append("\",\"")
			.append(newValue.get(i))
			.append("\"");

			logger.fatal(s.toString());
		}
	}
	*/

}
