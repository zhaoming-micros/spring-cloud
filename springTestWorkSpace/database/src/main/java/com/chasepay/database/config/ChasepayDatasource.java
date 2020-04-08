package com.chasepay.database.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.database.constants.DatabaseConstant;
import com.chasepay.databse.entity.ChasepayBasicDatasource;


public class ChasepayDatasource {
	
	private static Logger logger = LogManager.getLogger(ChasepayDatabaseConnection.class);
	private static volatile ChasepayDatasource instance ;
	private static Map<String, ChasepayBasicDatasource> dataSourceHt = new ConcurrentHashMap<String, ChasepayBasicDatasource>(); 
	
	private ChasepayDatasource()
	{
		
	}
	
	public static ChasepayDatasource getInstance()
	{
		if(null==instance)
		{
			synchronized(ChasepayDatasource.class)
			{
				if(null==instance)
				{
					init();
					instance = new ChasepayDatasource();
				}
			}
		}
		return instance;
	}
	
	private static void init()
	{
		Map<String, Map<String, String>> dbParameters = DatabaseSourceReader.getInstance().getParameters();
		if (dbParameters == null)
		{
			logger.error("fail to reader database config file.datasource is null");
			return;
		}
		
		Iterator<String> i = dbParameters.keySet().iterator();
		
		while (i.hasNext())
		{
			String key = i.next();
			Map<String,String> databaseMap = dbParameters.get(key);
			ChasepayBasicDatasource chasepayBasicDatasource = new ChasepayBasicDatasource();
			BasicDataSource basicDataSource = new BasicDataSource();
			if (databaseMap.get(DatabaseConstant.DataBaseDriverClass)==null)
				basicDataSource.setDriverClassName(DatabaseConstant.MYSQL_DRIVER_CLASS);
			else
				basicDataSource.setDriverClassName(databaseMap.get(DatabaseConstant.DataBaseDriverClass));
			
			int initialSize = DatabaseConstant.INIT_CONN;
			int maxTotal = initialSize * 3;
			int minIdle = initialSize / 2;
			
			try {
				String tmp = databaseMap.get(DatabaseConstant.DatabasePoolInitSize);
				if (tmp!=null && tmp.matches("[0-9]{2,4}"))
				{	
					initialSize = Integer.parseInt(tmp);
					maxTotal = initialSize * 3;
					minIdle = initialSize / 2;
				}
				
				tmp = databaseMap.get(DatabaseConstant.DatabaseUser);
				if (tmp == null)
				{
					logger.error("databaseMap error 1");
					continue;
				}
				basicDataSource.setUsername(tmp);
				
				tmp = databaseMap.get(DatabaseConstant.DatabasePassword);
				if (tmp == null)
				{
					logger.error("databaseMap error 2");
					continue;
				}
				basicDataSource.setPassword(tmp);
				
				//String host = databaseMap.get(DatabaseConstant.DatabaseHost);
				//String port = databaseMap.get(DatabaseConstant.DatabasePort);
				
				String url = databaseMap.get(DatabaseConstant.DatabaseUrl);
				String name = databaseMap.get(DatabaseConstant.DatabaseName);

				if (url== null || name == null)
				{
					logger.error("database url is null .");
					continue;
				}
				
				basicDataSource.setUrl(url);
				
				chasepayBasicDatasource.setKey(key);
				chasepayBasicDatasource.setDbName(name);
				
				
				String relatonTableStr1 = databaseMap.get(DatabaseConstant.DatabaseRelationTables1);
				if(null!=relatonTableStr1)
				{
					List<String> list = getRelationTableList(relatonTableStr1);
					chasepayBasicDatasource.setRelationTables1(list);
				}
				
				String relatonTableStr2 = databaseMap.get(DatabaseConstant.DatabaseRelationTables2);
				if(null!=relatonTableStr2)
				{
					List<String> list = getRelationTableList(relatonTableStr2);
					chasepayBasicDatasource.setRelationTables2(list);
				}
			}
			catch(Exception e)
			{
				logger.error("databaseMap error 4", e);
				continue;
			}
			basicDataSource.setInitialSize(initialSize);
			basicDataSource.setMaxTotal(maxTotal);
			basicDataSource.setMinIdle(minIdle);
			chasepayBasicDatasource.setBasicDataSource(basicDataSource);
			
			logger.info("Init DB OK");
			ChasePayDatabaseMetaData.getInstsance().init(chasepayBasicDatasource);
			dataSourceHt.put(key, chasepayBasicDatasource);
		}
	}
	
	
	public ChasepayBasicDatasource getDataSource(String key)
	{
		return dataSourceHt.get(key);
	}
	
	
	private static List<String> getRelationTableList(String str)
	{
		String[] split = str.split(DatabaseConstant.FLAG_SEP_RELATION_TABLE);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < split.length; i++) 
		{
			list.add(split[i].trim());
		}
		return list;
	}

}
