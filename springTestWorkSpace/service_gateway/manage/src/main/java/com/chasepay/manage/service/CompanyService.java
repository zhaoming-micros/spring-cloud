package com.chasepay.manage.service;

import java.util.List;
import java.util.Map;

import com.chasepay.utilities.entity.Entity;

public interface CompanyService {
	
    public String create(Map<String,String> map,Entity entity);
	
	public boolean update(Map<String,String> map,Entity entity);
	
	public boolean delete(Map<String,String> map);
	
	public List<Entity> select(Map<String,String> map);
	
	public List<Entity> selectList(Map<String,String> map);
	

}
