package com.chasepay.login.outerservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="order-service")
public interface FeignOrderService {
	
	@RequestMapping("/create")
	public String createOrder(@RequestParam(value="name",defaultValue="zhaoming") String name);

	
	
	@RequestMapping("/query")
	public String queryOrder(@RequestParam(value="name",defaultValue="zhaoming") String name,@RequestParam(value="id",defaultValue="-1") String id);


}
