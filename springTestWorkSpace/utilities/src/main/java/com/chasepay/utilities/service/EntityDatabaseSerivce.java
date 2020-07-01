package com.chasepay.utilities.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.constants.webutil.AccountDatabaseConstants;
import com.chasepay.database.config.ChasepayDatabaseConnection;
import com.chasepay.database.util.ChasepayDatabaseUtil;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.Entity_Group;
import com.chasepay.utilities.entity.Entity_Group_Relation;



public class EntityDatabaseSerivce {

	private static Logger logger = LogManager.getLogger(EntityDatabaseSerivce.class);
	private ChasepayDatabaseUtil databaseUtil = new ChasepayDatabaseUtil();
	private EntityUtil entityUtil = new EntityUtil();
	
	public Connection getConnection(String dataSourceKey)
	{
		Connection conn = null;
		conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
		if (conn == null)
		{
			logger.error("get connection error.dataSourceKey = "+dataSourceKey);
		    return null;
		}
		return conn;
	}
	
	public void closeConnection(Connection conn)
	{
		databaseUtil.close(conn);
	}
	
	public void commitConnection(Connection conn)
	{
		databaseUtil.commit(conn);
	}
	
	
	public void rollbackConnection(Connection conn)
	{
		databaseUtil.rollback(conn);
	}
	
	public boolean addEntityList_Group_Relation(Connection conn,String dataSourceKey, String companyId, String groupId, List<String> entity_ids)
	{
		try 
		{
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			Map<String,String> h = new HashMap<String,String>();
			
			for(String e:entity_ids)
			{
				h.put(EntityConstant.entity_id, e);
				h.put(EntityConstant.entity_group_id, groupId);
				list.add(h);
			}
			boolean result = databaseUtil.insertBulkDataNonReturnID(conn, dataSourceKey, list, companyId, null, EntityConstant.table_name_entity_group_relation);
			if (!result)
			{
				databaseUtil.rollback(conn);
				return false;
			}
			return true;
		}
		catch (Exception exp) 
		{
			logger.error("insertBulkEntities error!", exp);
			logger.error(exp);
			return false;
		}
	}
	
	public boolean insertBulkEntities(Connection conn,String dataSourceKey, String companyId, List<Entity> el)
	{
		try 
		{
			
			List<Map<String,String>> listHt = new ArrayList<Map<String,String>>();
			
			boolean isMenu = false;
			for (Entity e:el)
			{
				Map<String,String> ht1 = new HashMap<String,String>();
				ht1.put(EntityConstant.entity_type, e.getEntity_type());
			
				String entity_id = EntityConstant.INSERT_FAIL;

				if (e.isPureEntityGroup())
				{
					entity_id = databaseUtil.insertDataReturnId(dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_group);
				}
				else
				{
					String attribute = el.get(0).getEntity_type();
					
					if (EntityConstant.ENTITY_TYPE_MENU.equals(attribute))
					{
						isMenu = true;
						entity_id = databaseUtil.insertDataReturnId(dataSourceKey,ht1, companyId, null, EntityConstant.table_name_sys_entity);
					}
					else
						entity_id = databaseUtil.insertDataReturnId(dataSourceKey,ht1, companyId, null, EntityConstant.table_name_entity);
				}
				if (EntityConstant.INSERT_FAIL.equals(entity_id))
				{
					return false;
				}
			
				e.setEntity_id(entity_id);
			
				List<Entity_Attribute> alist = e.getAttribute_value_list();
				if (alist==null || alist.size()==0)
				{
					continue;
				}
			
				for (Entity_Attribute a : alist)
				{
					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.entity_id, entity_id);
					ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
					ht1.put(EntityConstant.attribute_value, a.getAttribute_value());
					if (a.getAttribute_value2()!=null)
						ht1.put(EntityConstant.attribute_value2, a.getAttribute_value2());
					
					listHt.add(ht1);
				}
			}

			boolean result = false;
			if (isMenu)
			{
				result = databaseUtil.insertBulkDataNonReturnID(dataSourceKey, listHt, companyId, null, EntityConstant.table_name_sys_entity_attribute);
			}   
			else
			{
				result = databaseUtil.insertBulkDataNonReturnID(dataSourceKey,listHt, companyId, null, EntityConstant.table_name_entity_attribute);
			}		
			
			if (!result)
			{
				return false; 
			}
			return true;
		}
		catch (Exception exp) 
		{
			logger.error("insertBulkEntities error!", exp);
			logger.error(exp);
			return false;
		}
	}
	
	
	public List<Entity> getEntityListFromGroupRelation(Connection conn,String dataSourceKey, String companyId, String entity_type, String groupId, boolean splitValue2)
	{
		try 
		{
			
			List<Map<String,String>> list = null;
			Map<String,String> ht1 = new HashMap<String,String>();
			ht1.put(EntityConstant.entity_group_id, groupId);
			list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_group_relation, null);
			if (list == null)
			{
				return null;
			}
			
			if (list.size() == 0)
				return new ArrayList<Entity>();
			
			//get entity_id, then get all attribute of the entity
			StringBuilder s = new StringBuilder(64);
			s.append(" in (");
			
			Map<String, Entity> el = new HashMap<String, Entity>();
			String key = EntityConstant.entity_id;

			for (Map<String,String> h:list)
			{
				String id = h.get(key);
				Entity e = new Entity();
				el.put(id, e);
				e.setEntity_type(entity_type);
				e.setEntity_id(id);

				s.append(id).append(",");
			}
			
			int len = s.length();
			s.delete(len-1, len);
			s.append(") ");
			
			ht1.clear();
			ht1.put(EntityConstant.entity_id, s.toString());
			
			if (EntityConstant.ENTITY_TYPE_MENU.equals(entity_type))
			{
				list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_sys_entity_attribute, EntityConstant.entity_id);

			}
			else
				list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_attribute, EntityConstant.entity_id);
			
			return setEntityList(list, el, splitValue2);
		}
		catch (Exception exp) 
		{
			logger.error("getEntityListByEntityType error!", exp);
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e1) 
			{
				logger.error(e1);
			}
			return null;
		}
		finally
		{
			try 
			{
				conn.close();
			} 
			catch (Exception e2) 
			{
				logger.error(e2);
			}
		}

	}
	
	private String getCond(List<String> ids)
	{
		StringBuilder s = new StringBuilder();
		s.append(" in (");
		for (String i:ids)
		{
			s.append(i).append(",");
		}
		s.delete(s.length()-1, s.length());
		s.append(")");
		return s.toString();
	}
	
	public List<Entity> getEntityListByEntityIds(Connection conn,String dataSourceKey,String companyId, List<String> ids)
	{
		try 
		{
			
			Map<String,String> ht1 = new HashMap<String,String>();
			ht1.put(EntityConstant.entity_id, getCond(ids));
			
			List<Map<String,String>> list = null;
			list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity, EntityConstant.entity_id);
			
			if (list == null)
			{
				return null;
			}
			
			if (list.size() == 0)
				return new ArrayList<Entity>();
			
			//get entity_id, then get all attribute of the entity
			StringBuilder s = new StringBuilder(64);
			s.append(" in (");
			
			Map<String, Entity> el = new HashMap<String, Entity>();
			String key = EntityConstant.entity_id;

			String entity_type = list.get(0).get(EntityConstant.entity_type);
			for (Map<String,String> h:list)
			{
				String id = h.get(key);
				Entity e = new Entity();
				el.put(id, e);
				e.setEntity_type(entity_type);
				e.setEntity_id(id);

				s.append(id).append(",");
			}
			
			int len = s.length();
			s.delete(len-1, len);
			s.append(") ");
			ht1.clear();
			ht1.put(EntityConstant.entity_id, s.toString());
			ht1.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
			
			return setEntityListAttribute(conn,dataSourceKey, companyId, entity_type, ht1, el, false);

		}
		catch (Exception exp) 
		{
			logger.error("getEntityListByEntityType error!", exp);
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e1) 
			{
				logger.error(e1);
			}
			return null;
		}
		finally
		{
			try 
			{
				conn.close();
			} 
			catch (Exception e2) 
			{
				logger.error(e2);
			}
		}
	}
	
	public List<Entity> getEntityListByEntityType(Connection conn,String dataSourceKey,String companyId, String entity_type, boolean isEntityGroup, boolean splitValue2)
	{

			Map<String,String> ht1 = new HashMap<String,String>();
			ht1.put(EntityConstant.entity_type, entity_type);
			
			List<Map<String,String>> list = null;
			
			if (isEntityGroup)
			{
				list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_group, EntityConstant.entity_group_id);
			}
			else if (EntityConstant.ENTITY_TYPE_MENU.equals(entity_type))
			{
				list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_sys_entity, EntityConstant.entity_id);

			}
			else
				list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity, EntityConstant.entity_id);
			
			if (list == null)
			{
				return null;
			}
			
			if (list.size() == 0)
				return new ArrayList<Entity>();
			
			//get entity_id, then get all attribute of the entity
			StringBuilder s = new StringBuilder(64);
			s.append(" in (");
			
			Map<String, Entity> el = new HashMap<String, Entity>();
			String key = EntityConstant.entity_id;
			if (isEntityGroup)
			{
				key = EntityConstant.entity_group_id;
			}

			for (Map<String,String> h:list)
			{
				String id = h.get(key);
				Entity e = new Entity();
				el.put(id, e);
				e.setEntity_type(entity_type);
				e.setEntity_id(id);

				s.append(id).append(",");
			}
			
			int len = s.length();
			s.delete(len-1, len);
			s.append(") ");
			ht1.clear();
			ht1.put(EntityConstant.entity_id, s.toString());
			ht1.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
			
			return setEntityListAttribute(conn,dataSourceKey, companyId, entity_type, ht1, el, splitValue2);

		
		

	}
	
	private List<Entity> setEntityListAttribute(Connection conn,String dataSourceKey, String companyId, String entity_type, Map<String,String> ht1, Map<String, Entity> el, boolean splitValue2)
	{
		List<Entity> ee = new ArrayList<Entity>();
		if (EntityConstant.ENTITY_TYPE_MENU.equals(entity_type))
		{
			List<Map<String,String>> list = databaseUtil.selectData(conn, dataSourceKey,ht1, companyId, null, EntityConstant.table_name_sys_entity_attribute, EntityConstant.entity_id);
			return setEntityList(list, el, splitValue2);
		}
		
		List<Map<String,String>> list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_attribute, EntityConstant.entity_id);
		if (list == null || list.size() == 0)
		{
			
		}
		else
			ee = setEntityList(list, el, splitValue2);
		
		list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_attribute_int, EntityConstant.entity_id);
		if (list == null || list.size() == 0)
		{
			
		}
		else
		{
			if (ee == null)
				ee = setEntityList(list, el, splitValue2);
			else
				ee.addAll(setEntityList(list, el, splitValue2));
		}
		
		list = databaseUtil.selectData(conn,dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_attribute_date, EntityConstant.entity_id);
		if (list == null || list.size() == 0)
		{
			
		}
		else
		{
			if (ee == null)
				ee = setEntityList(list, el, splitValue2);
			else
				ee.addAll(setEntityList(list, el, splitValue2));
		}
		
		return ee;
	}
	
	private List<Entity> setEntityList(List<Map<String,String>> list, Map<String, Entity> el, boolean splitValue2)
	{

		List<Entity> ee = new ArrayList<Entity>();
		for (Map<String,String> h:list)
		{
			String id = h.get(EntityConstant.entity_id);
			Entity e = el.get(id);
			
			Entity currentE = getExistEntityInList(ee,e);
			if(null==currentE)
			{
				ee.add(e);
			}
			else
			{
				e = currentE;
			}
			
			
			List<Entity_Attribute> al = e.getAttribute_value_list();
			if (al == null)
			{
				al = new ArrayList<Entity_Attribute>();
				e.setAttribute_value_list(al);
			}
			
			Entity_Attribute a = new Entity_Attribute();
			al.add(a);
			
			a.setAttribute_def_id(h.get(EntityConstant.attribute_def_id));
			a.setAttribute_id(h.get(EntityConstant.attribute_id));
			a.setAttribute_type(h.get(EntityConstant.attribute_type));
			a.setAttribute_value(h.get(EntityConstant.attribute_value));
			
			String tmp = h.get(EntityConstant.attribute_value2);
			if (tmp != null)
				a.setAttribute_value2(tmp);
			
			if (splitValue2)
			{
				String attribute_type = a.getAttribute_type();
				String[] vv = tmp.split("[|]");
				for (String v:vv)
				{
					String[] d = v.split("[=]");
					a = new Entity_Attribute();
					al.add(a);
					
					a.setAttribute_type(attribute_type);
					a.setAttribute_def_id(d[0]);
					a.setAttribute_value(d[1]);
				}
			}
		}
		
		return ee;
	}
	
	
	private Entity getExistEntityInList(List<Entity> list,Entity e)
	{
		String orgId = e.getEntity_id();
		for (Entity entity : list) 
		{
			String id = entity.getEntity_id();
			if(id.equals(orgId))
			{
				return entity;
			}
		}
		return null;
	}
	
	public String insertEntity(Connection conn,String dataSourceKey, String companyId, Entity e) {
			
			Map<String,String> ht1 = new HashMap<String,String>();
			ht1.put(EntityConstant.entity_type, e.getEntity_type());
			
			String entity_id = null;
			
			if (e.isPureEntityGroup())
			{
				entity_id = databaseUtil.insertDataReturnId(conn, dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_group);
			}
			else
			{
				entity_id = databaseUtil.insertDataReturnId(conn,dataSourceKey,ht1, companyId, null, EntityConstant.table_name_entity);
			}
				
			if (null==entity_id)
			{
				return null;
			}
			
			e.setEntity_id(entity_id);
			
			List<Entity_Attribute> alist = e.getAttribute_value_list();
			if (alist==null || alist.size()==0)
			{
				//c.commit(conn);
				return entity_id;
			}
			
			List<Map<String,String>> listHt = new ArrayList<Map<String,String>>();
			for (Entity_Attribute a : alist)
			{
				ht1 = new HashMap<String,String>();
				ht1.put(EntityConstant.entity_id, entity_id);
				ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
				ht1.put(EntityConstant.attribute_value, a.getAttribute_value());
				if (a.getAttribute_value2()!=null)
					ht1.put(EntityConstant.attribute_value2, a.getAttribute_value2());
				
				listHt.add(ht1);
			}
			
			//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
			boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourceKey, listHt, companyId, null);
			if (!result)
			{
				return null; 
			}
			
			return entity_id;

	}
	
	public String insertEntity(Connection conn,String dataSourcekey,String companyId,Map<String,String> ht, Entity e) {
		
			
			String entity_id = insertEntity(conn, dataSourcekey, companyId, e);
			if (EntityConstant.INSERT_FAIL.equals(entity_id))
				return EntityConstant.INSERT_FAIL;
			
			String relationType = ht.get(EntityConstant.relation_type);
			String entity_group_id = ht.get(EntityConstant.entity_group_id);
			if(null!=relationType&&null!=entity_group_id)
			{
				ht.put(EntityConstant.entity_id, entity_id);
				String relation_id = databaseUtil.insertDataReturnId(conn,dataSourcekey,ht, companyId, null, EntityConstant.table_name_entity_group_relation);
				if (EntityConstant.INSERT_FAIL.equals(relation_id))
				{
					return EntityConstant.INSERT_FAIL;
				}
					
			}
            return entity_id;
	}

	public String insertEntityGroup(Connection conn,String dataSourcekey,String companyId, Entity_Group eg) 
	{
			Map<String,String> ht1 = new HashMap<String,String>();
			ht1.put(EntityConstant.entity_type, eg.getEntity_group_type());
			
			String entity_group_id = databaseUtil.insertDataReturnId(conn,dataSourcekey,ht1, companyId, null, EntityConstant.table_name_entity);
			if (null==entity_group_id)
			{
				return null;
			}
			
			eg.setEntity_group_id(entity_group_id);
			
			List<Entity_Attribute> alist = eg.getGroup_attribute_value_list();
			List<Entity_Group_Relation> rlist = eg.getRelation_list();
			
			if ( (alist==null || alist.size()==0) && (rlist==null || rlist.size()==0))
			{
				return entity_group_id;
			}
			
			List<Map<String,String>> listHt = new ArrayList<Map<String,String>>();
			if (alist != null && alist.size()>0)
			{
				for (Entity_Attribute a : alist)
				{
					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.entity_id, entity_group_id);
					ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
					ht1.put(EntityConstant.attribute_value, a.getAttribute_value());
					ht1.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
					
					listHt.add(ht1);
				}
				
				//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourcekey, listHt, companyId, null);
				if (!result)
				{
					return null; 
				}

			}
			
			if (rlist != null && rlist.size()>0)
			{
				listHt = new ArrayList<Map<String,String>>();
				for (Entity_Group_Relation a : rlist)
				{
					
					if (!addEntity_Group_Relation(conn,dataSourcekey,listHt, ht1, companyId, a, entity_group_id))
					{
						return null;
					}
				}
				
				//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourcekey, listHt, companyId, null);
				if (!result)
				{
					return null;
				}

			}
			
			return entity_group_id;
	}
	
	
	private boolean addEntity_Group_Relation(
			Connection conn,
			String dataSourceKey,
			List<Map<String,String>> listHt,
			Map<String,String> ht1, 
			String companyId, 
			Entity_Group_Relation a, 
			String entity_group_id)
	{
		ht1 = new HashMap<String,String>();
		ht1.put(EntityConstant.entity_id, a.getEntity_id());
		ht1.put(EntityConstant.entity_group_id, entity_group_id);
		if (a.getRelation_type()!=null)
			ht1.put(EntityConstant.relation_type, a.getRelation_type());
		
		
		String entity_relation_id = databaseUtil.insertDataReturnId(conn,dataSourceKey,ht1, companyId, null, EntityConstant.table_name_entity_group_relation);
		if (EntityConstant.INSERT_FAIL.equals(entity_relation_id))
		{
			return false;
		}
		
		a.setEntity_relation_id(entity_relation_id);
		
		List<Entity_Attribute> alist = a.getRelation_attribute_value_list();
		if (alist == null || alist.size()==0)
		{
			return true;
		}

		for(Entity_Attribute aa:alist)
		{
			ht1.put(EntityConstant.attribute_def_id, aa.getAttribute_def_id());
			ht1.put(EntityConstant.attribute_value, aa.getAttribute_value());
			ht1.put(EntityConstant.entity_id, entity_relation_id);
			ht1.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
			
			listHt.add(ht1);
		}
		
		return true;
	}
	

	/* we did not provide updateEntity */
	public boolean updateEntityAttribute(Connection conn ,String dataSourceKey, Map<String,String> ht, Entity e) {


			String companyId = ht.get(AccountDatabaseConstants.company_id);
			
			
			List<Entity_Attribute> alist = e.getAttribute_value_list();
			if (alist==null || alist.size()==0)
				return false;
			
			List<Map<String,String>> listHt = new ArrayList<Map<String,String>>();
			List<Map<String,String>> updateListHt = new ArrayList<Map<String,String>>();
			List<Map<String,String>> updateListKeys = new ArrayList<Map<String,String>>();
			
			Map<String,String> ht1 = null;
			
			String entity_id = e.getEntity_id();
			String attribute_id = null;
			
			for (Entity_Attribute a : alist)
			{
				attribute_id = a.getAttribute_id();
				if (attribute_id == null)
				{
					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.entity_id, entity_id);
					ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
					ht1.put(EntityConstant.attribute_value, a.getAttribute_value());

					listHt.add(ht1);
					continue;
				}
				
				ht1 = new HashMap<String,String>();
				ht1.put(EntityConstant.attribute_id, a.getAttribute_id());
				ht1.put(EntityConstant.entity_id, a.getEntity_id());
				updateListKeys.add(ht1);
				
				ht1 = new HashMap<String,String>();
				ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
				ht1.put(EntityConstant.attribute_value, a.getAttribute_value());
				updateListHt.add(ht1);
			}
			
			if (listHt.size()>0)
			{
				logger.info("insert entityAttribute,size = "+listHt.size());
				//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourceKey, listHt, companyId, null);
				if (!result)
				{
					return false;
				}
			}
			
			if (updateListHt.size()>0)
			{
				logger.info("update entityAttribute,size = "+updateListHt.size());
				//boolean result = c.updateBulkData(conn,updateListHt, updateListKeys, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.updateBulkEntityAttribute(conn,dataSourceKey, updateListHt, updateListKeys, companyId, null);
				if (!result)
				{
					return false;
				}
			}

			return true;
		
	}

	public boolean updateEntityGroup(Connection conn,String dataSourceKey,Map<String,String> ht, Entity_Group eg) {

			String companyId = ht.get(AccountDatabaseConstants.company_id);
			
			List<Entity_Attribute> alist = eg.getGroup_attribute_value_list();
			
			List<Map<String,String>> listHt = new ArrayList<Map<String,String>>();
			List<Map<String,String>> updateListHt = new ArrayList<Map<String,String>>();
			List<Map<String,String>> updateListKeys = new ArrayList<Map<String,String>>();
			
			Map<String,String> ht1 = null;
			
			String entity_group_id = eg.getEntity_group_id();
			String attribute_id = null;
			
			if (alist != null && alist.size()>0)
			{
				for (Entity_Attribute a : alist)
				{
					attribute_id = a.getAttribute_id();
					if (attribute_id == null||"".equals(attribute_id)||"-1".equals(attribute_id))
					{
						ht1 = new HashMap<String,String>();
						ht1.put(EntityConstant.entity_id, entity_group_id);
						ht1.put(EntityConstant.attribute_def_id, a.getAttribute_def_id());
						ht1.put(EntityConstant.attribute_value, a.getAttribute_value());

						listHt.add(ht1);
						continue;
					}

					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.attribute_id, a.getAttribute_id());
					updateListKeys.add(ht1);

					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.attribute_value, a.getAttribute_value());
					updateListHt.add(ht1);
				}

				if (listHt.size()>0)
				{
					//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
					boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourceKey, listHt, companyId, null);
					if (!result)
					{
						return false;
					}
				}

				if (updateListHt.size()>0)
				{
					//boolean result = c.updateBulkData(conn,updateListHt, updateListKeys, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
					boolean result = entityUtil.updateBulkEntityAttribute(conn,dataSourceKey, updateListHt, updateListKeys, companyId, null);
					if (!result)
					{
						return false;
					}
				}
			}

			List<Entity_Group_Relation> elist = eg.getRelation_list();
			if (elist==null || elist.size()==0)
			{
				return true;
			}
			
			listHt = new ArrayList<Map<String,String>>();
			updateListHt = new ArrayList<Map<String,String>>();
			updateListKeys = new ArrayList<Map<String,String>>();
			List<Map<String,String>> deleteRelationList = new ArrayList<Map<String,String>>();
			List<Map<String,String>> deleteAttributeList = new ArrayList<Map<String,String>>();
			Map<String,String> deleteMap = null;
			String entity_id = null;
			attribute_id = null;
			
			for (Entity_Group_Relation e:elist)
			{
				ht1 = new HashMap<String,String>();
				
				String entity_relation_id = e.getEntity_relation_id();

				//new relationship
				if(EntityConstant.FLAG_DELETE.equals(entity_relation_id))
				{
					deleteMap = new HashMap<String,String>();
					deleteMap.put(EntityConstant.entity_id, e.getEntity_id());
					deleteMap.put(EntityConstant.entity_group_id, e.getEntity_group_id());
					deleteRelationList.add(deleteMap);
					
					deleteMap = new HashMap<String,String>();
					deleteMap.put(EntityConstant.entity_id, entity_relation_id);
					deleteMap.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
					deleteAttributeList.add(deleteMap);
					
					continue;
				}
				else if (EntityConstant.FLAG_DELETE.equals(e.getEntity_id())&&null!=entity_relation_id)
				{
					deleteMap = new HashMap<String,String>();
					deleteMap.put(EntityConstant.entity_relation_id, entity_relation_id);
					deleteRelationList.add(deleteMap);
					
					deleteMap = new HashMap<String,String>();
					deleteMap.put(EntityConstant.entity_id, entity_relation_id);
					deleteMap.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
					deleteAttributeList.add(deleteMap);
					
					continue;
				}
				
				if (entity_relation_id==null)
				{
					if (!addEntity_Group_Relation(conn,dataSourceKey,listHt, ht1, companyId, e, entity_group_id))
					{
						return false;
					}
					continue;
				}

				alist = e.getRelation_attribute_value_list();
				if (alist == null || alist.size()==0)
				{
					continue;
				}
				
				for(Entity_Attribute aa:alist)
				{
					
					attribute_id = aa.getAttribute_id();
					if (attribute_id == null)
					{
						ht1 = new HashMap<String,String>();
						ht1.put(EntityConstant.entity_id, entity_id);
						ht1.put(EntityConstant.attribute_def_id, aa.getAttribute_def_id());
						ht1.put(EntityConstant.attribute_value, aa.getAttribute_value());
						ht1.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
						listHt.add(ht1);
						continue;
					}
					
					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.attribute_id, aa.getAttribute_id());
					updateListKeys.add(ht1);
					
					ht1 = new HashMap<String,String>();
					ht1.put(EntityConstant.attribute_value, aa.getAttribute_value());
					updateListHt.add(ht1);
				}
					
			}
			
			if (listHt.size()>0)
			{
				//boolean result = c.insertBulkData(conn,listHt, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.insertBulkEntityAttribute(conn,dataSourceKey, listHt, companyId, null);
				if (!result)
				{
					return false;
				}
			}
			
			if (updateListHt.size()>0)
			{
				//boolean result = c.updateBulkData(conn,updateListHt, updateListKeys, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.updateBulkEntityAttribute(conn,dataSourceKey, updateListHt, updateListKeys, companyId, null);
				if (!result)
				{
					return false;
				}
			}
			
			
			if (deleteRelationList.size()>0)
			{
				boolean result = databaseUtil.deleteBulkData(conn,dataSourceKey, deleteRelationList, companyId, null, EntityConstant.table_name_entity_group_relation);
				if(!result)
				{
					return false;
				}
			}
			
			
			if (deleteAttributeList.size()>0)
			{
				//boolean result = c.deleteBulkData(conn, deleteAttributeList, companyId, null, ProductDatabaseConstant.table_name_entity_attribute);
				boolean result = entityUtil.deleteBulkEntityAttribute(conn,dataSourceKey, deleteAttributeList, companyId, null);
				if (!result)
				{
					return false;
				}
			}
			
			return true;
	
	}

	public List<Entity> getEntityList(Connection conn,String dataSouceKey,Map<String,String> ht, List<Entity_Attribute> alist) 
	{
			String companyId = ht.get(AccountDatabaseConstants.company_id);

			Map<String,String> condHt = new HashMap<String,String>();
			
			for (Entity_Attribute a:alist)
			{
				condHt.put(a.getAttribute_def_id(), a.getAttribute_value());
				
			}
			
			List<Map<String,String>> list = databaseUtil.selectData(condHt,dataSouceKey, companyId, null, EntityConstant.table_name_entity_attribute, null);
			if (list == null)
				return null;
			
			if (list.size() == 0)
				return new ArrayList<Entity>();
			
			for (Map<String,String> h:list)
			{
				
			}
			
			return null;
		
	}

	public List<Entity_Group> getEntityGroupList(Connection conn,String dataSouceKey,Map<String,String> ht, List<String> alist,boolean withGroupAttributes, boolean withRelationAttributes, boolean withRelationEntitys) throws Exception
	{
			String companyId = ht.get(AccountDatabaseConstants.company_id);
			
			List<Map<String,String>> list = null;
			
			list = databaseUtil.selectData(conn,dataSouceKey, ht, companyId, null, EntityConstant.table_name_entity, EntityConstant.entity_id);
			
			if (list == null)
			{
				return null;
			}
				
			List<Entity_Group> entityGroups = new ArrayList<Entity_Group>();
			
			for (Map<String, String>  map : list) 
			{
				Entity_Group entity_Group = new Entity_Group();
				String entity_id = map.get(EntityConstant.entity_id);
				entity_Group.setEntity_group_id(entity_id);
				entityGroups.add(entity_Group);
			}
			
			
			for (Entity_Group entity_group : entityGroups) 
			{
				setEntityGroupRelationInfo(conn,dataSouceKey, ht, entity_group, alist,withGroupAttributes, withRelationAttributes, withRelationEntitys);
			}
			
			return entityGroups;

	}
		

	public Entity getUniqueEntityById(Connection conn,String dataSourceKey,Map<String,String> ht) throws Exception
	{
		String company_id = ht.remove(AccountDatabaseConstants.company_id);
		try 
		{
			Entity entity = new Entity();
			List<Entity_Attribute> attributes = new ArrayList<Entity_Attribute>();
			setEntityAttributeList(conn, dataSourceKey,company_id, ht, attributes);
			entity.setAttribute_value_list(attributes);
			return entity;
		}
		catch (Exception exp) 
		{
			logger.error("getUniqueEntityById error!", exp);
			throw new Exception("getUniqueEntityById error!");	
			
		}
	}
	
	
	
	public Entity getUniqueEntityBySingleAttribute(Connection conn,String dataSourceKey,Map<String,String> ht) throws Exception
	{
		String company_id = ht.remove(AccountDatabaseConstants.company_id);
		try 
		{
			String tablename = entityUtil.getAttributeValueTableName(conn, dataSourceKey, ht, company_id);
			if(null==tablename)
			{
				logger.error("getAttributeValueTableName error!ht = "+ht.toString() );
				throw new Exception("getAttributeValueTableName error");	
			}
			List<Map<String, String>> list = databaseUtil.selectData(ht,dataSourceKey, company_id, null, tablename, null);
			Map<String, String> map = list.get(0);
			if(null==map)
			{
				return null;
			}
			String entity_id = list.get(0).get(EntityConstant.entity_id);
			ht = new HashMap<String, String>();
			ht.put(AccountDatabaseConstants.company_id, company_id);
			ht.put(EntityConstant.entity_id, entity_id);
			ht.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
			Entity entity = getUniqueEntityById(conn, dataSourceKey, ht);
			entity.setEntity_id(entity_id);
			return entity;
		}
		catch (Exception exp) 
		{
			logger.error("getUniqueEntityBySingleAttribute error!", exp);
			throw new Exception("getUniqueEntityBySingleAttribute error!");	
			
		}
		finally
		{
			try 
			{
				conn.close();
			} 
			catch (Exception e2) 
			{
				logger.error(e2);
			}
		}
	}
	
	
	
	
	public Entity_Group getEntityGroupDetail(Connection conn,String dataSouceKey,Map<String,String> ht,boolean withGroupAttributes,boolean withRelationAttributes,boolean withRelationEntitys) throws Exception
	{
		String entity_group_id = ht.get(EntityConstant.entity_id);
	
		Entity_Group g = new Entity_Group();
			
			
		g.setEntity_group_id(entity_group_id);
			
		setEntityGroupRelationInfo(conn,dataSouceKey, ht, g, null,withGroupAttributes, withRelationAttributes, withRelationEntitys);
			
		return g;
	}

	
	
	
	private void setEntityGroupRelationInfo(Connection conn,String dataSourceKey,Map<String,String> ht,Entity_Group entity_Group,List<String> relationEntityIds,boolean withGroupAttributes,boolean withRelationAttributes,boolean withRelationEntitys) throws Exception
	{
		String company_id = ht.get(AccountDatabaseConstants.company_id);
		Map<String,String> condHt = new HashMap<String,String>();
		String entity_group_id = entity_Group.getEntity_group_id();
		if(withGroupAttributes)
		{
			List<Entity_Attribute> a = new ArrayList<Entity_Attribute>();
			entity_Group.setGroup_attribute_value_list(a);
			
			condHt = new HashMap<String,String>();
			condHt.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
			condHt.put(EntityConstant.entity_id, entity_group_id);
			
			setEntityAttributeList(conn,dataSourceKey, company_id, condHt, a);
		}
		
		
		if(!withRelationAttributes&&!withRelationEntitys)
		{
			return;
		}

		String relation_type = ht.get(EntityConstant.relation_type);
		condHt.clear();
		condHt.put(EntityConstant.entity_group_id, entity_group_id);
		
		StringBuilder sb = new StringBuilder(64);
		if(null!=relationEntityIds&&relationEntityIds.size()>0)
		{
			sb.append(" in (");
			for (int i=0;i<relationEntityIds.size();i++)
			{
				sb.append(relationEntityIds.get(i)).append(",");
			}
			int len = sb.length();
			sb.delete(len-1, len);
			sb.append(") ");
			condHt.put(EntityConstant.entity_id, sb.toString());
		}
		condHt.put(EntityConstant.relation_type, relation_type);
		List<Map<String,String>> list = databaseUtil.selectData(conn,dataSourceKey,condHt, company_id, null, EntityConstant.table_name_entity_group_relation, null);
		if (list == null)
		{
			logger.error("getEntityDetail, query entity_group_relation null");
			throw new Exception("getEntityDetail, query entity_group_relation null");
		}
		else
		{
			if(withRelationAttributes)
			{
				List<Entity_Group_Relation> relation_list = new ArrayList<Entity_Group_Relation>();
				entity_Group.setRelation_list(relation_list);
				Entity_Group_Relation entity_group_relation = null;
				StringBuilder s = new StringBuilder(64);
				s.append(" in (");
				for (Map<String, String> relation_map : list) 
				{
					String relation_id = relation_map.get(EntityConstant.entity_relation_id);
					String entity_id = relation_map.get(EntityConstant.entity_id);
					entity_group_relation = new Entity_Group_Relation();
					entity_group_relation.setEntity_group_id(entity_group_id);
					entity_group_relation.setEntity_id(entity_id);
					entity_group_relation.setEntity_relation_id(relation_id);
					entity_group_relation.setRelation_type(relation_type);
					s.append(relation_id).append(",");
					relation_list.add(entity_group_relation);
				}
				int len = s.length();
				s.delete(len-1, len);
				s.append(") ");
				condHt.clear();
				condHt.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
				condHt.put(EntityConstant.entity_id, s.toString());
				entityUtil.setEntityGroupRelationAttributeData(ht,dataSourceKey, company_id, null, relation_list);
				
			}
			
			
			if(withRelationEntitys)
			{
				if(list.size()<1)
				{
					return;
				}
				Map<String,Entity> map = new HashMap<String,Entity>();
				List<Entity> entity_list = new ArrayList<Entity>();
				entity_Group.setEntity_list(entity_list);
				Entity entity = null;
				StringBuilder s = new StringBuilder(64);
				s.append(" in (");
				for (Map<String, String> relation_map : list) 
				{
					String entity_id = relation_map.get(EntityConstant.entity_id);
					entity = new Entity();
					s.append(entity_id).append(",");
					map.put(entity_id, entity);
				}
				int len = s.length();
				s.delete(len-1, len);
				s.append(") ");
				condHt.clear();
				condHt.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY);
				condHt.put(EntityConstant.entity_id, s.toString());
				setEntityMapAttributeList(conn,dataSourceKey, company_id,  condHt, map);
				entityUtil.transferMapToList(map, entity_list);
			}
		}
	}
	
	
	private void setEntityAttributeList(Connection conn,String dataSourceKey, String companyId, Map<String,String> condHt, List<Entity_Attribute> a) throws Exception
	{
		List<Map<String,String>> list = databaseUtil.selectData(condHt, dataSourceKey,companyId, null, EntityConstant.table_name_entity_attribute, null);

		if (list == null )
		{
			logger.error("setEntityAttributeList.query entity_attribute error!");
			throw new Exception("setEntityAttributeList.query entity_attribute error!");
		}
		else
			setEntityAttributeList(list, a, EntityConstant.ATTRIBUTE_DATA_TYPE_STRING);
		
		list = databaseUtil.selectData(condHt,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute_int, null);

		if (list == null )
		{
			logger.error("setEntityAttributeList.query entity_attribute_int error!");
			throw new Exception("setEntityAttributeList.query entity_attribute_int error!");
		}
		else
			setEntityAttributeList(list, a, EntityConstant.ATTRIBUTE_DATA_TYPE_INT);

		list = databaseUtil.selectData(condHt,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute_date, null);

		if (list == null )
		{
			logger.error("setEntityAttributeList.query entity_attribute_date error!");
			throw new Exception("setEntityAttributeList.query entity_attribute_date error!");
		}
		else
			setEntityAttributeList(list, a, EntityConstant.ATTRIBUTE_DATA_TYPE_DATE);
	}
	
	private void setEntityAttributeList(List<Map<String,String>> list, List<Entity_Attribute> a, String dataType)
	{
		for (Map<String,String> ht:list)
		{
			Entity_Attribute aa = new Entity_Attribute();
			aa.setAttribute_def_id(ht.get(EntityConstant.attribute_def_id));
			aa.setAttribute_value(ht.get(EntityConstant.attribute_value));
			aa.setAttribute_id(ht.get(EntityConstant.attribute_id));
			aa.setAttribute_data_type(dataType);
			a.add(aa);
		}
	}
	
	
	
	
	private void setEntityMapAttributeList(Connection conn,String dataSourceKey, String companyId, Map<String,String> condHt, Map<String,Entity> entityMap) throws Exception
	{
		List<Map<String,String>> list = databaseUtil.selectData(condHt,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute, null);

		if (list == null)
		{
			logger.error("setEntityMapAttributeList.query entity_attribute error!");
			throw new Exception("setEntityMapAttributeList.query entity_attribute error!");
		}
		else
		{
			setEntityMapAttributeList(list, entityMap, EntityConstant.ATTRIBUTE_DATA_TYPE_STRING);
		}
			
		
		list = databaseUtil.selectData(condHt,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute_int, null);

		if (list == null)
		{
			logger.error("setEntityMapAttributeList.query entity_attribute error!");
			throw new Exception("setEntityMapAttributeList.query entity_attribute_int error!");
		}
		else
		{
			setEntityMapAttributeList(list, entityMap, EntityConstant.ATTRIBUTE_DATA_TYPE_INT);
		}


		list = databaseUtil.selectData(condHt,dataSourceKey, companyId, null, EntityConstant.table_name_entity_attribute_date, null);

		if (list == null)
		{
			logger.error("setEntityMapAttributeList.query entity_attribute error!");
			throw new Exception("setEntityMapAttributeList.query entity_attribute_date error!");
		}
		else
		{
			setEntityMapAttributeList(list, entityMap, EntityConstant.ATTRIBUTE_DATA_TYPE_DATE);
		}
	}
	
	
	private void setEntityMapAttributeList(List<Map<String,String>> list, Map<String,Entity> entityMap, String dataType)
	{
		for (Map<String,String> ht:list)
		{
			String entity_id = ht.get(EntityConstant.entity_id);			
			Entity entity = entityMap.get(entity_id);
			if(null==entity)
			{
				continue;
			}
			
			List<Entity_Attribute> attribute_value_list = entity.getAttribute_value_list();
			if(attribute_value_list==null)
			{
				attribute_value_list = new ArrayList<Entity_Attribute>();
				entity.setAttribute_value_list(attribute_value_list);
			}
			
			Entity_Attribute aa = new Entity_Attribute();
			aa.setAttribute_def_id(ht.get(EntityConstant.attribute_def_id));
			aa.setAttribute_value(ht.get(EntityConstant.attribute_value));
			aa.setAttribute_id(ht.get(EntityConstant.attribute_id));
			aa.setAttribute_data_type(dataType);
			attribute_value_list.add(aa);
		}
	}

	public List<Map<String, String>> getTableListMap(Connection conn,String dataSourceKey,String table_name,String company_id) {

			Map<String,String> ht1 = new HashMap<String,String>();
			return  databaseUtil.selectData(conn, dataSourceKey, ht1, company_id, null, table_name, null);

		
	}

	public boolean deleteEntity(Connection conn, String dataSourceKey,Map<String, String> ht, Entity e) throws Exception
	{

			String companyId = ht.get(AccountDatabaseConstants.company_id);
			
			Map<String,String> ht1 = new HashMap<String, String>();
			
			String entity_id = e.getEntity_id();
			String entity_type = e.getEntity_type();
			
			ht1.put(EntityConstant.entity_type, entity_type);
			ht1.put(EntityConstant.entity_id, entity_id);
			
			boolean deleteEntity = databaseUtil.deleteData(conn, dataSourceKey ,ht1, companyId, null, EntityConstant.table_name_entity);
			if(!deleteEntity)
			{
				logger.error("delete entity error! entity_id = "+entity_id);
				throw new Exception("delete entity error! entity_id = "+entity_id);
			}
			boolean deleteAttribute = databaseUtil.deleteData(conn,dataSourceKey,ht1, companyId, null, EntityConstant.table_name_entity_attribute);
			if(!deleteAttribute)
			{
				logger.error("delete Attribute error! entity_id = "+entity_id);
				throw new Exception("delete Attribute error! entity_id = "+entity_id);
			}
			boolean deleteAttributeInt = databaseUtil.deleteData(conn,dataSourceKey,ht1, companyId, null, EntityConstant.table_name_entity_attribute_int);
			if(!deleteAttributeInt)
			{
				logger.error("delete Attribute Int error! entity_id = "+entity_id);
				throw new Exception("delete Attribute Int error! entity_id = "+entity_id);
			}
			boolean deleteAttributeDate = databaseUtil.deleteData(conn , dataSourceKey, ht1, companyId, null, EntityConstant.table_name_entity_attribute_date);
			if(!deleteAttributeDate)
			{
				logger.error("delete Attribute Date error! entity_id = "+entity_id);
				throw new Exception("delete Attribute Date error! entity_id = "+entity_id);
			}

			return true;
		
	}

	public String getSingleRelationAttributeValue(Connection conn, String dataSourceKey,Map<String, String> ht,String table_name,Entity_Group_Relation relation) 
	{
			String companyId = ht.get(AccountDatabaseConstants.company_id);

			ht = new HashMap<String, String>();
			Entity_Attribute entity_Attribute = relation.getRelation_attribute_value_list().get(0);
			String attribute_def_id = entity_Attribute.getAttribute_def_id();
			String relation_id = relation.getEntity_relation_id();
			
			if(null==relation_id)
			{
				ht.clear();
				ht.put(EntityConstant.entity_group_id, relation.getEntity_group_id());
				ht.put(EntityConstant.entity_id, relation.getEntity_id());
				ht.put(EntityConstant.relation_type, relation.getRelation_type());
				List<Map<String, String>> list = databaseUtil.selectData(conn,dataSourceKey, ht, companyId, null, EntityConstant.table_name_entity_group_relation, null);
				if(null==list)
				{
					logger.error("query entity_group_relation error!entity_group_id = "+relation.getEntity_group_id()+",entity_id = "+relation.getEntity_id());
					return null;
				}
				if(list.size()==0)
				{
					logger.error("RelationAttributeValue is not existed.tableName is "+EntityConstant.table_name_entity_group_relation);
					return EntityConstant.NO_RECORD;
				}
				relation_id = list.get(0).get(EntityConstant.entity_relation_id);
			}
			
			ht.clear();
			ht.put(EntityConstant.entity_id, relation_id);
			ht.put(EntityConstant.attribute_def_id, attribute_def_id);
			ht.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
			
			List<Map<String, String>> list = databaseUtil.selectData(conn, dataSourceKey, ht, companyId, null, table_name, null);
			if(null==list)
			{
				logger.error("query database error!table_name = "+table_name);
				return null;
			}
			
			if(list.size()==0)
			{
				logger.error("RelationAttributeValue is not existed.tableName is "+table_name);
				return EntityConstant.NO_RECORD;
			}
			
			return list.get(0).get(EntityConstant.attribute_value);

	}

	public boolean setSingleRelationAttributeValue(Connection conn, String dataSourceKey,Map<String, String> ht,String table_name,Entity_Group_Relation relation,boolean isInsert) 
	{

			String companyId = ht.get(AccountDatabaseConstants.company_id);
			
			Entity_Attribute entity_Attribute = relation.getRelation_attribute_value_list().get(0);
			
			if(!isInsert)
			{
				Map<String, String> valueMap = new HashMap<String, String>();
				valueMap.put(EntityConstant.attribute_value, entity_Attribute.getAttribute_value());
				
				ht.clear();
				ht.put(EntityConstant.entity_id, relation.getEntity_relation_id());
				ht.put(EntityConstant.attribute_def_id, entity_Attribute.getAttribute_def_id());
				ht.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
				databaseUtil.updateData(conn,dataSourceKey, valueMap, companyId, null, table_name, ht);
			}
			else
			{
				ht.clear();
				ht.put(EntityConstant.relation_type, relation.getRelation_type());
				ht.put(EntityConstant.entity_group_id, relation.getEntity_group_id());
				ht.put(EntityConstant.entity_id, relation.getEntity_id());
				String realationId = databaseUtil.insertDataReturnId(conn,dataSourceKey, ht, companyId, null, EntityConstant.table_name_entity_group_relation);
				if(null == realationId)
				{
					logger.error("insert entity_group_relation error!");
					return false;
				}
				
				ht.clear();
				ht.put(EntityConstant.attribute_type, EntityConstant.ATTRIBUTE_TYPE_ENTITY_GROUP_RELATION);
				ht.put(EntityConstant.entity_id, realationId);
				ht.put(EntityConstant.attribute_def_id, entity_Attribute.getAttribute_def_id());
				ht.put(EntityConstant.attribute_value, entity_Attribute.getAttribute_value());
				
				
				String returnId = databaseUtil.insertDataReturnId(conn, dataSourceKey, ht, companyId, null, table_name);
				if(null == returnId)
				{
					logger.error("insert group_relation attribute value error!");
					return false;
				}
			}
			return true;
			
	}

	public String getAttributeTableNameByDefId(Connection conn,String dataSourceKey,String defId,String companyId) {
		
		Map<String, Map<String, String>> entityDefCache = EntityDataCache.getInstance().getEntityDefCache(conn,dataSourceKey,companyId);
		String data_type = entityDefCache.get(defId).get(EntityConstant.attribute_data_type);
		 if(EntityConstant.ATTRIBUTE_DATA_TYPE_INT.equals(data_type))
		 {
		    return EntityConstant.table_name_entity_attribute_int;
		 }
		 else if(EntityConstant.ATTRIBUTE_DATA_TYPE_DATE.equals(data_type))
		 {
			 return EntityConstant.table_name_entity_attribute_date;
		 }
		 else
		 {
			 return EntityConstant.table_name_entity_attribute;
		 }   
	}

}