package com.chasepay.purchase.service;

import java.util.List;
import java.util.Map;

import com.chasepay.utilities.entity.Entity_Group;

public interface PurchaseService {
	
	public String createPurchaseOrder(Entity_Group entity_Group,Map<String,String> input);
	
	public boolean updatePurchaseOrder(Entity_Group entity_Group,Map<String,String> input);

	public List<Entity_Group> getPurchaseOrders(Map<String,String> input);
	
	public boolean deletePurchaseOrder(Map<String,String> input);
	
	public Entity_Group selectPurchaseOrder(Map<String,String> input);
	
    public String createPurchaseInboundOrder(Entity_Group entity_Group,Map<String,String> input);
	
	public boolean updatePurchaseInboundOrder(Entity_Group entity_Group,Map<String,String> input);

	public List<Entity_Group> getPurchaseInboundOrders(Map<String,String> input);
	
	public boolean deletePurchaseInboundOrder(Map<String,String> input);
	
	public Entity_Group selectPurchaseInboundOrder(Map<String,String> input);
	
	public String createPurchaseReturnOrder(Entity_Group entity_Group,Map<String,String> input);
		
	public boolean updatePurchaseReturnOrder(Entity_Group entity_Group,Map<String,String> input);

	public List<Entity_Group> getPurchaseReturnOrders(Map<String,String> input);
		
	public boolean deletePurchaseReturnOrder(Map<String,String> input);
	
	public Entity_Group selectPurchaseReturnOrder(Map<String,String> input);
}
