package com.chasepay.utilities.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chasepay.utilities.constant.EntityConstant;


public class EntityDataCache {
	
	private static class EntityDataCacheClass
	{
		private static final EntityDataCache  instance = new EntityDataCache();
	}

	
	private static Map<String,Map<String,Map<String,String>>> cache =  new HashMap<String,Map<String,Map<String,String>>>();
	
	private EntityDataCache()
	{
		
	}
	
	public static EntityDataCache getInstance()
	{
		return EntityDataCacheClass.instance;
	}
	
	public Map<String, Map<String, String>>  getEntityDefCache(Connection conn,String dataSourceKey,String companyId)
	{
		Map<String, Map<String, String>> defCache = cache.get(EntityConstant.CACHE_ENTITY_DEF);
		if(null==defCache)
		{
			EntityDatabaseSerivce entityDatabaseSerivce = new EntityDatabaseSerivce();
			List<Map<String, String>> tableListMap = entityDatabaseSerivce.getTableListMap(conn,dataSourceKey, EntityConstant.table_name_entity_attribute_def, companyId);
			if(null==tableListMap||0==tableListMap.size())
			{
				return null;
			}
			else
			{
				Map<String, Map<String, String>> tansferedMap = tansferListToMap(tableListMap, EntityConstant.attribute_def_id);
				cache.put(EntityConstant.CACHE_ENTITY_DEF, tansferedMap);
				return tansferedMap;
			}
			
		}
		else
		{
			return defCache;
		}
	}
	
	
	public void  setEntityDefCache(Map<String, Map<String, String>> entityDef,String companyId)
	{
		cache.put(EntityConstant.CACHE_ENTITY_DEF, entityDef);
	}
	
	
	
	public Map<String, Map<String, String>> tansferListToMap(List<Map<String, String>> list,String key)
	{
		Map<String, Map<String, String>> transferedMap = new HashMap<String, Map<String,String>>();
		for (Map<String, String> map : list) 
		{
			String value = map.get(key);
			transferedMap.put(value, map);
		}
		return transferedMap;
	}

}
