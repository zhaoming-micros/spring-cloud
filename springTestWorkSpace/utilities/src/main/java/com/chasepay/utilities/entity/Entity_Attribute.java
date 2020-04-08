package com.chasepay.utilities.entity;

public class Entity_Attribute {

	
	/*
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_group_id() {
		return user_group_id;
	}
	public void setUser_group_id(int user_group_id) {
		this.user_group_id = user_group_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	*/
	public String getAttribute_value() {
		return attribute_value;
	}
	public void setAttribute_value(String attribute_value) {
		this.attribute_value = attribute_value;
	}

	public String getAttribute_def_id() {
		return attribute_def_id;
	}
	public void setAttribute_def_id(String attribute_def_id) {
		this.attribute_def_id = attribute_def_id;
	}
	public String getAttribute_id() {
		return attribute_id;
	}
	public void setAttribute_id(String attribute_id) {
		this.attribute_id = attribute_id;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	/*
	public String getEntity_group_id() {
		return entity_group_id;
	}
	public void setEntity_group_id(String entity_group_id) {
		this.entity_group_id = entity_group_id;
	}
	*/
	public String getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}
	
	public String getAttribute_value2() {
		return attribute_value2;
	}
	public void setAttribute_value2(String attribute_value2) {
		this.attribute_value2 = attribute_value2;
	}

	private String attribute_def_id;
	private String attribute_id;
	private String entity_id;
	private String attribute_type; //0 - entity_id 1 - entity_group id 2 - entity_relation_id


	//private String entity_group_id;
	private String attribute_value;
	private String attribute_value2;
	public String getAttribute_value3() {
		return attribute_value3;
	}
	public void setAttribute_value3(String attribute_value3) {
		this.attribute_value3 = attribute_value3;
	}
	public String getAttribute_value4() {
		return attribute_value4;
	}
	public void setAttribute_value4(String attribute_value4) {
		this.attribute_value4 = attribute_value4;
	}
	public String getAttribute_data_type() {
		return attribute_data_type;
	}
	public void setAttribute_data_type(String attribute_data_type) {
		this.attribute_data_type = attribute_data_type;
	}

	private String attribute_value3;
	private String attribute_value4;
	
	private String attribute_data_type;
	
	/*
	private int user_id;
	private int user_group_id;
	private String date;
	private String time;
	private int status;
	*/

}
