package com.chasepay.product.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.product.service.ProductService;
import com.chasepay.redis.RedisUtil;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.chasepay.utilities.entity.webservice.ServletResponseData;


@EnableAutoConfiguration
@CrossOrigin
@RestController
public class ProductController {
	
	private Logger logger = LogManager.getLogger(ProductController.class);
	
	@Resource
	ProductService productService;
	
	
	@Autowired
	 private RedisUtil redisUtil;
	
	@RequestMapping(value="/getProducts", method = RequestMethod.POST)
	public ServletResponseData getProducts(@RequestBody ServletRequestData request) 
	{
		Map<String, String> paramterMap = request.getParamterMap();
		logger.info(paramterMap.toString());
		List<Entity> products = productService.getProducts(paramterMap);
		logger.info("products = "+products.toString());
		ServletResponseData servletResponseData = new ServletResponseData();
		if(null!=products)
		{
			servletResponseData.setSuccess(true);
			servletResponseData.setData(products);
		}
		return servletResponseData;
		
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		String from = null;
		boolean result = redisUtil.hasKey("myfirstHash");
		if(!result)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key1", "value1");
			map.put("key2", "value2");
			redisUtil.hmset("myfirstHash", map);
			from = "from database.";
		}
		else
		{
			Map<Object, Object> hmget = redisUtil.hmget("myfirstHash");
			from = "from redis."+ hmget.toString();
		}
		
		return name+ " log in  successfully on service login "+from;
	}
	

}
