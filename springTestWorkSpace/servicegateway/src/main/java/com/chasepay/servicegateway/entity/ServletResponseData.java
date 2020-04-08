package com.chasepay.servicegateway.entity;





public class ServletResponseData {

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ServletRespData getData() {
		return data;
	}
	public void setData(ServletRespData data) {
		this.data = data;
	}

	public String toString()
	{
		return null;
	}
	


	private boolean success = false;
	private String errorCode;
	private ServletRespData data;
	private String errorMsg;


}
