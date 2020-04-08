package com.carl.springCloudTest;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController@SpringBootApplication
public class MyController 
{
	
	private static Logger logger = LogManager.getLogger(MyController.class);
	
	@Value("${app.author}")
	private String author;
	
	
	@Value("${database.file}")
	private String dataBaseFile;
	
	@RequestMapping("/")
	public String welcome() 
	{
		 System.out.println(System.getProperty("user.dir"));
		return "Welcome to the world of "+author;
	}
	
	@RequestMapping("/test")
	public String hello() 
	{
		try 
		{
			File file = new File("config"+File.separator+dataBaseFile);
			System.out.println("config"+File.separator+dataBaseFile);
			logger.info("config"+File.separator+dataBaseFile);
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter write = new FileWriter(file);
			write.write("test");
			
			write.close();
			logger.info( "Hello World!");
			return "Hello World!";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Hello World error!",e);
			return "Hello World error!";
		}
		
	}
	
	public static void main(String[] args) 
	{
		SpringApplication.run(MyController.class, args);
	}
}


