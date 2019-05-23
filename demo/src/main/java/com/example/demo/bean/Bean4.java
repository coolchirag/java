package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean4 {
	//@Autowired
	private Bean2 obj;

	public Bean4() {
		System.out.println("Bean4");
	}
}
