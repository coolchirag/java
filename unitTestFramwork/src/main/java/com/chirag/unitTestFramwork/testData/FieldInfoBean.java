package com.chirag.unitTestFramwork.testData;

import java.lang.reflect.Field;

public class FieldInfoBean {

	private Field field;
	private Object obj; //This variable store instance of class which contain field as member. It is not value of field.
	
	
	
	public FieldInfoBean(Field field, Object obj) {
		super();
		this.field = field;
		this.obj = obj;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
	
}
