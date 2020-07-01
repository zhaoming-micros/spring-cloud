package com.chasepay.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.manage.constant.ManageConstants;
import com.chasepay.manage.service.UserService;
import com.chasepay.redis.RedisUtil;
import com.chasepay.utilities.constant.EntityAttributeConstants;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.constant.SystemConstants;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.chasepay.utilities.entity.webservice.ServletResponseData;
import com.chasepay.utilities.service.EntityUtil;
import com.chasepay.utilities.util.CommonUtil;
import com.chasepay.utilities.util.DesCryptUtil;
import com.chasepay.utilities.util.PasswordHashUtil;
import com.google.common.util.concurrent.ExecutionError;

@EnableAutoConfiguration
@CrossOrigin
@RestController
public class UserController {
	
	@Resource
	private UserService userService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private Logger logger = LogManager.getLogger(UserController.class);

	
	@RequestMapping(value="/user/create", method = RequestMethod.POST)
	public ServletResponseData create(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
	}
	
	@RequestMapping(value="/user/select", method = RequestMethod.POST)
	public ServletResponseData select(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	@RequestMapping(value="/user/update", method = RequestMethod.POST)
	public ServletResponseData update(@RequestBody ServletRequestData request,@RequestHeader Map<String, String> headers) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		logger.info("headers = "+headers.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	@RequestMapping(value="/user/delete", method = RequestMethod.POST)
	public ServletResponseData delete(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	
	
	
	
	
	

	
	
	
	@RequestMapping(value="/service/manage/user/getregistphone",method = RequestMethod.GET)
	public String registPhone(@RequestParam(value="userName") String userName,@RequestParam(value="password") String password,@RequestParam(value="phone") String phone)
	{
	
		
		return null;
		
		
	}
	
	
	@RequestMapping(value="/service/manage/user/getrgistemail",method = RequestMethod.GET)
	public String registEmail(@RequestParam(value="userName") String userName,@RequestParam(value="password") String password,@RequestParam(value="email") String email)
	{
	
		Object activeObj = redisUtil.get(ManageConstants.CACHE_KEY_REGIST+email); 
		if(null!=activeObj)
		{	
			redisUtil.del(ManageConstants.CACHE_KEY_REGIST+email);
		}
		
		boolean userExisted = userService.userExisted(userName);
		if(userExisted)
		{
			return "user is used!";
		}
		
		boolean emailExist = userService.emailExist(email);
		if(emailExist)
		{
			return "email is used!";
		}
		
		
		boolean registByEmail = userService.registByEmail(userName, password, email);
		if(!registByEmail)
		{
			return "system error!";
		}
		
		return "succeed to regist.please active in you email.";
		
		
	}
	
	
	@RequestMapping(value="/service/manage/user/active",method = RequestMethod.GET)
	public String active(@RequestParam(value="email") String email,@RequestParam(value="activecode") String activecode)
	{
	
		Object activeObj = redisUtil.get(ManageConstants.CACHE_KEY_REGIST+email); 
		if(null==activeObj)
		{
			return "active error!";
		}
		else
		{
			Map<String,String> userRegistMap = (Map<String,String>)activeObj;
			String user_name = userRegistMap.get(ManageConstants.USER_NAME);
			String pass = userRegistMap.get(ManageConstants.PASSWORD);
			String regist_email = userRegistMap.get(ManageConstants.EMAIL);
			
			logger.info("get regist info from redis.userName="+user_name+",password="+pass+",email="+regist_email);
			
			String encryptValue = PasswordHashUtil.getActivateCode(user_name+regist_email+pass);
			
			logger.info("generate active code by redis info.activeCode=["+encryptValue+"]");
			
			if(!activecode.equals(encryptValue))
			{
				logger.error("active code error!");
				
				return "active error!";
			}
			
			
			String id = create(user_name,regist_email, pass);
			
			if(null==id)
			{
                logger.error("active code error!");
				return "active error!";
			}
			
			redisUtil.del(ManageConstants.CACHE_KEY_REGIST+email);
			
			
			return "you can login now.";
		}
		
		
	}
	
	
	
	@RequestMapping(value="/service/manage/login",method = RequestMethod.GET)
	public String login(@RequestParam(value="email") String email,@RequestParam(value="password") String password)
	{
	    try 
	    {
	    	Map<String,String> map = new HashMap<String,String>();
			map.put(EntityConstant.attribute_def_id, EntityAttributeConstants.def_id_user_email);
			map.put(EntityConstant.attribute_value, email);
			
			Entity entity = userService.selectUserByAttribute(map);
			if(null==entity)
			{
				logger.error("email is not regist.email = "+email);
				return "email is not registed!";
			}
			
			List<Entity_Attribute> attribute_value_list = entity.getAttribute_value_list();
			Map<String, String> attribute_value_map = EntityUtil.getMapFromAttributeList(attribute_value_list);
			String password_db = attribute_value_map.get(EntityAttributeConstants.def_id_user_password);
			if(!PasswordHashUtil.isPasswdCorrect(password.getBytes(), password_db))
			{
				return "password error!";
			}
			
			
			boolean set = redisUtil.set(SystemConstants.CACHE_KEY_LOGIN+entity.getEntity_id(), email, 24*60*60);
			if(!set)
			{
				return "fail to login!";
			}
			
			String token = DesCryptUtil.encrypt(entity.getEntity_id()+SystemConstants.FALG_SPLIT_TOKEN+email);
			if(null==token)
			{
				return "fail to login!";
			}
			
			return token;
			
		}
	    catch (Exception e) 
	    {
	    	logger.error("login error!");
	    	logger.error(e.getMessage(),e);
	    	return "login error.";
		}
		
	}
	
	
	private String create(String userName,String email,String password)
	{
		Entity entity = new Entity();
		entity.setEntity_type(EntityConstant.ENTITY_TYPE_COMPANY);
		List<Entity_Attribute> attributes = new ArrayList<Entity_Attribute>();
		Entity_Attribute entity_Attribute =  new Entity_Attribute();

		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_user_name);
		entity_Attribute.setAttribute_value(userName);
		attributes.add(entity_Attribute);
		
		entity_Attribute = new Entity_Attribute();
		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_user_email);
		entity_Attribute.setAttribute_value(email);
		attributes.add(entity_Attribute);
		
		entity_Attribute = new Entity_Attribute();
		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_user_password);
		entity_Attribute.setAttribute_value(PasswordHashUtil.getPassword(password.getBytes()));
		attributes.add(entity_Attribute);
		
		entity_Attribute = new Entity_Attribute();
		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_user_create_time);
		entity_Attribute.setAttribute_value(CommonUtil.getCurrentTimeMillis());
		attributes.add(entity_Attribute);
		
		entity.setAttribute_value_list(attributes);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(EntityConstant.company_id, null);
		String id = userService.create(map, entity);
		
		if(null==id)
		{
			logger.error(" create user "+userName+"error");
			return null;
		}
		
		return id;
	}

}