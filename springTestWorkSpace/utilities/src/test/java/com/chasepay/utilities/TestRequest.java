package com.chasepay.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.google.gson.Gson;

public class TestRequest {
	
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
		
		
		 ServletRequestData requestData = new ServletRequestData();
		 
		 requestData.setEntity(entity);
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("company_id", "62");
		 
		 requestData.setParamterMap(map);
		 
		 String jsonStr = requestData.toString();
		 
		 System.out.println(jsonStr);
		 
		 Gson gson = new Gson();
		 
		 ServletRequestData fromJson = gson.fromJson(jsonStr, ServletRequestData.class);
		 
		 System.out.println(fromJson.toString());
		 
	}

}
