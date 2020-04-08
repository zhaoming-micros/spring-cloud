package com.chasepay.utilities.entity;

import java.util.List;

public class Entity_Group_Relation {

	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getEntity_relation_id() {
		return entity_relation_id;
	}
	public String getRelation_type() {
		return relation_type;
	}
	public void setRelation_type(String relation_type) {
		this.relation_type = relation_type;
	}
	public void setEntity_relation_id(String entity_relation_id) {
		this.entity_relation_id = entity_relation_id;
	}
	public List<Entity_Attribute> getRelation_attribute_value_list() {
		return relation_attribute_value_list;
	}
	public void setRelation_attribute_value_list(
			List<Entity_Attribute> relation_attribute_value_list) {
		this.relation_attribute_value_list = relation_attribute_value_list;
	}
	
	
	
	public String getEntity_group_id() {
		return entity_group_id;
	}
	public void setEntity_group_id(String entity_group_id) {
		this.entity_group_id = entity_group_id;
	}



	private String relation_type;

	private String entity_id;
	private String entity_group_id;
	private String entity_relation_id;
	private List<Entity_Attribute> relation_attribute_value_list;
	
}
