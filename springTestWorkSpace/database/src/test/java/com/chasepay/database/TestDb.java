package com.chasepay.database;

import java.util.List;
import java.util.Map;

import com.chasepay.configuration.ChasePayResourceBundle;
import com.chasepay.configuration.SystemBaseHome;
import com.chasepay.database.util.ChasepayDatabaseUtil;

public class TestDb {
	
	public static void main(String[] args) {
		String home = "E:\\microsgitrepo\\NewSystem\\accountweb\\apache-tomcat-8.0.15-windows-x86\\apache-tomcat-8.0.15\\webapps\\DaoUIWeb";
		SystemBaseHome.getInstance().setSystemHome(home);
		System.out.println(SystemBaseHome.getInstance().getBaseHome());
		ChasePayResourceBundle.getInstance();
		
		ChasepayDatabaseUtil MarketplaceDatabaseUtil = new ChasepayDatabaseUtil();
		
		List<Map<String, String>> selectAllData = MarketplaceDatabaseUtil.selectAllData("db1", "62", null, "entity_attribute", null);
		System.out.println(selectAllData.toString());
	}

}
