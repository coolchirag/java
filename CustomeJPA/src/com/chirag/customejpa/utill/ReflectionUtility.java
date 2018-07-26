package com.chirag.customejpa.utill;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtility {

	public static Field getFieldByType(Object obj, Type type)
	{
		Field foundField = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields)
		{
			Type fieldType = field.getGenericType();
			if(fieldType.equals(type))
			{
				foundField = field;
			} else if (fieldType instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) fieldType;
				Type[] fieldArgTypes = pt.getActualTypeArguments();
				for(Type fieldArgType : fieldArgTypes)
				{
					if(fieldArgType.equals(type)) {
						foundField = field;
					}
				}
				
			}
		}
		
		return foundField;
	}
	
	public static Field getFieldByName(Object obj, String fieldName) throws NoSuchFieldException, SecurityException
	{
		return obj.getClass().getDeclaredField(fieldName);
	}
	
	public static Object callGetter(Object obj, String fieldName) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
		return pd.getReadMethod().invoke(obj);
	}
	
	public static void callSetter(Object obj, String fieldName, Object[] args) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
		pd.getWriteMethod().invoke(obj, args);
	}
}
