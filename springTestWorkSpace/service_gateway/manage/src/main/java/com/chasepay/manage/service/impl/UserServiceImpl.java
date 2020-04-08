package com.chasepay.manage.service.impl;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chasepay.email.EmailFunction;
import com.chasepay.email.EmailObject;
import com.chasepay.manage.config.ManageServiceConfigUtil;
import com.chasepay.manage.constant.ManageConstants;
import com.chasepay.manage.service.UserService;
import com.chasepay.redis.RedisUtil;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.service.EntityDatabaseSerivce;
import com.chasepay.utilities.util.PasswordHashUtil;

@Service
public class UserServiceImpl implements UserService{
	
    private Logger logger = LogManager.getLogger(UserServiceImpl.class);
    
    @Autowired
	private RedisUtil redisUtil;
	
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
			logger.error("create user error.");
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
	public Entity selectUserById(Map<String, String> map) throws Exception
	{
		String dataSourceKey = ManageServiceConfigUtil.getDataSourceKey(map);
		Connection connection = entityDatabaseSerivce.getConnection(dataSourceKey);
		if(null==connection)
		{
			logger.error("get connection error.dataSourceKey = "+dataSourceKey);
			throw new Exception("get connection error.dataSourceKey = "+dataSourceKey);
		}
		try 
		{
			Entity entity = entityDatabaseSerivce.getUniqueEntityById(connection, dataSourceKey, map);
			return entity;
		} 
		catch (Exception e) 
		{
			logger.error("get user error.");
			logger.error(e.getMessage(),e);
			entityDatabaseSerivce.rollbackConnection(connection);
			throw e;
		}
		finally
		{
			entityDatabaseSerivce.closeConnection(connection);
		}
	}
	
	
	@Override
	public Entity selectUserByAttribute(Map<String, String> map) throws Exception
	{
		String dataSourceKey = ManageServiceConfigUtil.getDataSourceKey(map);
		Connection connection = entityDatabaseSerivce.getConnection(dataSourceKey);
		if(null==connection)
		{
			logger.error("get connection error.dataSourceKey = "+dataSourceKey);
			throw new Exception("get connection error.dataSourceKey = "+dataSourceKey);
		}
		try 
		{
			Entity entity = entityDatabaseSerivce.getUniqueEntityBySingleAttribute(connection, dataSourceKey, map);
			return entity;
		} 
		catch (Exception e) 
		{
			logger.error("get user error.");
			logger.error(e.getMessage(),e);
			entityDatabaseSerivce.rollbackConnection(connection);
			throw e;
		}
		finally
		{
			entityDatabaseSerivce.closeConnection(connection);
		}
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

	@Override
	public boolean registByPhone(String userName, String password, String phone) 
	{
		return false;
	}
	
	@Override
	public boolean registByEmail(String userName, String password, String email) 
	{
		
		logger.info("generate active code.userName="+userName+",password="+password+",email="+email);
 
		String activateCode = PasswordHashUtil.getActivateCode(userName+email+password);
		
		logger.info("generate active code.activeCode=["+activateCode+"]");
		
        EmailObject emailObject = new EmailObject();
		
		emailObject.setTo(Arrays.asList(email));
		emailObject.setSubject(ManageConstants.ACTIVE_REGIST_SUBJECT);
		emailObject.setContent("please active you account. click followed link:<font color='red'><a href='"+ManageConstants.ACTIVE_REGIST_URL+"?email="+email+"&activecode="+activateCode+"' target='_blank'>"+activateCode+"</a></font>");
		
		EmailFunction emailFunction = new EmailFunction(emailObject);
		boolean sendEmail = emailFunction.sendEmail();
		if(!sendEmail)
		{
			logger.error("fail to send email to "+email);
			return false;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(ManageConstants.USER_NAME, userName);
		map.put(ManageConstants.PASSWORD, password);
		map.put(ManageConstants.EMAIL, email);
		
		boolean set = redisUtil.set(ManageConstants.CACHE_KEY_REGIST+email, map, 24*60*60);
		
		if(!set)
		{
			logger.error("fail to set redis cache,key = "+ManageConstants.CACHE_KEY_REGIST+email);
			return false;
		} 
		
		return true;
	}

	@Override
	public boolean userExisted(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean emailExist(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean phoneExist(String phone) {
		// TODO Auto-generated method stub
		return false;
	}

	


}
