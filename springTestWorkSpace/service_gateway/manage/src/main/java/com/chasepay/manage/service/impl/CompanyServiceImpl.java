package com.chasepay.manage.service.impl;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.manage.config.ManageServiceConfigUtil;
import com.chasepay.manage.controller.CompanyController;
import com.chasepay.manage.service.CompanyService;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.service.EntityDatabaseSerivce;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	private Logger logger = LogManager.getLogger(CompanyServiceImpl.class);
	
	EntityDatabaseSerivce entityDatabaseSerivce = new EntityDatabaseSerivce();

	@Override
	public String create(Map<String, String> map, Entity entity) {

		String dataSourceKey = ManageServiceConfigUtil.getDataSourceKey(map);
		Connection connection = entityDatabaseSerivce.getConnection(dataSourceKey);
		if(null==connection)
		{
			logger.error("get connection error.dataSourceKey = "+dataSourceKey);
			return null;
		}
		try 
		{
			connection.setAutoCommit(false);
			String id = entityDatabaseSerivce.insertEntity(connection, dataSourceKey, null, entity);
			if(null==id)
			{
				throw new Exception("insert entity error.entity = "+ entity.toString());
			}
			entityDatabaseSerivce.commitConnection(connection);
			return id;
		} 
		catch (Exception e) 
		{
			logger.error("create company error.");
			logger.error(e.getMessage(),e);
			entityDatabaseSerivce.rollbackConnection(connection);
			return null;
		}
		finally
		{
			entityDatabaseSerivce.closeConnection(connection);
		}

	}

	@Override
	public boolean update(Map<String, String> map, Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Map<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Entity> select(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> selectList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	


}