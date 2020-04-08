package com.chasepay.utilities.entity;

import java.util.List;

public class Entity_Def {

	public String getEntity_def_id() {
		return entity_def_id;
	}
	public void setEntity_def_id(String entity_def_id) {
		this.entity_def_id = entity_def_id;
	}
	public List<String> getEntity_attribute_def_list() {
		return entity_attribute_def_list;
	}
	public void setEntity_attribute_def_list(List<String> entity_attribute_def_list) {
		this.entity_attribute_def_list = entity_attribute_def_list;
	}
	private String entity_def_id; /* PO, Product Category...*/
	private List<String> entity_attribute_def_list;
}
