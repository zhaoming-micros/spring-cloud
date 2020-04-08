package com.chasepay.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chasepay.utilities.entity.webservice.ServletRequestData;
import com.chasepay.utilities.entity.webservice.ServletResponseData;

@FeignClient(value="service-product")
public interface FeignProductService {
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam(value="name",defaultValue="zhaoming") String name);

	@RequestMapping(value="/getProducts", method = RequestMethod.POST)
	public ServletResponseData getProducts(@RequestBody ServletRequestData request);


}
