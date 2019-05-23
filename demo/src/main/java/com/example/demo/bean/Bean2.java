package com.example.demo.bean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean2 {

	/*@Autowired
	private Bean1 bean1;*/
	
	@Autowired
	private Bean3 bean3;
	
	private Bean2() {
		//bean3.str = "hello";
		//obj.str = "hello";
		System.out.println("Bean2");
	}
	
	@PostConstruct
	private void init()
	{
		System.out.println("Init of bean2 : "+bean3.str);
	}
	
	
}
