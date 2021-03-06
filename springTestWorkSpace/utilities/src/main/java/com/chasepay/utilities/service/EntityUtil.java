package com.chasepay.utilities.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.chasepay.database.util.ChasepayDatabaseUtil;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.Entity_Group_Relation;
import com.chasepay.utilities.service.EntityDataCache;


public class EntityUtil {
	
	private static Logger logger = LogManager.getLogger(EntityUtil.class);
	private ChasepayDatabaseUtil databaseUtil = new ChasepayDatabaseUtil();
	
	
	public static Map<String,String> getMapFromAttributeList(List<Entity_Attribute> attribute_value_list)
	{
		Map<String,String> map = new HashMap<String,String>();
		for (Entity_Attribute entity_Attribute : attribute_value_list) 
		{
			String attribute_def_id = entity_Attribute.getAttribute_def_id();
			String attribute_value = entity_Attribute.getAttribute_value();
			map.put(attribute_def_id, attribute_value);
		}
		return map;
	}

	
	public String getAttributeValueTableName(Connection conn,String dataSourceKey, Map<String, String> map, String companyId)
	{
		String defId = map.get(EntityConstant.attribute_def_id);
		String dateType = getDataTyeByDefId(conn,dataSourceKey,defId, companyId);
		if(null==dateType)
		{
			logger.error("get dateType error.defId = "+defId);
			return null;
		}
		
		String table_name = null;
		
		 if(EntityConstant.ATTRIBUTE_DATA_TYPE_INT.equals(dateType))
		 {
			 table_name = EntityConstant.table_name_entity_attribute_int;
		 }
		 else if(EntityConstant.ATTRIBUTE_DATA_TYPE_DATE.equals(dateType))
		 {
			 table_name = EntityConstant.table_name_entity_attribute_date;	
		 }
		 else
		 {
			 table_name = EntityConstant.table_name_entity_attribute;
		 }   
		return table_name;
	}
	
	
	public boolean insertBulkEntityAttribute(Connection conn,String dataSourceKey, List<Map<String, String>> listHt, String companyId, String year)
	{
		List<Map<String, String>> listChar = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listInt = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listDate = new ArrayList<Map<String,String>>();
		for (Map<String, String> map : listHt) 
		{
			String dateType = map.get(EntityConstant.attribute_data_type);
			if(null==dateType)
			{
				String defId = map.get(EntityConstant.attribute_def_id);
				dateType = getDataTyeByDefId(conn,dataSourceKey,defId, companyId);
				if(null==dateType)
				{
					logger.error("get dateType error.defId = "+defId);
					return false;
				}
			}
			
			//map.remove(ProductDatabaseConstant.attribute_def_id);
			
		    if(EntityConstant.ATTRIBUTE_DATA_TYPE_INT.equals(dateType))
			{
		    	listInt.add(map);
			}
			else if(EntityConstant.ATTRIBUTE_DATA_TYPE_DATE.equals(dateType))
			{
				listDate.add(map);
			}
			else
			{
				listChar.add(map);
			}   		
		}
		
		boolean result = false;
		
		if(listChar.size()>0)
		{
			
			result = databaseUtil.insertBulkDataNonReturnID(conn, dataSourceKey, listChar, companyId, null, EntityConstant.table_name_entity_attribute);
			if(!result)
			{
				return false;
			}
		}
		
		if(listInt.size()>0)
		{
			result = databaseUtil.insertBulkDataNonReturnID(conn, dataSourceKey, listInt, companyId, null, EntityConstant.table_name_entity_attribute_int);
			if(!result)
			{
				return false;
			}
		}
		
		if(listDate.size()>0)
		{
			result = databaseUtil.insertBulkDataNonReturnID(conn, dataSourceKey, listDate, companyId, null, EntityConstant.table_name_entity_attribute_date);
			if(!result)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	public boolean updateBulkEntityAttribute(Connection conn,String dataSourceKey, List<Map<String, String>> listHt, List<Map<String, String>> updateKeys, String companyId, String year)
	{
		List<Map<String, String>> listChar = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listCharKey = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listInt = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listIntKey = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listDate = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listDateKey = new ArrayList<Map<String,String>>();

		for (int i=0;i<listHt.size();i++) 
		{
			Map<String, String> map = listHt.get(i);
			String dateType = map.get(EntityConstant.attribute_data_type);
			if(null==dateType)
			{
				String defId = map.get(EntityConstant.attribute_def_id);
				if(null==defId||"".equals(defId))
				{
					continue;
				}
				dateType = getDataTyeByDefId(conn,dataSourceKey,defId, companyId);
				if(null==dateType)
				{
					logger.error("get dateType error.defId = "+defId);
					return false;
				}
			}
			map.remove(EntityConstant.attribute_def_id);
			
		    if(EntityConstant.ATTRIBUTE_DATA_TYPE_INT.equals(dateType))
			{
		    	listInt.add(map);
		    	listIntKey.add(updateKeys.get(i));
			}
			else if(EntityConstant.ATTRIBUTE_DATA_TYPE_DATE.equals(dateType))
			{
				listDate.add(map);
				listDateKey.add(updateKeys.get(i));
			}
			else
			{
				listChar.add(map);
				listCharKey.add(updateKeys.get(i));
			}   		
		}
		
		boolean result = false;
		
		if(listChar.size()>0)
		{
			result = databaseUtil.updateBulkData(conn,dataSourceKey, listChar, listCharKey, companyId, null, EntityConstant.table_name_entity_attribute);
			if(!result)
			{
				return false;
			}
		}
		
		if(listInt.size()>0)
		{
			result = databaseUtil.updateBulkData(conn,dataSourceKey, listInt, listIntKey, companyId, null, EntityConstant.table_name_entity_attribute_int);
			if(!result)
			{
				return false;
			}
		}
		
		if(listDate.size()>0)
		{
			result = databaseUtil.updateBulkData(conn,dataSourceKey, listDate, listDateKey, companyId, null, EntityConstant.table_name_entity_attribute_date);
			if(!result)
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	public boolean deleteBulkEntityAttribute(Connection conn,String dataSourceKey, List<Map<String, String>> listHt, String companyId, String year)
	{

		boolean result = databaseUtil.deleteBulkData(conn, dataSourceKey, listHt, companyId, null, EntityConstant.table_name_entity_attribute);
		if(!result)
		{
			return false;
		}
		
		result = databaseUtil.deleteBulkData(conn,dataSourceKey, listHt, companyId, null, EntityConstant.table_name_entity_attribute_int);
		if(!result)
		{
			return false;
		}
		
		result = databaseUtil.deleteBulkData(conn,dataSourceKey, listHt, companyId, null, EntityConstant.table_name_entity_attribute_date);
		if(!result)
		{
			return false;
		}
		
		return true;
	}

	
	
	public void setEntityGroupRelationAttributeData(Map<String, String> ht,String dataSourceKey, String companyId, String year,List<Entity_Group_Relation> relation_list)
	{
		List<Map<String, String>> relation_value_list = new ArrayList<Map<String,String>>(0);
		List<Map<String, String>> relation_value_list_string = databaseUtil.selectData(ht,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute, null);
		if(null!=relation_value_list)
		{
			relation_value_list.addAll(relation_value_list_string);
		}
		List<Map<String, String>> relation_value_list_int = databaseUtil.selectData(ht,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute_int, null);
		if(null!=relation_value_list_int)
		{
			relation_value_list.addAll(relation_value_list_int);
		}
		List<Map<String, String>> relation_value_list_date = databaseUtil.selectData(ht, dataSourceKey,companyId, null, EntityConstant.table_name_entity_attribute_date, null);
		if(null!=relation_value_list_date)
		{
			relation_value_list.addAll(relation_value_list_date);
		}
		groupEntityGroupRelationAttributeList(relation_list, relation_value_list);
	}
	
	
	
	
	private void groupEntityGroupRelationAttributeList(List<Entity_Group_Relation> relation_list,List<Map<String, String>> relation_value_list)
	{
		for (Entity_Group_Relation entity_Group_Relation : relation_list) 
		{
			String entity_relation_id = entity_Group_Relation.getEntity_relation_id();
			
			Iterator<Map<String, String>> iterator = relation_value_list.iterator();
			List<Entity_Attribute> list = new ArrayList<Entity_Attribute>();
			Entity_Attribute entity_Attribute = null;
			while(iterator.hasNext())
			{
				Map<String, String> next = iterator.next();
				String entity_id = next.get(EntityConstant.entity_id);
				if(entity_relation_id.equals(entity_id))
				{
					iterator.remove();
					entity_Attribute = new Entity_Attribute();
					String attribute_id = next.get(EntityConstant.attribute_id);
					String def_id = next.get(EntityConstant.attribute_def_id);
					String value = next.get(EntityConstant.attribute_value);
					entity_Attribute.setAttribute_id(attribute_id);
					entity_Attribute.setAttribute_def_id(def_id);
					entity_Attribute.setAttribute_value(value);
					list.add(entity_Attribute);
				}
			}
			entity_Group_Relation.setRelation_attribute_value_list(list);
			
		}
	}
	
	
	public String getDataTyeByDefId(Connection conn,String dataSourceKey,String defId, String companyId)
	{
		Map<String, Map<String, String>> entityDefCache = EntityDataCache.getInstance().getEntityDefCache(conn,dataSourceKey,companyId);
		if(null==entityDefCache)
		{
			return null;
		}
		return entityDefCache.get(defId).get(EntityConstant.attribute_data_type);
	}
	
	
	public List<Entity> transferMapToList(Map<String, Entity> map,List<Entity> list)
	{ 
		Iterator<Entry<String, Entity>> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<String, Entity> next = iterator.next();
			list.add(next.getValue());
		}
		return list;
	}

}
