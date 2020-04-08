package com.chasepay.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.login.message.MessageSender;
import com.chasepay.login.outerservice.FeignOrderService;

@EnableAutoConfiguration
@RestController
public class LoginService {
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	FeignOrderService feignOrderService;
	
	@Autowired
	MessageSender messageSender;
	
	@RequestMapping(value="/login",method = {RequestMethod.POST,RequestMethod.GET})
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		String queryOrder = feignOrderService.queryOrder("zhaoyizhou", "77");
		return name+ " log in  successfully on service login : "+port+"feign response :"+queryOrder;
	}
	
	
	
	@RequestMapping("/logout")
	public String logout(@RequestParam("name") String name)
	{
		messageSender.sendMessage("carl_topic1", name+ " log out  successfully from service login : "+port);
		return name+ " log out  successfully from service login : "+port;
	}
	
	
	
	
	

}
