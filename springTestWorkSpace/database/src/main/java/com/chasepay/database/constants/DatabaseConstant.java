package com.chasepay.database.constants;

import java.io.File;

public class DatabaseConstant {
	
	public static final String DATABASE_FILE = "config"+File.separator+"database"+File.separator+"DatabaseProperty.properties";
	
	public static final String DATASOURCE_HEADER= "{DATASOURCE_";
	
	public static final int SQL_PARAMETER_INT = 0;
	public static final int SQL_PARAMETER_DECIMAL = 2;
	public static final int SQL_PARAMETER_STRING = 1;
	public static final int SQL_PARAMETER_OTHERS = 3;
	
	public static String DatabaseProperty = "DatabaseProperty";
	//public static String DatabaseHost = "DatabaseHost";
	//public static String DatabasePort = "DatabasePort";
	public static String DatabaseUrl = "DatabaseUrl";
	
	public static String DatabaseName = "DatabaseName";
	public static String DatabaseUser = "DatabaseUser";
	public static String DatabasePassword = "DatabasePassword";
	public static String DataBaseDriverClass = "DatabaseDriverClass";
	
	public static String DatabasePoolInitSize = "DatabasePoolInitSize";
	public static String DatabaseRelationTables1 = "DatabaseRelationTables1";
	public static String DatabaseRelationTables2 = "DatabaseRelationTables2";
	public static String SYSTEM_URL = "URL";
	
	public static String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	public static final int MYSQL_DUPLICATE = 1062;
	
	public static final int MYSQL_TABLE_EXIST = 1050;
	
	public static final int INIT_CONN = 50;
	public static final int DEFULT_STANDALONE_CONN = 10;
	
	public static String FLAG_SEP_RELATION_TABLE = "[|]";
	
	public static String DATASOURCE_KEY = "DATASOURCE_KEY";
	
	public static String NO_RECORD = "-1";
	
	public static final String INSERT_GET_ID_SQL = "SELECT LAST_INSERT_ID()";
	
	
    public static final boolean ENABLE_SQL = true;
    
    public static final String[] CONDITIONS = {
    		"in",
    		">",
    		">=",
    		"<",
    		"<=",
    		"is",
    		"between",
    		"like",
    		"not in",
    		"!="
    	};
    
	public static final String CONDITIONS2 = "(";
}
