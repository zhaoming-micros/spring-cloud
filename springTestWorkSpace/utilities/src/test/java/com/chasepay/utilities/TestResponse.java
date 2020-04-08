package com.chasepay.utilities;

import java.util.ArrayList;
import java.util.List;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.webservice.ServletResponseData;
import com.google.gson.Gson;

public class TestResponse {
	
	public static void main(String[] args) {

		 Entity entity = new Entity();
		 entity.setEntity_id("1");
		 entity.setEntity_type("2");
		
		 List<Entity_Attribute> attributes = new ArrayList<Entity_Attribute>();
		 Entity_Attribute entity_Attribute = new Entity_Attribute();
		 entity_Attribute.setAttribute_def_id("1");
		 entity_Attribute.setAttribute_value("name");
		 attributes.add(entity_Attribute);
		 entity.setAttribute_value_list(attributes);
		 
		 
		 List<Entity> entitys = new ArrayList<Entity>();
		 entitys.add(entity);
		 
		 ServletResponseData responseData  = new ServletResponseData();
		 responseData.setSuccess(true);
		// responseData.setData(entitys);
		 responseData.setData("test");

		 String jsonStr = responseData.toString();
		 
		 System.out.println(jsonStr);
		 
		 Gson gson = new Gson();
		 
		 ServletResponseData fromJson = gson.fromJson(jsonStr, ServletResponseData.class);
		 
		 System.out.println(fromJson.toString());
		 
	}

}
