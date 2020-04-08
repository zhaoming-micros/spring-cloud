package com.chasepay.feign.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.feign.service.FeignProductService;
import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.chasepay.utilities.entity.webservice.ServletResponseData;

@CrossOrigin
@RestController
public class productServiceController {
	
	@Autowired
	FeignProductService feignProductService;
	
	@RequestMapping(value="/productlogin",method = RequestMethod.GET)
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name)
	{
		return feignProductService.login(name);
		
	}

	
	
	@RequestMapping(value="/getProducts", method = RequestMethod.POST)
	public ServletResponseData getProducts(@RequestBody ServletRequestData request)
	{
		System.out.println("feign request = "+request.toString());
		ServletResponseData response = feignProductService.getProducts(request);
		System.out.println("feign response = "+response.toString());
		return response;
	}
	

}
