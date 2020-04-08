package com.chasepay.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chasepay.configuration.ChasePayResourceBundle;
import com.chasepay.configuration.SystemBaseHome;
import com.chasepay.database.util.ChasepayDatabaseUtil;
import com.chasepay.product.service.impl.ProductServiceImpl;
import com.chasepay.utilities.constant.EntityConstant;
import com.chasepay.utilities.entity.Entity;

public class TestDb {
	
	public static void main(String[] args) {
		String home = "E:\\microsgitrepo\\NewSystem\\accountweb\\apache-tomcat-8.0.15-windows-x86\\apache-tomcat-8.0.15\\webapps\\DaoUIWeb";
		SystemBaseHome.getInstance().setSystemHome(home);
		System.out.println(SystemBaseHome.getInstance().getBaseHome());
		ChasePayResourceBundle.getInstance();
		
		ProductServiceImpl ProductServiceImpl =  new ProductServiceImpl();
		Map<String,String> map =  new HashMap<String,String>();
		map.put(EntityConstant.entity_type,"1");
		map.put(EntityConstant.company_id,"62");
		List<Entity> products = ProductServiceImpl.getProducts(map);
		
		System.out.println(products.toString());
	}

}
