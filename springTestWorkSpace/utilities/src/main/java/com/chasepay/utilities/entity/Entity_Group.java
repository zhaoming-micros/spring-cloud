package com.chasepay.utilities.entity;

import java.util.List;

public class Entity_Group {

	public String getEntity_group_id() {
		return entity_group_id;
	}
	public void setEntity_group_id(String entity_group_id) {
		this.entity_group_id = entity_group_id;
	}
	
	public List<Entity_Attribute> getGroup_attribute_value_list() {
		return group_attribute_value_list;
	}
	public void setGroup_attribute_value_list(
			List<Entity_Attribute> group_attribute_value_list) {
		this.group_attribute_value_list = group_attribute_value_list;
	}

	public List<Entity> getEntity_list() {
		return entity_list;
	}
	public void setEntity_list(List<Entity> entity_list) {
		this.entity_list = entity_list;
	}

	public List<Entity_Group_Relation> getRelation_list() {
		return relation_list;
	}
	public void setRelation_list(List<Entity_Group_Relation> relation_list) {
		this.relation_list = relation_list;
	}

	public List<Entity_Group> getChild() {
		return child;
	}
	public void setChild(List<Entity_Group> child) {
		this.child = child;
	}
	
	public String getEntity_group_type() {
		return entity_group_type;
	}
	public void setEntity_group_type(String entity_group_type) {
		this.entity_group_type = entity_group_type;
	}

	private String entity_group_id;
	private String entity_group_type;

	private List<Entity_Attribute> group_attribute_value_list;
	private List<Entity> entity_list;
	
	private List<Entity_Group_Relation> relation_list;
	private List<Entity_Group> child;

}
