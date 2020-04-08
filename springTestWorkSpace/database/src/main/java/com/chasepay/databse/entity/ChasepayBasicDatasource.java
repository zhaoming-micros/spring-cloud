package com.chasepay.databse.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class ChasepayBasicDatasource {
	
	private String key;
	
	private String dbName;
	
	private BasicDataSource basicDataSource;
	
	private List<String> relationTables1 = new ArrayList<String>();
	
	private List<String> relationTables2 = new ArrayList<String>();

	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}

	public List<String> getRelationTables1() {
		return relationTables1;
	}

	public void setRelationTables1(List<String> relationTables1) {
		this.relationTables1 = relationTables1;
	}
	
	public List<String> getRelationTables2() {
		return relationTables2;
	}

	public void setRelationTables2(List<String> relationTables2) {
		this.relationTables2 = relationTables2;
	}

}
