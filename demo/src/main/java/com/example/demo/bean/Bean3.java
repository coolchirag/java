package com.example.demo.bean;

import org.springframework.stereotype.Component;

@Component
public class Bean3 {
	static int i = 0;
	public String str = "hi";

	public Bean3() {
		i++;
		System.out.println("bean3 : "+i);
	}
}
