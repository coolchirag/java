package com.example.demo.bean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class AServiceBean {

	//@Autowired
	private Bean3 obj;
	private AServiceBean() {
		System.out.println("ServiceBean");
	}
	
	@PostConstruct
	private void init()
	{
		System.out.println("Init ServiceBean : "/*+obj.str*/);
	}
}
