package com.chasepay.servicegateway.servlet.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.servicegateway.entity.ServletResponseData;
import com.chasepay.utilities.data.ServletRequestData;
import com.google.gson.Gson;

public abstract class AccountingRemoteServiceAbstractServlet extends HttpServlet {

	static final long serialVersionUID = 20170816L;
	
	static Logger logger = LogManager.getLogger(AccountingRemoteServiceAbstractServlet.class);
	
	private ServletUtil util = new ServletUtil();

	protected ServletRequestData getRequestString(HttpServletRequest request) throws IOException, Exception
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader =  request.getReader() ; 
			char[] charBuffer = new char[128];
			int bytesRead;
			while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 )
				sb.append(charBuffer, 0, bytesRead);

		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("getRequestString", ex);
			return null;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {

				}
			}
		}


		try {
			Gson gson = new Gson();
			return gson.fromJson(sb.toString(), ServletRequestData.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("convertRequestToHt", e);
			return null;
		}

	}
	
	protected abstract String doPostInternal(HttpServletRequest request, ServletRequestData requestData, Map<String,String> requestHeaderHt) 
			throws ServletException, IOException, Exception;
	
	protected abstract String doGetInternal(HttpServletRequest request, Map<String, String> requestHeaderHt) throws ServletException, IOException;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			Map<String,String> inHt = util.setRequestHeaderInfo(request);
			
			logger.info("in doGet");
			
			
			String resp = doGetInternal(request, inHt);

			
			util.setDoGetResponse(response, resp);
		}
		catch(Exception e)
		{
			logger.error("Servlet doGet exception", e);
			ServletResponseData data = new ServletResponseData();
			data.setSuccess(false);
			data.setErrorCode(AccountingErrorConstant.CODE_2044);

			util.setResponse(response, data.toString());
			
			logger.error("Servlet doGet exception", e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			Map<String,String> inHt = util.setRequestHeaderInfo(request);
			
			
			
	
			
			ServletRequestData requestData = getRequestString(request);
			
			String resp = doPostInternal(request, requestData, inHt);

			

			
			
			util.setResponse(response, resp);
		}
		catch(Exception e)
		{
			ServletResponseData data = new ServletResponseData();
			data.setSuccess(false);
			data.setErrorCode(AccountingErrorConstant.CODE_2045);

			util.setResponse(response, data.toString());
			
			logger.error("Servlet doPost exception", e);
		}
	}
	


	private boolean fullDebug = true;


	

	protected String redirectPage = null;
}
