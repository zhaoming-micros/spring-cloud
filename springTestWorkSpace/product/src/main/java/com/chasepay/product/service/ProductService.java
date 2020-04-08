package com.chasepay.product.service;

import java.util.List;
import java.util.Map;

import com.chasepay.utilities.entity.Entity;

public interface ProductService {
	
	public List<Entity> getProducts(Map<String,String> map);

}
