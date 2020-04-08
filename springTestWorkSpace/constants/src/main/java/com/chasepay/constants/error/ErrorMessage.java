package com.chasepay.constants.error;

public class ErrorMessage {

	public ErrorMessage(String type)
	{
		this.type = type;
		this.code = AccountingErrorConstant.CODE_0000;
	}
	
	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCode(String code, String parameter1) {
		this.code = code;
	}
	
	public void setCode(String code, String parameter1, String parameter2) {
		this.code = code;
	}
	
	public String getMessage() {
		if (message == null)
			return "Error : "+type+code;
		
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getE() {
		return e;
	}
	public void setE(Exception e) {
		this.e = e;
	}
	public String getWebMessage() {
		if (webMessage == null)
			return "Error : "+type+code;
		
		return webMessage;
	}
	public void setWebMessage(String webMessage) {
		this.webMessage = webMessage;
	}

	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	private String type;
	private String code = AccountingErrorConstant.CODE_0000;
	private String message;
	private Exception e;
	private String webMessage;
	
	private String level = AccountingErrorConstant.ERROR_LEVEL_INFO;
	
	public String toString()
	{
		StringBuilder s = new StringBuilder(128);
		s.append("Error ")
		.append(type)
		.append(".")
		.append(code)
		.append(" ")
		.append(level)
		.append(" [")
		.append(message==null?"":message)
		.append("][")
		.append(webMessage==null?"":webMessage)
		.append("]");
		
		if (e == null)
			return s.toString();
		
		s.append(e.getMessage()).append("\r\n");
		StackTraceElement[] st = e.getStackTrace();
		for (StackTraceElement s1:st)
		{
			s.append(s1.getFileName()).append(".").append(s1.getLineNumber())
			.append(".").append(s1.getMethodName())
			.append("\r\n");
		}
		return s.toString();
	}

}
