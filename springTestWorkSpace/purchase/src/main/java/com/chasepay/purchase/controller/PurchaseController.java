package com.chasepay.purchase.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chasepay.purchase.service.PurchaseService;
import com.chasepay.utilities.entity.Entity_Group;
import com.chasepay.utilities.entity.webservice.ServletRequestData;

@EnableAutoConfiguration
@CrossOrigin
@RestController
public class PurchaseController {
	
	@Resource
	private PurchaseService purchaseService;
	
	@RequestMapping(value="/createPurchase",method=RequestMethod.POST)
    public String createPurchaseEntity(@RequestBody ServletRequestData request)
    {
		Map<String, String> paramterMap = request.getParamterMap();
		Entity_Group entity_group = (Entity_Group)request.getEntity();
		String purchaseOrderId = purchaseService.createPurchaseOrder(entity_group, paramterMap);
		
		return purchaseOrderId;
    	
    }
	
	public boolean updatePurchaseEntity(@RequestBody ServletRequestData request)
	{
		return false;
		
	}

	public List<Entity_Group> getPurchaseEntitys(@RequestBody ServletRequestData request)
	{
		return null;
		
	}
	
	public Entity_Group deletePurchaseEntity(@RequestBody ServletRequestData request)
	{
		return null;
		
	}
	
	public Entity_Group selectPurchaseEntity(@RequestBody ServletRequestData request)
	{
		return null;
		
	}
	

}
