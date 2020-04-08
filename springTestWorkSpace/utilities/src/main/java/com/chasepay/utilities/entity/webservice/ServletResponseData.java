package com.chasepay.utilities.entity.webservice;

import com.google.gson.Gson;

public class ServletResponseData {

	private boolean success = false;
	private String errorCode;
	private String errorMsg;
	private Object data;

	
	
	public boolean isSuccess() {
		return success;
	}



	public void setSuccess(boolean success) {
		this.success = success;
	}



	public String getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMsg() {
		return errorMsg;
	}



	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	public Object getData() {
		return data;
	}



	public void setData(Object data) {
		this.data = data;
	}



	public String toString()
	{
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
	
	
	
	


}
