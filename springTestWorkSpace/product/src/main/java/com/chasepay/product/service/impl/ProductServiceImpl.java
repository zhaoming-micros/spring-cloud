package com.chasepay.product.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chasepay.database.config.DatabaseSourceReader;
import com.chasepay.product.config.ServiceConfigUtil;
import com.chasepay.product.service.ProductService;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.service.EntityDatabaseSerivce;

@Service
public class ProductServiceImpl implements ProductService{

	EntityDatabaseSerivce entityDatabaseSerivce = new EntityDatabaseSerivce();
	
	private static Logger logger = LogManager.getLogger(DatabaseSourceReader.class);
	
	public List<Entity> getProducts(Map<String, String> map) 
	{
		String dataSourceKey = ServiceConfigUtil.getDataSourceKey(map);
		if(null==dataSourceKey)
		{
			logger.error("dataSourceKey is null.");
			return null;
		}
		Connection connection = entityDatabaseSerivce.getConnection(dataSourceKey);
		if(null==connection)
		{
			logger.error("connection is null.");
			return null;
		}
		
		String companyId = map.get(EntityConstant.company_id);
		
		String entity_type = map.get(EntityConstant.entity_type);
		
		List<Entity> products = entityDatabaseSerivce.getEntityListByEntityType(connection, dataSourceKey, companyId, entity_type, false, false);
		return products;
		
	}

}
