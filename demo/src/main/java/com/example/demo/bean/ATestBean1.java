package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ATestBean1 {

	//@Autowired
	private Bean3 obj;
	
	public ATestBean1() {
		System.out.println("ATestBean1");
	}
}
