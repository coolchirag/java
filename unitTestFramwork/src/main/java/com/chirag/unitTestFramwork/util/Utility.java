package com.chirag.unitTestFramwork.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utility {

	public static <T> T deepCopy(T object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		     ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
		     outputStrm.writeObject(object);
		     ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		     ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
		     return (T) objInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isValidArray(Object[] array)
	{
		return array != null && array.length > 0;
	}
	
	public static String appandObjectPath(String objectPath, String newResource)
	{
		if(objectPath == null || objectPath.isEmpty())
		{
			return newResource;
		} else {
			return objectPath+"."+newResource;
		}
	}
	
}
