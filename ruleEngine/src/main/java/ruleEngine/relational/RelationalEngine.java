package ruleEngine.relational;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	Set<String> dualParamOperations = new HashSet<String>(Arrays.asList(EQUAL_TO, NOT_EQUAL_TO, IN, NOT_IN, LESS_THAN, GREATER_THAN, IS_DUPLICATE, BETWEEN));
	Set<String> singleParamOperations = new HashSet<String>(Arrays.asList(IS_BLANK));
	
	
	
	default List<String> executeOperation(String operation, String value1, String value2) {
		List<String> result = new ArrayList<String>();
		
		final List<String> value1List;
		if(value1 == null) {
			value1List = Collections.EMPTY_LIST;
		} else if(value1.startsWith("[")) {
			value1List = Arrays.asList(value1.substring(1, value1.length()-1).split(","));
		} else {
			value1List = Collections.singletonList(value1);
		}
		List<String> value2List = null;
		if(value2 != null && value2.startsWith("[")) {
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
			if(value2List==null) {
				throw new InvalidValueException("Invalid value during : "+operation+", expected array but found single value : "+value2);
			}
			result = performIsDuplicate(value1List, value2List);
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
	
	public List<String> performEqualTo(List<String> value1, String value2);
	
	public default List<String> performNotEqualTo(List<String> value1, String value2) {
		List<String> equalList = performEqualTo(value1, value2);
		value1.removeAll(equalList);
		return value1;
	}
	
	public default List<String> performIn(List<String> value1, List<String> value2) {
		List<String> result = new ArrayList<String>();
		value2.forEach(value -> {
			result.addAll(performEqualTo(value1, value));
		});
		return result;
	}
	
	public default List<String> performNotIn(List<String> value1, List<String> value2) {
		List<String> equalList = performIn(value1, value2);
		value1.removeAll(equalList);
		return value1;
	}
	
	public default List<String> performIsBlank(List<String> values1) {
		return values1.stream().filter(value -> value == null || value.isEmpty() || value.equalsIgnoreCase("null") || value.equals(" ")).collect(Collectors.toList());
	}
	
	public List<String> performLessThan(List<String> value1, String value2);
	
	public List<String> performGreaterThan(List<String> value1, String value2);
	
	public default List<String> performIsDuplicate(List<String> value1, List<String> value2) {
		List<String> duplicateList = new ArrayList<String>();
		for(String value : value2) {
			List<String> equalList = performEqualTo(value1, value);
			if(equalList!=null && equalList.size()>1) {
				duplicateList.addAll(equalList);
			}
		}
		return duplicateList;
	}
	
	public List<String> performBetween(List<String> value1, List<String> value2);
}
