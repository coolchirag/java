package com.chirag.ruleengin.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Utility {

	public static <T extends Serializable> T deepCopy(T object) throws IOException, ClassNotFoundException {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
			outputStrm.writeObject(object);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return (T) objInputStream.readObject();
		
	}

	public static boolean isValid(final Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (obj instanceof String) {
				final String str = ((String) obj).trim();
				return !(str.isEmpty() || "null".equalsIgnoreCase(str));
			}
			return true;
		}
	}

	public static boolean isValidCollection(final Collection<?> collection) {
		return null != collection && collection.size() > 0;
	}

}
