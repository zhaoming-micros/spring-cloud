package com.chasepay.servicegateway.servlet.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.webutil.AccountServletConstant;
import com.chasepay.servicegateway.constants.GatewayUrlUtil;
import com.chasepay.servicegateway.context.AccountingSessionConstant;

public class ServletUtil {

	private static Logger logger = LogManager.getLogger(ServletUtil.class);

	public void setRequestDoGetHt(HttpServletRequest request, Map<String,String> ht) throws UnsupportedEncodingException
	{
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
	}
	
	Map<String,String> setRequestHeaderInfo(HttpServletRequest request)
	{
		try 
		{
			request.setCharacterEncoding("utf-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			logger.error("encode request error!", e);
		}
		
		Map<String,String> requestHeaderHt = new HashMap<String,String>();
		setRequestInfo(request, requestHeaderHt);
		
		if (fullDebug)
			logger.info("requestHeaderHt="+requestHeaderHt);

		return requestHeaderHt;
	}

	void setResponse(HttpServletResponse response, String resp) throws IOException {		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(resp);
		out.flush();
		
		if (fullDebug)
			logger.info("new Servlet response=["+ resp+"]");
	}
	
	void setDoGetResponse(HttpServletResponse response, String resp)throws IOException {
		
		if (fullDebug)
			logger.info("doGet servlet response=["+ resp+"]");
		response.sendRedirect(GatewayUrlUtil.getUrl()+resp);
	}
	
	
	private void setRequestInfo(HttpServletRequest request, Map<String,String> inHt)
	{
		StringBuilder s1 = new StringBuilder(256);
		Enumeration<String> headerNames = request.getHeaderNames();
		
		while(headerNames.hasMoreElements()) {

			String headerName = (String)headerNames.nextElement();
			String value = request.getHeader(headerName);
			
			s1.append(headerName)
			.append("=")
			.append(value)
			.append("|");
			
			if (AccountingSessionConstant.ACCEPT_LANGUAGE.equals(headerName))
			{
				inHt.put(AccountingSessionConstant.ACCEPT_LANGUAGE,  value);
			}
			

			
		}

		String reqHeader = s1.toString();
		if (reqHeader.length() > AccountServletConstant.MAX_REQ_LEN)
		{
			logger.warn("Req header too long : ["+reqHeader+"]");
			reqHeader = reqHeader.substring(0, AccountServletConstant.MAX_REQ_LEN);
		}

		inHt.put(AccountServletConstant.HEADER, reqHeader);
		inHt.put(AccountServletConstant.REMOTE_INFO, request.getRemoteHost()+":"+request.getRemoteAddr()+":"+request.getRemotePort());

		String date = new SimpleDateFormat(AccountServletConstant.DATE_FORMAT).format(new Date());
		inHt.put(AccountServletConstant.DATE,  date.substring(0,8));
		inHt.put(AccountServletConstant.TIME,  date.substring(8,14));
		inHt.put(AccountServletConstant.TIMEZONE,  date.substring(15));
		
	}
	
	private boolean fullDebug = true;
	
}
