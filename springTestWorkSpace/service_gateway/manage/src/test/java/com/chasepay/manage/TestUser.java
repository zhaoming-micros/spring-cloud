package com.chasepay.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.chasepay.configuration.SystemBaseHome;
import com.chasepay.manage.service.UserService;
import com.chasepay.manage.service.impl.UserServiceImpl;
import com.chasepay.utilities.constant.EntityAttributeConstants;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;
import com.chasepay.utilities.entity.Entity_Attribute;
import com.chasepay.utilities.service.EntityUtil;
import com.chasepay.utilities.util.CommonUtil;
import com.chasepay.utilities.util.PasswordHashUtil;

public class TestUser {
	
	public static void main(String[] args) {
		
		String path = "D:\\workspace\\chasepay_service_product\\";
		SystemBaseHome.getInstance().setSystemHome(path);
	
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(EntityConstant.attribute_def_id, EntityAttributeConstants.def_id_user_email);
		map.put(EntityConstant.attribute_value, "waxdyy2007@126.com");
		
		UserService userService = new UserServiceImpl();
		try 
		{
			Entity entity = userService.selectUserByAttribute(map);
			List<Entity_Attribute> attribute_value_list = entity.getAttribute_value_list();
			Map<String, String> attribute_value_map = EntityUtil.getMapFromAttributeList(attribute_value_list);
			String password_db = attribute_value_map.get(EntityAttributeConstants.def_id_user_password);
			if(!PasswordHashUtil.isPasswdCorrect("1987o2o2".getBytes(), password_db))
			{
				System.out.println("password error!");
			}
			
			System.out.println("succeed to log in.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
