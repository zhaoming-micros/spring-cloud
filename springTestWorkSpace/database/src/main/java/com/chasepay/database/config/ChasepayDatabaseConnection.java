package com.chasepay.database.config;

import java.sql.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.databse.entity.ChasepayBasicDatasource;



public class ChasepayDatabaseConnection {

	private static Logger logger = LogManager.getLogger(ChasepayDatabaseConnection.class);
	
	public static Connection getConnection(String key)
	{
		Connection connection = null;

		ChasepayBasicDatasource chasepayBasicDatasource = ChasepayDatasource.getInstance().getDataSource(key);
		if (null==chasepayBasicDatasource)
		{
			logger.error("getConnection: "+key +" data source is  null");
			return null;
		}

		try
		{
			connection = chasepayBasicDatasource.getBasicDataSource().getConnection();
		} 
		catch (Exception e) 
		{
			logger.error(e);
			return null;
		}

		return connection;
	}	
	

}
