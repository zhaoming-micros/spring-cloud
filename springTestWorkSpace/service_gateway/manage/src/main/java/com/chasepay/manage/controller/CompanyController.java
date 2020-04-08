package com.chasepay.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.manage.service.CompanyService;
import com.chasepay.utilities.constant.EntityAttributeConstants;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.chasepay.utilities.entity.webservice.ServletResponseData;
import com.chasepay.utilities.util.CommonUtil;

@EnableAutoConfiguration
@CrossOrigin
@RestController
public class CompanyController {
	
	@Resource
	private CompanyService companyService;
	
	private Logger logger = LogManager.getLogger(CompanyController.class);
	
	@RequestMapping(value="/company/create", method = RequestMethod.POST)
	public ServletResponseData create(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
	}
	
	@RequestMapping(value="/company/select", method = RequestMethod.POST)
	public ServletResponseData select(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	@RequestMapping(value="/company/update", method = RequestMethod.POST)
	public ServletResponseData update(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	@RequestMapping(value="/company/delete", method = RequestMethod.POST)
	public ServletResponseData delete(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		
		ServletResponseData servletResponseData = new ServletResponseData();
		
		return servletResponseData;
		
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/company/getcreate",method = RequestMethod.GET)
	public String create(@RequestParam(value="companyName") String companyName,@RequestParam(value="userId") String userId)
	{
		Entity entity = new Entity();
		entity.setEntity_type(EntityConstant.ENTITY_TYPE_COMPANY);
		List<Entity_Attribute> attributes = new ArrayList<Entity_Attribute>();
		Entity_Attribute entity_Attribute = new Entity_Attribute();

		entity_Attribute = new Entity_Attribute();
		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_company_name);
		entity_Attribute.setAttribute_value(companyName);
		attributes.add(entity_Attribute);
		entity_Attribute = new Entity_Attribute();
		entity_Attribute.setAttribute_def_id(EntityAttributeConstants.def_id_company_create_time);
		entity_Attribute.setAttribute_value(CommonUtil.getCurrentTimeMillis());
		attributes.add(entity_Attribute);
		
		entity.setAttribute_value_list(attributes);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(EntityConstant.company_id, null);
		String id = companyService.create(map, entity);
		
		if(null==id)
		{
			return userId+ " create company "+companyName+"error";
		}
		else
		{
			return userId+ " create company "+companyName+"successfully";
		}
	}

}
