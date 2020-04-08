package com.chasepay.email;

import java.util.List;

public class EmailObject {
	
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	private String subject;
	private String content;
	private List<String> attachements;
	
	public List<String> getAttachements() {
		return attachements;
	}
	public void setAttachements(List<String> attachements) {
		this.attachements = attachements;
	}
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public List<String> getCc() {
		return cc;
	}
	public void setCc(List<String> cc) {
		this.cc = cc;
	}
	public List<String> getBcc() {
		return bcc;
	}
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "EmailObject [to=" + to + "\n" + 
				", cc=" + cc + "\n" + 
				", bcc=" + bcc + "\n" + 
				", subject=" + subject + "\n" + 
				", content=" + content + "]";
	}
}
