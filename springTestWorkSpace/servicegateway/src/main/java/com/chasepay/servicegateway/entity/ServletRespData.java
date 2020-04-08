package com.chasepay.servicegateway.entity;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ServletRespData {
	
	
	public WebRouterConfig[] getConfig() {
		return config;
	}
	public void setConfig(WebRouterConfig[] config) {
		this.config = config;
	}
	
	public Map<String, String> getHt() {
		return ht;
	}
	public void setHt(Map<String, String> ht) {
		this.ht = ht;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getDefault_company_id() {
		return default_company_id;
	}
	public void setDefault_company_id(String default_company_id) {
		this.default_company_id = default_company_id;
	}
	public String getDefault_company_name() {
		return default_company_name;
	}
	public void setDefault_company_name(String default_company_name) {
		this.default_company_name = default_company_name;
	}
	
	public String getAccount_chart() {
		return account_chart;
	}
	public void setAccount_chart(String account_chart) {
		this.account_chart = account_chart;
	}
	public void setFull_account_chart(String full_account_chart) {
		this.full_account_chart = full_account_chart;
	}
	public String getFull_account_chart() {
		return full_account_chart;
	}
	public String getPdfname() {
		return pdfname;
	}
	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}
	public String getHt2Json() {
		if (ht2!=null && ht2.size()>0)
		{
			Gson gs = new Gson(); 
			JsonElement obj = null;
			obj = gs.toJsonTree(ht2);
			return obj.toString();
		}
		
		return null;
	}
	
	
	public void setHt2(Map<String, List<List<String>>> ht2) {
		this.ht2 = ht2;
	}
	
	public String getHt3Json() {
		if (ht3!=null && ht3.size()>0)
		{
			Gson gs = new Gson(); 
			JsonElement obj = null;
			obj = gs.toJsonTree(ht3);
			return obj.toString();
		}
		
		return null;
	}
	
	
	public void setHt3(Map<String, List<List<String>>> ht3) {
		this.ht3 = ht3;
	}
	
	public String getHt4Json() {
		if (ht4!=null && ht4.size()>0)
		{
			Gson gs = new Gson(); 
			JsonElement obj = null;
			obj = gs.toJsonTree(ht4);
			return obj.toString();
		}
		
		return null;
	}
	
	
	public void setHt4(Map<String, List<String>> ht4) {
		this.ht4 = ht4;
	}
	
	
	
	
	
	private String first_name;
	private String last_name;
	private String email;
	private String mobile;
	
	private String login_id;
	private String default_company_id;
	private String default_company_name;
	private String pdfname;
	private String account_chart;
	private String full_account_chart;

	private String menu;

	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}



	private WebRouterConfig[] config;
	private Map<String,String> ht;
	private List<String> list;

	private Map<String, List<List<String>>> ht3;

	private Map<String, List<List<String>>> ht2;
	
	private Map<String, List<String>> ht4;
	
	private Gson gson = new Gson();
	
	private String jsonStr;
	private List<String> listJson;
	private String jsonListOfMap;
	private String toJson;
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public List<String> getListJson() {
		return listJson;
	}
	public void setListJson(List<String> listJson) {
		this.listJson = listJson;
	}
	public String getJsonListOfMap() {
		return jsonListOfMap;
	}
	public void setJsonListOfMap(List<Map<String, String>> jsonListOfMap) {
		this.jsonListOfMap = gson.toJson(jsonListOfMap);
	}
	public String getToJson() {
		return this.toJson;
	}
	public void setToJson(Map<String, Object> toJson) {
		this.toJson = gson.toJson(toJson);
	}

	String pageCount;
	String currentPage;
	String currentHtml;

	
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getCurrentHtml() {
		return currentHtml;
	}
	public void setCurrentHtml(String currentHtml) {
		this.currentHtml = currentHtml;
	}
	
}
