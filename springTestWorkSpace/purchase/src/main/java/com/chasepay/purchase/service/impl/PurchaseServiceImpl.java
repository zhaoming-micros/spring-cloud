package com.chasepay.purchase.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chasepay.purchase.service.PurchaseService;
import com.chasepay.utilities.entity.Entity_Group;

@Service
public class PurchaseServiceImpl implements PurchaseService{
	
	
	private static Logger logger = LogManager.getLogger(PurchaseServiceImpl.class);

	public String createPurchaseOrder(Entity_Group entity_Group, Map<String, String> input) {
        
		logger.info("enter PurchaseServiceImpl.createPurchaseOrder(Entity_Group entity_Group, Map<String, String> input)");
		
		return null;
		
	}

	public boolean updatePurchaseOrder(Entity_Group entity_Group, Map<String, String> input) 
	{
		logger.info("enter PurchaseServiceImpl.updatePurchaseOrder(Entity_Group entity_Group, Map<String, String> input) ");

		boolean result = false;

		if(!result)
		{
			logger.info("update PurchaseOrder error!");
			return false;
		}
		else
		{
			logger.info("update PurchaseOrder successfullly!");
			return true;
		}
	}

	public List<Entity_Group> getPurchaseOrders(Map<String, String> input) 
	{
		logger.info("enter getPurchaseOrders(Map<String, String> input) ");

		List<Entity_Group> entitys = null;

		if(null==entitys)
		{
			logger.info("get PurchaseOrders error!");
			return null;
		}
		else
		{
			logger.info("get PurchaseOrders successfullly!");
			return entitys;
		}
	}

	public boolean deletePurchaseOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Entity_Group selectPurchaseOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createPurchaseInboundOrder(Entity_Group entity_Group, Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updatePurchaseInboundOrder(Entity_Group entity_Group, Map<String, String> input) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Entity_Group> getPurchaseInboundOrders(Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deletePurchaseInboundOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Entity_Group selectPurchaseInboundOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createPurchaseReturnOrder(Entity_Group entity_Group, Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updatePurchaseReturnOrder(Entity_Group entity_Group, Map<String, String> input) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Entity_Group> getPurchaseReturnOrders(Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deletePurchaseReturnOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return false;
	}


	public Entity_Group selectPurchaseReturnOrder(Map<String, String> input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
