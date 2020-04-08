package com.chasepay.servicegateway.context;


import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.configuration.ChasePayResourceBundle;
import com.chasepay.configuration.SystemBaseHome;



public class AccountingSessionContextListener implements ServletContextListener {

	static Logger logger = LogManager.getLogger(AccountingSessionContextListener.class);
	static final String VERSION = "2.0.0 (1) build 20190401";

	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Accounting session shutdown ");
	}


	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext context = arg0.getServletContext();
		String realpath = context.getRealPath("/");
		String filesep = System.getProperty("file.separator");
		if(realpath!=null && (!realpath.endsWith(filesep)))
			realpath = realpath + filesep;
		SystemBaseHome.getInstance().setSystemHome(realpath);
		
		ChasePayResourceBundle.getInstance();
		
		logger.info("Start accounting session context listener "+VERSION+ "...");
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		AccountingSessionContextListener.logger = logger;
	}

	public static String getVersion() {
		return VERSION;
	}
}
