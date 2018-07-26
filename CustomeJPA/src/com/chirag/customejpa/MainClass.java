package com.chirag.customejpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.chirag.customejpa.utill.ReflectionUtility;

public class MainClass {
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		
	}
	
	
	public void f1(Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		//MainClass obj = new MainClass();
		Class c = obj.getClass();//MainClass.class;
		Field[] fs = c.getDeclaredFields();
		c.getFields();
		c.getDeclaredFields();
		for(Field f : fs)
		{
			String t = f.getType().getName();
			ParameterizedType pt = (ParameterizedType) f.getGenericType();
			//f.setAccessible(true);
			for(Type fieldArgType : pt.getActualTypeArguments()){
		        Class fieldArgClass = (Class) fieldArgType;
		        System.out.println("fieldArgClass = " + fieldArgClass);
		    }
			
			//System.out.println(f.get(obj)+","+t+","+gt);
		}
		
	}

}
