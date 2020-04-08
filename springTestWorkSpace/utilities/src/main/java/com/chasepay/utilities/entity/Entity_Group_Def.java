package com.chasepay.utilities.entity;

import java.util.List;

public class Entity_Group_Def {

	public String getEntity_group_def_id() {
		return entity_group_def_id;
	}

	public void setEntity_group_def_id(String entity_group_def_id) {
		this.entity_group_def_id = entity_group_def_id;
	}

	public List<String> getEntity_group_attribute_def_list() {
		return entity_group_attribute_def_list;
	}

	public void setEntity_group_attribute_def_list(
			List<String> entity_group_attribute_def_list) {
		this.entity_group_attribute_def_list = entity_group_attribute_def_list;
	}

	public Entity_Def getEntity_def() {
		return entity_def;
	}

	public void setEntity_def(Entity_Def entity_def) {
		this.entity_def = entity_def;
	}
	
	private String entity_group_def_id; /* PO, Product Category...*/
	private List<String> entity_group_attribute_def_list;
	
	private Entity_Def entity_def;

}
