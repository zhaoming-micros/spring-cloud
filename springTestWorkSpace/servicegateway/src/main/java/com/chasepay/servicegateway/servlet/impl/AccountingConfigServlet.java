package com.chasepay.servicegateway.servlet.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.constants.webutil.AccountServletConstant;
import com.chasepay.servicegateway.context.AccountingSessionConstant;
import com.chasepay.servicegateway.entity.ServletResponseData;

import com.chasepay.utilities.data.ServletRequestData;



public class AccountingConfigServlet extends AccountingRemoteServiceAbstractServlet {

	static final long serialVersionUID = 20180126L;
	static Logger logger = LogManager.getLogger(AccountingConfigServlet.class);
	
	protected String doPostInternal(HttpServletRequest request, ServletRequestData requestData, Map<String,String> requestHeaderHt) 
			throws ServletException, IOException, Exception
	{
		
		
		
		if (AccountServletConstant.SERVLET_REQUEST_TYPE_BUSINESS.equals(requestData.getCPAcct_Srv_Type()))
		{
			if (requestData.getCPAcct_Srv_Data() == null)
			{
				logger.error("Business request, no request data");
				ServletResponseData d = new ServletResponseData();
				d.setSuccess(false);
				d.setErrorCode(AccountingErrorConstant.CODE_2051);
				return d.toString();
			}

			

			
			
			
			Map<String,String> input = requestData.getCPAcct_Srv_Data();
			//validate input
			
			
			
			
			
			
			requestData.getCPAcct_Srv_Data().put(AccountServletConstant.SERVLET_REQUEST_TYPE, requestData.getCPAcct_Srv_Type());
			requestData.getCPAcct_Srv_Data().put(AccountServletConstant.SERVLET_REQUEST_SUB_TYPE, requestData.getCPAcct_Srv_SubType());
			
			ServletResponseData d = null;
			if(requestData.isEntity())
			{
                logger.info("businessProcess   entity");
				

				
				logger.info("businessProcess respones="+d.toString());
				
			}
			else
			{

				
				logger.info("businessProcess respones="+d.toString());
			}

			return d.toString();

		}

		ServletResponseData d = new ServletResponseData();
		d.setSuccess(false);
		d.setErrorCode(AccountingErrorConstant.CODE_2054);
		return d.toString();
		
	}
	
	
	
	protected String doGetInternal(HttpServletRequest request, Map<String, String> reqHeaderHt) throws ServletException, IOException
	{		
		String requestType = request.getParameter(URLEncoder.encode(AccountServletConstant.SERVLET_REQUEST_TYPE, StandardCharsets.UTF_8.toString()));
		String requestSubtype = request.getParameter(URLEncoder.encode(AccountServletConstant.SERVLET_REQUEST_SUB_TYPE, StandardCharsets.UTF_8.toString()));
		
		
		
		Map<String, String> ht = new HashMap<>();
	    Map<String, String[]> params = request.getParameterMap();

	    Iterator<String> i = params.keySet().iterator();

	    while (i.hasNext())
	    {
	    	String key = URLDecoder.decode((String) i.next(), StandardCharsets.UTF_8.toString());
	    	String value = Arrays.stream((String[]) params.get(key)).map(elem -> {
				try {
					return URLDecoder.decode(elem, StandardCharsets.UTF_8.toString());
				} catch (UnsupportedEncodingException e) {
					return "";
				}
			}).collect(Collectors.joining(","));
	    	ht.put(key, value);
	    }
	    
	    logger.info("request para => "+ht);
	    

		
		String d = businessProcessDoGet(null, ht, reqHeaderHt);		
		return d;
		
	}
	
	private String businessProcessDoGet(String className, Map<String, String> ht, Map<String, String> reqHeaderHt)
	{

		try{

			Class<?> c = Class.forName(PACKAGE_DOGET+className);
			Object o = c.newInstance();

			Method method = c.getDeclaredMethod("process", Map.class, Map.class);
			return (String)method.invoke(o, ht, reqHeaderHt);

		}catch (Exception e) {

			e.printStackTrace();
			logger.error("Invoke businessProcessDoGet exp: " + e.toString(), e);
			ServletResponseData d = new ServletResponseData();
			d.setSuccess(false);
			d.setErrorCode(AccountingErrorConstant.CODE_2053);
			return AccountServletConstant.APP_HTML;
		}
	}

	private static final String PACKAGE = "com.chasepay.accounting.business.impl.";
	private static final String PACKAGE_DOGET = "com.chasepay.accounting.business.doget.impl.";
}
