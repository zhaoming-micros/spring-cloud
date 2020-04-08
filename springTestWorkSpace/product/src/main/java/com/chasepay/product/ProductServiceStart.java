package com.chasepay.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import com.chasepay.configuration.SystemBaseHome;
import com.chasepay.redis.RedisUtil;
import com.chasepay.redis.config.RedisConfiguration;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceStart {
	

	
	public static void main(String[] args) {
		String path = "D:\\workspace\\chasepay_service_product\\";
		//String home = "E:\\microsgitrepo\\NewSystem\\accountweb\\apache-tomcat-8.0.15-windows-x86\\apache-tomcat-8.0.15\\webapps\\DaoUIWeb";
		SystemBaseHome.getInstance().setSystemHome(path);
		System.out.println(SystemBaseHome.getInstance().getBaseHome());
		//ChasePayResourceBundle.getInstance();
		Class<?>[] classes = new Class<?>[3];
		classes[0]=ProductServiceStart.class;
		classes[1]=RedisUtil.class;
		classes[2]=RedisConfiguration.class;
		SpringApplication.run(classes, args);
		
	}

}
