package com.chirag.unitTestFramwork.bean;

import java.io.Serializable;

/*
 * All Test bean class must follow below rules.
 * 1. All properties must be initialize.
 * 2. All members must be Serializable.
 * 3. Provide Getter and Setter for all properties.
 * 4. Override all properties which you  used in annotations.
 * 5. For Collection properties, return type of Getter must be single element and argument type of Setter must be Single element. 
 */
public abstract class AbstractTestBean implements Serializable {

	private int statusCode = 200;
	
	private String generationSource = "default";

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getGenerationSource() {
		return generationSource;
	}

	public void setGenerationSource(String generationSource) {
		this.generationSource = generationSource;
	}

	@Override
	public String toString() {
		return "AbstractTestBean [statusCode=" + statusCode + ", generationSource=" + generationSource + "]";
	}
	
	
	
	
	
}
