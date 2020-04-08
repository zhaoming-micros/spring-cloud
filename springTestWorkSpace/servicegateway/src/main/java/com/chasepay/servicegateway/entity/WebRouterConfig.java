package com.chasepay.servicegateway.entity;

public class WebRouterConfig {
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String key;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	private String url;
	
	private String scope;

}
