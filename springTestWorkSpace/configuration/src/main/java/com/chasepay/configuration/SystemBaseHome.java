package com.chasepay.configuration;

import java.io.File;

import com.chasepay.configuration.constants.SystemConstant;

public class SystemBaseHome {

	private static String homeDirectory = null;
	private static volatile SystemBaseHome instance = null;
	

	private SystemBaseHome()
	{
		
	}
	
	public static SystemBaseHome getInstance()
	{
		if (instance == null)
		{
			
			synchronized (SystemBaseHome.class)
			{
				if (instance == null)
				{
					homeDirectory = System.getenv("GENIESELLER_HOME");
					
					if (homeDirectory == null)
					{
						homeDirectory = System.getProperty("user.dir");
					}
						
					if (!homeDirectory.endsWith(File.separator))
					{
						homeDirectory = homeDirectory + File.separator;
					}	
					
					instance = new SystemBaseHome();
				}
			}
		}
		
		return instance;
	}
	
	public void setSystemHome(String dir)
	{
		homeDirectory = dir;
		
		if (homeDirectory.endsWith(File.separator))
		{
			
		}
		else
			homeDirectory = homeDirectory + File.separator;
	}
	
	public String getBaseHome()
	{
		return homeDirectory;
	}
	
	
	public String getConfigPropertiesHome()
	{
		if (homeDirectory.endsWith(File.separator))
			return homeDirectory +  "config" + File.separator + SystemConstant.propertiesDirectory + File.separator;

		return homeDirectory + File.separator+"config" + File.separator + SystemConstant.propertiesDirectory + File.separator;
			
	}
	
	public String getPropertiesHome()
	{
		if (homeDirectory.endsWith(File.separator))
			return homeDirectory +  SystemConstant.webinfDirectory + File.separator + SystemConstant.propertiesDirectory + File.separator;

		return homeDirectory + File.separator+ SystemConstant.webinfDirectory + File.separator + SystemConstant.propertiesDirectory + File.separator;
			
	}
	
	
	public String getUploadFileDirectory()
	{
		return homeDirectory + SystemConstant.uploadFileDirectory + File.separator;
	}
	
	public String getReportTemplateDirectory()
	{
		if (homeDirectory.endsWith(File.separator))
			//return homeDirectory +  webinfDirectory + File.separator + reportDirectory + File.separator + reportTemplateDirectory + File.separator;
			return homeDirectory +  SystemConstant.reportDirectory + File.separator  + SystemConstant.reportTemplateDirectory + File.separator;

		return homeDirectory + File.separator +  SystemConstant.reportDirectory + File.separator  + SystemConstant.reportTemplateDirectory + File.separator;
	}
	
	public String getReportOutputDirectory()
	{
		if (homeDirectory.endsWith(File.separator))
			return homeDirectory +  SystemConstant.reportDirectory + File.separator + SystemConstant.reportOutputDirectory + File.separator;

		return homeDirectory + File.separator+ SystemConstant.reportDirectory + File.separator + SystemConstant.reportOutputDirectory + File.separator;
	}
	
	public String getLineSeperator()
	{
		return System.getProperty("line.separator");
	}
}
