package com.chirag.eventdrivenframework.bean;

public final class Event<T> {
	
	private String name;
	private T data;
	
	
	
	public Event(String name, T data) {
		super();
		this.name = name;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public T getData() {
		return data;
	}
	
	

}
