package com.chasepay.utilities.entity.webservice;

import java.util.Map;
import com.google.gson.Gson;

public class ServletRequestData {
	
	private Map<String,String> paramterMap;
	private boolean isEntity = true ;
	private Object entity;
	
	public Map<String, String> getParamterMap() {
		return paramterMap;
	}

	public void setParamterMap(Map<String, String> paramterMap) {
		this.paramterMap = paramterMap;
	}

	public boolean isEntity() {
		return isEntity;
	}

	public void setEntity(boolean isEntity) {
		this.isEntity = isEntity;
	}


	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}



	public String toString()
	{
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
	
	
	
}
