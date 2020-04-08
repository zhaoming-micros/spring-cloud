package com.chasepay.servicegateway.filter;

public interface ServiceFilter {
	
	public int getOrder();
	
	public String getType();
	
	public void execute();

}
