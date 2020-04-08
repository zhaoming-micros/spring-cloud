package com.chasepay.utilities.entity;

public class Attribute_Def {

	public int getAttribute_def_id() {
		return attribute_def_id;
	}
	public void setAttribute_def_id(int attribute_def_id) {
		this.attribute_def_id = attribute_def_id;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public int getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(int attribute_type) {
		this.attribute_type = attribute_type;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public int getType2() {
		return type2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	private int attribute_def_id;
	private int data_type; /* int, string, long string (multiple lines), date, boolean, time, list, class, image, address */ 
	private int attribute_type; /* entity physical, activity...*/
	private String lang; /* English: en, Chinese: cn...*/
	private int type2;  /* for entity, entity_group, or both */
	private String authorization; /* like unix authorization bit own_group_others*/
}
