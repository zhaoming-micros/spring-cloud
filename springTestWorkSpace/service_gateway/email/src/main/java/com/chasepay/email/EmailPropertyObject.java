package com.chasepay.email;

public class EmailPropertyObject {
	
	private String host;
	private String port;
	private String from;
	private String from2;
	private String token;
	private String protocol;
	private String psswd;
	private String notifySuccStmt;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPsswd() {
		return psswd;
	}
	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}
	public String getFrom2() {
		return from2;
	}
	public void setFrom2(String from2) {
		this.from2 = from2;
	}	
	public String getNotifySuccStmt() {
		return notifySuccStmt;
	}
	public void setNotifySuccStmt(String notifySuccStmt) {
		this.notifySuccStmt = notifySuccStmt;
	}
	@Override
	public String toString() {
		return "[host=" + host + "\n" + 
				", port=" + port + "\n" +
				", from=" + from + "\n" + 
				", token=" + token +  "\n" +
				", protocol=" + protocol + "]";
	}	
}
