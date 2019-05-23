package com.example.demo.bean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties(ConfigProperties.class)
public class AConfig {
			
	@Autowired
	private Bean3 bean3;
	
	/*@Autowired
	private ConfigProperties configProperties;*/

	public AConfig() {
		
		System.out.println("Config");
	}
		
	@Bean
	protected Bean3 getBean()
	{
		
		System.out.println("Getting bean");
		Bean3 obj = new Bean3();
		obj.str = "creation through bean.";
		return obj;
	}
	
	@PostConstruct
	private void init()
	{
		bean3.str = "hello";
		System.out.println("COnfig init");
	}
	
}
