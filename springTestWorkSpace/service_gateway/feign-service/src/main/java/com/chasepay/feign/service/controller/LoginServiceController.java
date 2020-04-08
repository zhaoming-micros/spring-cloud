package com.chasepay.feign.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.feign.service.FeignLoginService;

@RestController
public class LoginServiceController {
	
	@Autowired
	FeignLoginService feignLoginService;
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		return feignLoginService.login(name);
		
	}

	
	
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		return feignLoginService.logout(name);
	}

}
