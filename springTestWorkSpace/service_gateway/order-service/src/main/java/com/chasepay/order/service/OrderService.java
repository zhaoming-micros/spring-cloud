package com.chasepay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.order.message.MessageReceiver;





@EnableAutoConfiguration
@RestController
public class OrderService {
	
	@Value("${server.port}")
	String port;
	
	@RequestMapping("/create")
	public String createOrder(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		return name + " create a order on order service : "+port;
	}
	
	
	@RequestMapping("/query")
	public String queryOrder(@RequestParam(value="name",defaultValue="zhaoming") String name,@RequestParam(value="id",defaultValue="-1") String id)
	{
		return name + " query order "+ id +" from order service : "+port;
	}

}
