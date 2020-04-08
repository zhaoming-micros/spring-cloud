package com.chasepay.databse.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseAuditLog {

	static Logger logger = LogManager.getLogger(DatabaseAuditLog.class);
	private static final int TABLE_LEN = 30;
	private static final int USER_LEN = 50;
	private static final int SQL_LEN = 100;
	private static final int ACTION_LEN = 10;
	private static final int KEY_LEN = 50;
	
	public static final int INSERT = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	
	public void log(String tableName, String user, int function, String key, String sql, String comment)
	{
		StringBuilder s = new StringBuilder(256);
		s.append(addTailSpace(tableName, TABLE_LEN))
		.append(" ")
		.append(getFunction(function))
		.append(addTailSpace(key, KEY_LEN))
		.append(addTailSpace(sql, SQL_LEN))
		.append(comment)
		.append(" ")
		.append(user);
		
		logger.fatal(s.toString());
	}
	
	private String getFunction(int function)
	{
		if (function == INSERT)
			return "Insert  ";
		if (function == UPDATE)
			return "Update  ";
		if (function == DELETE)
			return "Delete  ";

		return "Unknown ";
	}
	
	private String addTailSpace(String t, int len)
	{
		if (t == null)
			return addTailSpace1("", len);
		
		if (t.length()>len)
			return t.substring(0,len);
		
		return addTailSpace1(t, len);
	}
	
	private String addTailSpace1(String t, int len)
	{
		StringBuilder s = new StringBuilder(len);
		s.append(t);
		int len1 = t.length();
		for (int i=len1; i<len; i++)
		{
			s.append(" ");
		}
		
		return s.toString();
	}
}
