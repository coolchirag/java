package ruleEngine.relational;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.InvalidAttributeValueException;

import ruleEngine.exception.InvalidValueException;

public interface RelationalEngine {

	String EQUAL_TO = "EQUALTO";
	String NOT_EQUAL_TO = "NOTEQUALTO";
	String IN = "IN";
	String NOT_IN = "NOTIN";
	String IS_BLANK = "ISBLANK";
	String LESS_THAN = "LESSTHAN";
	String GREATER_THAN = "GREATER_THAN";
	String IS_DUPLICATE = "ISDUPLICATE";
	String BETWEEN = "BETWEEN";
	
	Set<String> singleParamOperations = new HashSet<String>(Arrays.asList(IS_BLANK, IS_DUPLICATE));
	
	
	
	default boolean executeOperation(String operation, String value1, String value2) {
		boolean result = false;
		
		final List<String> value1List;
		if(value1.startsWith("[")) {
			value1List = Arrays.asList(value1.substring(1, value1.length()-1).split(","));
		} else {
			value1List = Collections.singletonList(value1);
		}
		List<String> value2List = null;
		if(value2.startsWith("[")) {
			value2List = Arrays.asList(value2.substring(1, value2.length()-1).split(","));
		}
		switch (operation) {
		case EQUAL_TO:
			if(value2List!=null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected single value but found array : "+value2);
			}
			result = performEqualTo(value1List, value2);
			break;
		case NOT_EQUAL_TO:
			if(value2List!=null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected single value but found array : "+value2);
			}
			result = performNotEqualTo(value1List, value2);
			break;
		case IN:
			if(value2List==null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected array but found single value : "+value2);
			}
			result = performIn(value1List, value2List);
			break;
		case NOT_IN:
			if(value2List==null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected array but found single : "+value2);
			}
			result = performNotIn(value1List, value2List);
			break;
		case IS_BLANK:
			result = performIsBlank(value1List);
			break;
		case LESS_THAN:
			if(value2List!=null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected single value but found array : "+value2);
			}
			result = performLessThan(value1List, value2);
			break;
		case GREATER_THAN:
			if(value2List!=null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected single value but found array : "+value2);
			}
			result = performGreaterThan(value1List, value2);
			break;
		case IS_DUPLICATE:
			result = performIsDuplicate(value1List);
			break;
		case BETWEEN:
			if(value2List==null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected array but found single value : "+value2);
			}
			result = performBetween(value1List, value2List);
			break;
		}
		
		return result;
	}
	
	public boolean performEqualTo(List<String> value1, String value2);
	
	public boolean performNotEqualTo(List<String> value1, String value2);
	
	public boolean performIn(List<String> value1, List<String> value2);
	
	public boolean performNotIn(List<String> value1, List<String> value2);
	
	public boolean performIsBlank(List<String> values1);
	
	public boolean performLessThan(List<String> value1, String value2);
	
	public boolean performGreaterThan(List<String> value1, String value2);
	
	public boolean performIsDuplicate(List<String> value1);
	
	public boolean performBetween(List<String> value1, List<String> value2);
}
