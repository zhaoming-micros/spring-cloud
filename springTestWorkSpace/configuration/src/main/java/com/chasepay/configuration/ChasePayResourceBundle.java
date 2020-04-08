package com.chasepay.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class ChasePayResourceBundle {

	private static Map<String, Map<String, String>> resourceBundleMap = new ConcurrentHashMap<String,Map<String,String>>();
	private static Logger logger = LogManager.getLogger(ChasePayResourceBundle.class);
	private static volatile ChasePayResourceBundle instance = null;
	
	private ChasePayResourceBundle()
	{
		
	}
	
	public static ChasePayResourceBundle getInstance()
	{
		if (instance == null)
		{
			synchronized (ChasePayResourceBundle.class) 
			{
				if (instance == null)
				{
					init();
					instance = new ChasePayResourceBundle();
				}
			}
		}
		
		return instance;
	}
	
	private static void init()
	{
		//SystemBaseHome.getInstance().setSystemHome(systemHome);
		String directory = SystemBaseHome.getInstance().getPropertiesHome();
		String logConfigurationFile = directory+"log4j2.xml";
		//ConfigurationSource  source;
		try 
		{
			Configurator.initialize(null, logConfigurationFile);
		} 
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		logger.info("Init ChasePay resouce bundle, base home =["+SystemBaseHome.getInstance().getBaseHome()+"]");
		
		final String extension = ".properties";
		final File currentDir = new File(directory);
		
		File[] files = currentDir.listFiles();
		
		for (File f:files)
		{
			try {
				
				if (!f.getName().endsWith(extension))
					continue;

				BufferedReader r = new BufferedReader(new FileReader(f));
				String line = null;
				
				Map<String, String> ht = new ConcurrentHashMap<String,String>();
				
				while ( (line=r.readLine())!=null )
				{
					if (line.startsWith("#"))
						continue;
					String[] l = line.split("[=]");
					
					if (l.length<2)
						continue;
					else if(l.length>2) {
						StringBuilder sb = new StringBuilder();
						for(int i=1; i<l.length; i++) {
							sb.append(l[i]).append("=");
						}
						sb.deleteCharAt(sb.length()-1);
						ht.put(l[0].trim(), sb.toString().trim());
					}else {
						ht.put(l[0].trim(), l[1].trim());
					}
				}
				
				r.close();
				if (ht.size()>0)
					resourceBundleMap.put(f.getName().substring(0, f.getName().length()-11), ht);
			}
			catch(Exception e)
			{
				logger.error("Read properties file exception :"+e.getMessage());
				logger.error(e);
			}
		}
	}
	
	public  Map<String, Map<String, String>> getResourceBundleMap()
	{
		return resourceBundleMap;
	}
}
