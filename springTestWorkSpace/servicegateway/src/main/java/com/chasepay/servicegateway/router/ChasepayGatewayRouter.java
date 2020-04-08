package com.chasepay.servicegateway.router;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.chasepay.servicegateway.constants.ServiceGatewayConstants;
import com.chasepay.servicegateway.filter.ServiceFilter;
import com.chasepay.servicegateway.filter.ServiceRequestFilter;
import com.chasepay.servicegateway.filter.ServiceResponseFilter;
import com.chasepay.servicegateway.filter.ServiceRouterFilter;

public class ChasepayGatewayRouter {
	
	private ConcurrentHashMap<String,List<ServiceFilter>> filtersMap = new ConcurrentHashMap<String,List<ServiceFilter>>();
	
	public ChasepayGatewayRouter()
	{
		init();
	}
	
	private void init()
	{
		List<ServiceFilter> requestFilters = new ArrayList<ServiceFilter>();
		ServiceFilter requestFilter = new ServiceRequestFilter();
		requestFilters.add(requestFilter);
		
		List<ServiceFilter> routerFilters = new ArrayList<ServiceFilter>();
		ServiceFilter routerFilter = new ServiceRouterFilter();
		routerFilters.add(routerFilter);
		
		List<ServiceFilter> responseFilters = new ArrayList<ServiceFilter>();
		ServiceFilter responseFilter = new ServiceResponseFilter();
		responseFilters.add(responseFilter);
		
		filtersMap.put(ServiceGatewayConstants.KEY_REQUEST_ROUTER, requestFilters);
		filtersMap.put(ServiceGatewayConstants.KEY_ROUTER, routerFilters);
		filtersMap.put(ServiceGatewayConstants.KEY_RESPONSE_ROUTER, responseFilters);
	}
	
	
	
	public void requestRouter() throws Exception
	{
		
	}
	
	
	public void router() throws Exception
	{
		
	}
	
	
	public void responseRouter() throws Exception
	{
		
	}
	
	

}
