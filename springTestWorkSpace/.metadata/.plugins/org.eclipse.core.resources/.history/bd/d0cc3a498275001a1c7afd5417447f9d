package com.chasepay.manage.service;

import java.util.List;
import java.util.Map;

import com.chasepay.utilities.entity.Entity;

public interface UserService {
	
    public String create(Map<String,String> map,Entity entity);
	
	public boolean update(Map<String,String> map,Entity entity);
	
	public boolean delete(Map<String,String> map);
	
	public Entity selectUserById(Map<String,String> map) throws Exception;
	
	public Entity selectUserByAttribute(Map<String,String> map) throws Exception;
	
	public List<Entity> select(Map<String,String> map) ;
	
	public List<Entity> selectList(Map<String,String> map);
	
	public boolean registByPhone(String userName,String password,String phone);
	
	public boolean registByEmail(String userName,String password,String email);
	
	public boolean userExisted(String userName);
	
	public boolean emailExist(String email);
	
	public boolean phoneExist(String phone);
	
	

}
