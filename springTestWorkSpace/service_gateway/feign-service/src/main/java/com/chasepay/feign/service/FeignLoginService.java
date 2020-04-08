package com.chasepay.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="service-login",fallback = FeignLoginServiceHystrix.class)
public interface FeignLoginService {
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name);

	
	
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(@RequestParam("name") String name);


}
