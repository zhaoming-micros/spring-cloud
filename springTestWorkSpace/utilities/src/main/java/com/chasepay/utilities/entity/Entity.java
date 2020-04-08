package com.chasepay.utilities.entity;

import java.util.List;

public class Entity {
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public List<Entity_Attribute> getAttribute_value_list() {
		return attribute_value_list;
	}
	public void setAttribute_value_list(List<Entity_Attribute> attribute_value_list) {
		this.attribute_value_list = attribute_value_list;
	}
	
	public boolean isPureEntityGroup() {
		return isPureEntityGroup;
	}
	public void setPureEntityGroup(boolean isPureEntityGroup) {
		this.isPureEntityGroup = isPureEntityGroup;
	}

	public String getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}

	protected String entity_id;
	protected String entity_type;	

	private boolean isPureEntityGroup = false; //for just insert entity_group, without relationship, we can use entity for it

	protected List<Entity_Attribute> attribute_value_list;


	public String toString() {
       String re =  "{entity_id = "+entity_id+",entity_type = "+entity_type+",isPureEntityGroup = "+isPureEntityGroup+",attribute_value_list = [";
       
       for (Entity_Attribute entity_Attribute : attribute_value_list) 
       {
		    re+= "{attribute_def_id = "+entity_Attribute.getAttribute_def_id()+",attribute_id = "+entity_Attribute.getAttribute_id()+",attribute_value = "+entity_Attribute.getAttribute_value()+"}";
	   }
       
       re += "]}";
       return re;

    }

}
