package com.chasepay.email;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.configuration.SystemBaseHome;



public class EmailPropertyReader {

	static Logger logger = LogManager.getLogger(EmailPropertyReader.class);	

	private static EmailPropertyReader instance = null;

	private static EmailPropertyObject o = null;

	private EmailPropertyReader()
	{

	}
	
	public static EmailPropertyReader getInstance()
	{
		if (instance == null)
		{
			instance = new EmailPropertyReader();
			init();
		}
		return instance;
	}

	public static void init()
	{
		logger.info("Start to read "+ EmailFunctionConstant.EMAIL_PROP);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(
					SystemBaseHome.getInstance().getConfigPropertiesHome()+
					EmailFunctionConstant.EMAIL_PROP));

			String line =null;
			
			o = new EmailPropertyObject();

			while( (line = reader.readLine())!=null)
			{
				line = line.trim();
				if(line.length()==0)
					continue;

				if(line.startsWith("#"))
					continue;

				String[] tokens = line.split("=");
				if (tokens.length != 2)
					continue;
				
				tokens[0] = tokens[0].trim();
				tokens[1] = tokens[1].trim();
				
				if (EmailFunctionConstant.EMAIL_HOST.equals(tokens[0]))
				{
					o.setHost(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.EMAIL_PORT.equals(tokens[0]))
				{
					o.setPort(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.EMAIL_FROM.equals(tokens[0]))
				{
					o.setFrom(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.EMAIL_FROM2.equals(tokens[0]))
				{
					o.setFrom2(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.EMAIL_TOKEN.equals(tokens[0]))
				{
					o.setToken(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.EMAIL_PROTOCOL.equals(tokens[0]))
				{
					o.setProtocol(tokens[1]);
					continue;
				}
				
				if (EmailFunctionConstant.NOTIFY_SUCC_STMT.equals(tokens[0]))
				{
					o.setNotifySuccStmt(tokens[1]);
					continue;
				}

			}
			
			logger.info("email.properties: \n" + o.toString());

		}
		catch(IOException ioe)
		{
			logger.error("parse email properties exp :"+ioe);
			o = null;
		}
		finally
		{
			if (reader!=null) try {reader.close();}catch(Exception ee){}
		}
	}
	
	public EmailPropertyObject getEmailObject() {
		return o;
	}
}
