package com.chirag.ruleengine.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MultiValueMap<K, T> implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<K, ArrayList<T>> map = new HashMap<K, ArrayList<T>>();
	
	public void add(K key, T element) {
		ArrayList<T> value = map.get(key);
		if(value == null) {
			value = new ArrayList<T>();
			map.put(key, value);
		}
		value.add(element);
	}
	
	public Set<K> getKeys() {
		return map.keySet();
	}
	
	public ArrayList<T> getValues(K key) {
		return map.get(key);
	}
	
	public ArrayList<T> getAllValues() {
		ArrayList<T> values = new ArrayList<T>();
		map.forEach((k,v) -> values.addAll(v));
		return values;
	}

}
