package com.chasepay.database.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.configuration.SystemBaseHome;
import com.chasepay.database.constants.DatabaseConstant;



public class DatabaseSourceReader {

	private static Logger logger = LogManager.getLogger(DatabaseSourceReader.class);

	private static DatabaseSourceReader instance = null;
	private static Map<String, Map<String,String>> dbParameters = new ConcurrentHashMap<String, Map<String,String>>();
	
	public static DatabaseSourceReader getInstance()
	{
		if (instance == null)
		{
			init();
			instance = new DatabaseSourceReader(); 
		}
		return instance;
	}
	
	private static void init()
	{
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader(SystemBaseHome.getInstance().getBaseHome()+DatabaseConstant.DATABASE_FILE));
			
			Map<String,String> ht = null;
			String line =null;
			while( (line = reader.readLine())!=null)
			{
				line = line.trim();
				if(line.length()==0)
					continue;

				if(line.startsWith("#"))
					continue;

				logger.info(line);

				if(line.startsWith(DatabaseConstant.DATASOURCE_HEADER))
				{
					ht = new HashMap<String,String>();
					String id = line.substring(DatabaseConstant.DATASOURCE_HEADER.length(), line.length()-1);
					dbParameters.put(id,  ht);
				}
				else 
				{
					String[] tokens = line.split("[=]");
					if(tokens.length==2)
					{
						ht.put(tokens[0], tokens[1]);
					}
					else if(tokens.length>2)
					{
						int indexOf = line.indexOf("=");
						String value = line.substring(indexOf+1).trim();
						ht.put(tokens[0], value);
					}

				}
			}
				
		}
		catch(IOException ioe)
		{
			logger.error("read db server exp :"+ioe);
			logger.error(ioe);
		}
		
		finally
		{
			if (reader!=null) try {reader.close();}catch(Exception ee){}
		}
			
	}
	
	public Map<String, Map<String,String>> getParameters()
	{
		return dbParameters;
	}
}
