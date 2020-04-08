package com.chasepay.feign.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.feign.service.FeignOrderService;

@RestController
public class OrderServiceController {
	
	@Autowired
	FeignOrderService feignOrderService;
	
	@RequestMapping("createOrder")
	public String createOrder(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		return feignOrderService.createOrder(name);
	}

	
	
	@RequestMapping("queryOrder")
	public String queryOrder(@RequestParam(value="name",defaultValue="zhaoming") String name,@RequestParam(value="id",defaultValue="-1") String id)
	{
		return feignOrderService.queryOrder(name, id);
	}


}
